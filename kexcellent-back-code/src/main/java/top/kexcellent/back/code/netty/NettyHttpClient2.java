/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.ClientCookieEncoder;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author kanglele
 * @version $Id: NettyHttpClient2, v 0.1 2023/6/13 16:41 kanglele Exp $
 */
@Slf4j
public class NettyHttpClient2 {

    public void run(String url, HttpRequest request) throws HttpPostRequestEncoder.ErrorDataEncoderException, InterruptedException {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
        String host = uri.getHost() == null ? "localhost" : uri.getHost();
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }

        if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
            log.error("Only HTTP(S) is supported.");
        }

        boolean ssl = "https".equalsIgnoreCase(scheme);

        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast("log", new LoggingHandler(LogLevel.INFO));
                            if (ssl) {
                                //SslContext context = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                                SSLEngine engine = SslContextFactory.getClientContext().createSSLEngine();
                                engine.setUseClientMode(true);
                                p.addLast("ssl", new SslHandler(engine));
                            }
                            p.addLast("request-encoder", new HttpRequestEncoder());
                            p.addLast("response-decoder", new HttpResponseDecoder());
                            // Remove the following line if you don't want automatic content decompression.
                            p.addLast("inflater", new HttpContentDecompressor());
                            //HttpObjectAggregator会将多个HttpResponse和HttpContents对象再拼装成一个单一的FullHttpRequest或是FullHttpResponse
                            //p.addLast("aggregator", new HttpObjectAggregator(1048576));
                            p.addLast("handler", new HttpClientHandler());
                        }
                    });

            // Make the connection attempt.
            Channel ch = b.connect(host, port).sync().channel();
            // send request
            ch.writeAndFlush(request).sync();
            ch.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }


    public static void main(String args[]) throws HttpPostRequestEncoder.ErrorDataEncoderException, InterruptedException {
        String url = "http://www.cnivi.com.cn/curriculum/search.html";
        JSONObject getData = new JSONObject();
        getData.put("tags", "806:938356;");
        getData.put("sort", "_p");

        HttpRequest get = getRequestMethod(getData, url, "get");
        new NettyHttpClient2().run(url, get);
    }

    public static HttpRequest getRequestMethod(JSONObject req, String url, String method) throws HttpPostRequestEncoder.ErrorDataEncoderException {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        String path = uri.getRawPath();
        String host = uri.getHost();

        HttpRequest request = null;
        if ("post".equalsIgnoreCase(method)) {
            request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.POST, path);

            HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
            // This encoder will help to encode Request for a FORM as POST.
            HttpPostRequestEncoder bodyRequestEncoder = new HttpPostRequestEncoder(factory, request, false);
            // add Form attribute
            if (req != null) {
                Set<Map.Entry<String, Object>> entrySet = req.entrySet();
                for (Map.Entry<String, Object> e : entrySet) {
                    String key = e.getKey();
                    Object value = e.getValue();
                    bodyRequestEncoder.addBodyAttribute(key, String.valueOf(value));
                }
                try {
                    request = bodyRequestEncoder.finalizeRequest();
                } catch (HttpPostRequestEncoder.ErrorDataEncoderException e) {
                    // if an encoding error occurs
                    e.printStackTrace();
                }
            }

            request.headers().set(HttpHeaderNames.HOST, host);
            request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);
            request.headers().set(HttpHeaderNames.COOKIE, ClientCookieEncoder.LAX.encode(
                    new DefaultCookie("my-cookie", "foo"),
                    new DefaultCookie("another-cookie", "bar")));
        } else if ("get".equalsIgnoreCase(method)) {
            //uri.toString()没有查询参数的uri
            QueryStringEncoder encoder = new QueryStringEncoder(uri.toString());
            if (req != null) {
                Set<Map.Entry<String, Object>> entrySet = req.entrySet();
                for (Map.Entry<String, Object> e : entrySet) {
                    String key = e.getKey();
                    Object value = e.getValue();
                    encoder.addParam(key, String.valueOf(value));
                }
            }
            //encoder.toString()有查询参数的uri
            request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.GET, encoder.toString());
            HttpHeaders headers = request.headers();
            headers.set(HttpHeaderNames.HOST, host);
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            headers.set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP.toString() + ','
                    + HttpHeaderValues.DEFLATE.toString());
            headers.set(HttpHeaderNames.ACCEPT_CHARSET, "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            headers.set(HttpHeaderNames.ACCEPT_LANGUAGE, "fr");
            headers.set(HttpHeaderNames.USER_AGENT, "Netty Simple Http Client side");
            headers.set(HttpHeaderNames.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

            headers.set(HttpHeaderNames.COOKIE, ClientCookieEncoder.LAX.encode(
                    new DefaultCookie("my-cookie", "foo"),
                    new DefaultCookie("another-cookie", "bar"))
            );
        } else {
            log.error("this method is not support!");
        }
        return request;
    }

    @Slf4j
    private static class HttpClientHandler extends SimpleChannelInboundHandler<HttpObject> {

        public void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            if (msg instanceof HttpResponse) {
                HttpResponse response = (HttpResponse) msg;

                log.info("STATUS: " + response.getStatus());
                log.info("VERSION: " + response.getProtocolVersion());
                log.info("");

                if (!response.headers().isEmpty()) {
                    for (String name : response.headers().names()) {
                        for (String value : response.headers().getAll(name)) {
                            log.info("HEADER: " + name + " = " + value);
                        }
                    }
                    log.info("");
                }

                if (HttpUtil.isTransferEncodingChunked(response)) {
                    log.info("CHUNKED CONTENT {");
                } else {
                    log.info("CONTENT {");
                }
            }
            if (msg instanceof HttpContent) {
                HttpContent content = (HttpContent) msg;

                log.info(content.content().toString(CharsetUtil.UTF_8));
                if (content instanceof LastHttpContent) {
                    log.info("} END OF CONTENT");
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
            messageReceived(ctx, msg);
        }
    }

    @Slf4j
    private static class HttpClientHandler2 extends SimpleChannelInboundHandler<HttpObject> {

        public void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            DefaultFullHttpResponse fullHttpResponse = (DefaultFullHttpResponse) msg;

            log.info("STATUS: " + fullHttpResponse.getStatus());
            log.info("VERSION: " + fullHttpResponse.getProtocolVersion());
            log.info("");

            if (!fullHttpResponse.headers().isEmpty()) {
                for (String name : fullHttpResponse.headers().names()) {
                    for (String value : fullHttpResponse.headers().getAll(name)) {
                        log.info("HEADER: " + name + " = " + value);
                    }
                }
                log.info("");
            }

            if (HttpHeaders.isTransferEncodingChunked(fullHttpResponse)) {
                log.info("CHUNKED CONTENT {");
            } else {
                log.info("CONTENT {");
                ByteBuf content = fullHttpResponse.content();
                log.info(content.toString(CharsetUtil.UTF_8));
                log.info("} END OF CONTENT");
            }
        }


        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
            messageReceived(ctx, msg);
        }
    }
}
