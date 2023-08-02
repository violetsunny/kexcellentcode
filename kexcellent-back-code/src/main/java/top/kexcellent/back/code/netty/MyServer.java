/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import top.kexcellent.back.code.thread.ExecutorFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author kanglele
 * @version $Id: MyServer, v 0.1 2023/3/1 17:01 kanglele Exp $
 */
public class MyServer {
    public static void main(String[] args) throws Exception {
        //创建两个线程组 boosGroup、workerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup(2,ExecutorFactory.getInstance());
        EventLoopGroup workerGroup = new NioEventLoopGroup(8,ExecutorFactory.getInstance());
        try {
            //创建服务端的启动对象，设置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置两个线程组boosGroup和workerGroup
            bootstrap.group(bossGroup, workerGroup)
                    //设置服务端通道实现类型
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //使用匿名内部类的形式初始化通道对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //给pipeline管道设置处理器
                            socketChannel.pipeline().addLast(new MyServerHandler());
                        }
                    });//给workerGroup的EventLoop对应的管道设置处理器
            System.out.println("java技术爱好者的服务端已经准备就绪...");
            //绑定端口号，启动服务端
            ChannelFuture channelFuture = bootstrap.bind(6666).sync();
            //对关闭通道进行监听 阻塞等待成功。注意，此处不是关闭服务器，而是channel的监听关闭。
            channelFuture.channel().closeFuture().sync();
        } finally {
            //优雅关闭EventLoopGroup
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private static class MyServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //获取到线程池eventLoop，添加线程，执行
            ctx.channel().eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //长时间操作，不至于长时间的业务操作导致Handler阻塞
                        Thread.sleep(1000);
                        System.out.println("长时间的业务处理");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //获取客户端发送过来的消息
            ByteBuf byteBuf = (ByteBuf) msg;
            System.out.println("收到客户端" + ctx.channel().remoteAddress() + "发送的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.channel().eventLoop().schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        //长时间操作，不至于长时间的业务操作导致Handler阻塞
                        Thread.sleep(1000);
                        System.out.println("长时间的业务处理");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },5, TimeUnit.SECONDS);//5秒后执行

            //发送消息给客户端
            ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已收到消息，并给你发送一个问号?", CharsetUtil.UTF_8));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            //发生异常，关闭通道
            ctx.close();
        }
    }
}
