/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.ttl.TransmittableThreadLocal;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.CharsetUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLEngine;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author kanglele
 * @version $Id: NettyHttpUtil, v 0.1 2023/5/17 18:22 kanglele Exp $
 */
@Slf4j
public class NettyHttpClient {

    private static final boolean KEEPALIVE = true;

    @Getter
    private final TransmittableThreadLocal<String> response = new TransmittableThreadLocal<>();

    public void connect(HttpMethod method, String url, Object msg, Map<String, String> headers) throws Exception {
        URI uri = new URI(url);
        String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
        String host = uri.getHost() == null ? "localhost" : uri.getHost();
        boolean ssl = "https".equalsIgnoreCase(scheme);

        InetSocketAddress inetAddress = null;
        InetAddress address = InetAddress.getByName(host);
        if (!host.equalsIgnoreCase(address.getHostAddress())) {
            //域名连接,https默认端口是443，http默认端口是80
            inetAddress = new InetSocketAddress(address, ssl ? 443 : 80);
        } else {
            //ip+端口连接
            int port = uri.getPort();
            inetAddress = InetSocketAddress.createUnresolved(host, port);
        }


        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .remoteAddress(inetAddress)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .option(ChannelOption.SO_KEEPALIVE, KEEPALIVE)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            if (ssl) {
                                //配置Https通信
                                //SslContext context = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                                //ch.pipeline().addLast(context.newHandler(ch.alloc()));
                                SSLEngine engine = SslContextFactory.getClientContext().createSSLEngine();
                                engine.setUseClientMode(true);
                                ch.pipeline().addLast("ssl", new SslHandler(engine));
                            }
                            ch.pipeline().addLast(new HttpClientCodec());
                            //HttpObjectAggregator会将多个HttpResponse和HttpContents对象再拼装成一个单一的FullHttpRequest或是FullHttpResponse
                            ch.pipeline().addLast("aggre", new HttpObjectAggregator(10 * 1024 * 1024));
                            //解压
                            ch.pipeline().addLast("decompressor", new HttpContentDecompressor());
                            // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                            ch.pipeline().addLast(new HttpResponseDecoder());
                            // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                            ch.pipeline().addLast(new HttpRequestEncoder());
                            // 获取返回
                            ch.pipeline().addLast(new OutputResultHandler2());
                        }
                    });

            // Start the client.
            ChannelFuture f = b.connect().sync();

            HttpRequest request = getRequestMethod(url, method, msg, headers);
            // 发送http请求
            f.channel().write(request);
            f.channel().flush();
            //f.channel().closeFuture().sync();
            f.channel().closeFuture();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static HttpRequest getRequestMethod(String url, HttpMethod method, Object msg, Map<String, String> headers) throws Exception {
        URI uri2 = new URI(url);
        String host = uri2.getHost();
        String postPath = uri2.getRawPath();//post
        String getPath = uri2.toString();//get
//        URL netUrl = new URL(url);
//        URI uri = new URI(netUrl.getPath());
//        String path = uri.toASCIIString();//post


        DefaultFullHttpRequest request = null;
        if (method.equals(HttpMethod.POST)) {
            // 构建http请求
            if (headers.get(HttpHeaderNames.CONTENT_TYPE.toString()).equals(HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())) {
                List<BasicNameValuePair> formData = new ArrayList<>();
                Set<Map.Entry<String, Object>> entrySet = JSONObject.parseObject(JSON.toJSONString(msg)).entrySet();
                for (Map.Entry<String, Object> e : entrySet) {
                    String key = e.getKey();
                    Object value = e.getValue();
                    formData.add(new BasicNameValuePair(key, String.valueOf(value)));
                }
                HttpEntity httpEntity = new UrlEncodedFormEntity(formData);
                ByteBuf byteBuf = Unpooled.wrappedBuffer(EntityUtils.toByteArray(httpEntity));
                request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, postPath, byteBuf);
                request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED);
            } else {
                ByteBuf byteBuf = Unpooled.wrappedBuffer(JSON.toJSONBytes(msg));
                request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, postPath, byteBuf);
                request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
            }
        } else if (method.equals(HttpMethod.GET)) {
            // 构建http请求
            QueryStringEncoder encoder = new QueryStringEncoder(getPath);
            if (msg != null) {
                Set<Map.Entry<String, Object>> entrySet = JSONObject.parseObject(JSON.toJSONString(msg)).entrySet();
                for (Map.Entry<String, Object> e : entrySet) {
                    String key = e.getKey();
                    Object value = e.getValue();
                    encoder.addParam(key, String.valueOf(value));
                }
            }
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, encoder.toString());

        }

        request.headers().set(HttpHeaderNames.HOST, host);
        if (KEEPALIVE) {
            request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        } else {
            request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        }
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        //其他头部信息
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.headers().set(entry.getKey(), entry.getValue());
            }
        }
        return request;
    }

    @Slf4j
    private static class OutputResultHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof HttpResponse) {
                HttpResponse response = (HttpResponse) msg;
                log.info(response.toString());
            }
            if (msg instanceof HttpContent) {
                HttpContent content = (HttpContent) msg;
                ByteBuf buf = content.content();
                log.info(buf.toString(CharsetUtil.UTF_8));
                buf.release();
            }
        }
    }

    public class OutputResultHandler2 extends SimpleChannelInboundHandler<FullHttpResponse> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpResponse fullHttpResponse) throws Exception {
            if (!fullHttpResponse.headers().isEmpty()) {
                for (String name : fullHttpResponse.headers().names()) {
                    for (String value : fullHttpResponse.headers().getAll(name)) {
                        //log.info("HEADER: " + name + " = " + value);
                    }
                }
            }

            ByteBuf buf = fullHttpResponse.content();
            //log.info("content:{}", buf.toString(CharsetUtil.UTF_8));
            response.set(buf.toString(CharsetUtil.UTF_8));
        }

    }
}
