/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author kanglele
 * @version $Id: NettyHttpClient3, v 0.1 2023/6/13 17:29 kanglele Exp $
 */
@Slf4j
public class NettyHttpClient3 {
    private final String urlStr;

    public NettyHttpClient3(String urlStr) {
        this.urlStr = urlStr;
    }

    public void start() throws InterruptedException {
        //线程组
        EventLoopGroup group = new NioEventLoopGroup();
        //启动类
        Bootstrap bootstrap = new Bootstrap();
        try {
            InetSocketAddress inetAddress = null;
            URI uri = new URI(urlStr);
            if (Objects.isNull(uri)) {
                return;
            }
            boolean isSSL = urlStr.contains("https");
            try {
                URL url = new URL(urlStr);
                String host = url.getHost();
                InetAddress address = InetAddress.getByName(host);
                if (!host.equalsIgnoreCase(address.getHostAddress())) {
                    //域名连接,https默认端口是443，http默认端口是80
                    inetAddress = new InetSocketAddress(address, isSSL ? 443 : 80);
                } else {
                    //ip+端口连接
                    int port = url.getPort();
                    inetAddress = InetSocketAddress.createUnresolved(host, port);
                }
            } catch (Throwable e) {
                log.error("请求地址不合法：" + e);
                return;
            }
            bootstrap.group(group)
                    .remoteAddress(inetAddress)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    .option(ChannelOption.TCP_NODELAY, true)
                    //长连接
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.ERROR))

                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            log.info("channelCreated. Channel ID：" + channel.id());
                            SocketChannel socketChannel = (SocketChannel) channel;
                            socketChannel.config().setKeepAlive(true);
                            socketChannel.config().setTcpNoDelay(true);
                            if (isSSL) {
                                //配置Https通信
                                SslContext context = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                                channel.pipeline().addLast(context.newHandler(channel.alloc()));
                            }
                            socketChannel.pipeline()
                                    //包含编码器和解码器
                                    .addLast(new HttpClientCodec())
                                    //聚合
                                    .addLast(new HttpObjectAggregator(1024 * 10 * 1024))
                                    //解压
                                    .addLast(new HttpContentDecompressor())
                                    //添加ChannelHandler
                                    .addLast(new ClientHandler());

                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NettyHttpClient3 client = new NettyHttpClient3("https://bjapi.push.jiguang.cn");
        client.start();
    }

    @Slf4j
    private static class ClientHandler extends ChannelInboundHandlerAdapter {

        /**
         * 客户端与服务端建立连接时执行
         *
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //发送请求至服务端
            log.info("channelActive");

            String msg = "{\n" +
                    "\"notification\":\n" +
                    "\t{\n" +
                    "\t\t\"android\":{\n" +
                    "\t\t\"alert\":\"alert-test\",\n" +
                    "\t\t\"title\":\"title-test\",\n" +
                    "\t\t \"style\":1,\n" +
                    "\t\t \"alert_type\":1,\n" +
                    "           \"big_text\":\"big text content\"\n" +
                    "\t\t  \n" +
                    "\t\t},\n" +
                    "\t\t\"winphone\":{\"alert\":\"alert-test\",\"title\":\"title-test\"},\n" +
                    "\t\t\"ios\":{\"alert\":\"通知\"}\n" +
                    "\t\n" +
                    "\t},\n" +
                    "\"audience\":{\"registration_id\":[\"180fe1da9e6b5af51a0\"]},\n" +
                    "\"options\":{\"apns_production\":false,\"time_to_live\":86400},\t\n" +
                    "\"platform\":\"all\"\n" +
                    "}\n";
            Map<String, String> header = new HashMap<>();
            header.put("Authorization", "Basic NIU3YzE2ZTgxOWU0YjY0MmVjNjg3NWI3OjllOTU2YjdkZmZhNDBhYWU1ZTg4YzVmOQ==");
            String url = "https://bjapi.push.jiguang.cn/v3/push";

            //配置HttpRequest的请求数据和一些配置信息
            HttpRequest request = new ClientHandler().buildRequest(msg, url, true, header);
            ChannelFuture future = ctx.writeAndFlush(request);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    //这里中刷出成功，并不代表客户接收成功，刷出数据成功默认代表已完成发送
                    log.info("http netty client刷出数据结果为：" + future.isSuccess());
                }
            });

        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            FullHttpResponse response = (FullHttpResponse) msg;
            ByteBuf content = response.content();
            log.info(": content:" + content.toString(CharsetUtil.UTF_8));
        }

        public HttpRequest buildRequest(String msg, String url, boolean isKeepAlive, Map<String, String> headers) throws Exception {
            URL netUrl = new URL(url);
            URI uri = new URI(netUrl.getPath());
            //构建http请求
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,
                    HttpMethod.POST,
                    uri.toASCIIString(),
                    Unpooled.wrappedBuffer(msg.getBytes(StandardCharsets.UTF_8)));

            //设置请求的host(这里可以是ip,也可以是域名)
            request.headers().set(HttpHeaderNames.HOST, netUrl.getHost());
            //其他头部信息
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    request.headers().set(entry.getKey(), entry.getValue());
                }
            }
            //设置返回Json
            request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
            //发送的长度
            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
            //是否是长连接
            if (isKeepAlive) {
                request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }

            return request;
        }
    }
}
