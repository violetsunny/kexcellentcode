/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.netty;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author kanglele
 * @version $Id: NettyHttpServer, v 0.1 2023/6/13 16:17 kanglele Exp $
 */
@Slf4j
public class NettyHttpServer {

    private final BiFunction function;

    public NettyHttpServer(BiFunction function, int port) throws Exception {
        this.function = function;
        this.start(port);
    }


    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            //ch.pipeline().addLast(new HttpServerCodec());//是HttpRequestDecoder和HttpResponseEncoder的封装
                            ch.pipeline().addLast(new HttpResponseEncoder());
                            ch.pipeline().addLast(new HttpRequestDecoder());
                            //Http请求经过HttpServerCodec解码之后是HttpRequest和HttpContents对象，
                            //HttpObjectAggregator会将多个HttpRequest和HttpContents对象再拼装成一个FullHttpRequest，再将其传递到下个Handler
                            ch.pipeline().addLast("aggregator", new HttpObjectAggregator(1024 * 1024));
                            ch.pipeline().addLast(new HttpServerInboundHandlerJson(function));
                        }
                    });

            ChannelFuture f = b.bind(port).sync();
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        log.info("端口" + port + "绑定成功!");
                    }
                }
            });
            //f.channel().closeFuture().sync();
            f.channel().closeFuture();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Slf4j
    private static class HttpServerInboundHandlerJson extends ChannelInboundHandlerAdapter {

        private final BiFunction function;

        public HttpServerInboundHandlerJson(BiFunction function) {
            this.function = function;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            FullHttpRequest request = (FullHttpRequest) msg;
            HttpHeaders headers = request.headers();
            String headValue = headers.get(HttpHeaderNames.CONTENT_TYPE.toString());
            String uri = request.uri();
            HttpMethod method = request.method();
            log.info("Uri:{} method:{}", uri, method);
            if (method.equals(HttpMethod.GET)) {
                QueryStringDecoder decoderQuery = new QueryStringDecoder(request.uri());
                Map<String, List<String>> uriAttributes = decoderQuery.parameters();
                try {
                    Object res = function.apply(uri, uriAttributes);
                    writeHttpResponseJson(JSONObject.toJSONString(res), ctx, HttpResponseStatus.OK);
                } catch (Exception e) {
                    writeHttpResponseJson(ExceptionUtils.getStackTrace(e), ctx, HttpResponseStatus.SERVICE_UNAVAILABLE);
                }

            }
            if (method.equals(HttpMethod.POST)) {
                ByteBuf buf = request.content();
                String bodyString = buf.toString(Charsets.UTF_8);
                try {
                    Object res = function.apply(uri, bodyString);
                    writeHttpResponseJson(JSONObject.toJSONString(res), ctx, HttpResponseStatus.OK);
                } catch (Exception e) {
                    writeHttpResponseJson(ExceptionUtils.getStackTrace(e), ctx, HttpResponseStatus.SERVICE_UNAVAILABLE);
                }

            }

        }

        private void writeHttpResponseJson(String res, ChannelHandlerContext ctx, HttpResponseStatus status) {
            log.info("开始写入返回数据...");
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.wrappedBuffer(res.getBytes(Charsets.UTF_8)));
            response.headers().set(CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON + ";charset=UTF-8");
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
            response.headers().setInt(EXPIRES, 0);
//          if (HttpHeaders.isKeepAlive(request)) {
//                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
//          }
            Channel ch = ctx.channel();
            ch.write(response);
//          ch.disconnect();
            ch.close();
        }

        private void writeHttpResponseImage(byte[] fileToByte, ChannelHandlerContext ctx, HttpResponseStatus status) {
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status);
            //byte[] fileToByte = this.fileToByte("f://test.jpg");
            response.content().writeBytes(fileToByte);
            response.headers().set(CONTENT_TYPE, "image/png;charset=UTF-8");
            response.headers().setInt(CONTENT_LENGTH, response.content().writerIndex());
//          Channel ch = ctx.channel();
//          ch.write(response);
            ctx.write(response);
            ctx.flush();
            ctx.close();
        }

        private void writeHttpResponseHtml(File file, ChannelHandlerContext ctx, HttpResponseStatus status) throws Exception {
//            String url = this.getClass().getResource("/").getPath() + "index.html";
//            File file = new File(url);
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
            ctx.write(response);
            ctx.write(new DefaultFileRegion(raf.getChannel(), 0, raf.length()));
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            future.addListener(ChannelFutureListener.CLOSE);
        }

        private void writeHttpResponseHtml2(String msg, ChannelHandlerContext ctx, HttpResponseStatus status) throws Exception {
            //2.给浏览器进行响应
            ByteBuf byteBuf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            //2.1 设置响应头
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            ctx.writeAndFlush(response);
        }

        private void writeHttpResponsePlain(String msg, ChannelHandlerContext ctx, HttpResponseStatus status) throws Exception {
            log.info("客服端地址" + ctx.channel().remoteAddress());
            // 回复信息给浏览器
            ByteBuf byteBuf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
            // 构造一个http响应体，即HttpResponse
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, byteBuf);
            // 设置响应头信息
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf8");
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            // 将响应体写入到通道中
            ctx.writeAndFlush(defaultFullHttpResponse);
        }

        /**
         * 本地图片的文件流
         *
         * @param filename 本地文件地址
         * @return
         */
        private byte[] fileToByte(String filename) {
            byte[] b = null;
            BufferedInputStream is = null;
            try {
                log.info("开始>>>>" + System.currentTimeMillis());
                File file = new File(filename);
                b = new byte[(int) file.length()];
                is = new BufferedInputStream(new FileInputStream(file));
                is.read(b);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return b;
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.error(cause.getMessage());
            ctx.close();
        }
    }

}
