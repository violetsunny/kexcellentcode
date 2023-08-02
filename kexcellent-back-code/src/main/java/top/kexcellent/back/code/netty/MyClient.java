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
import io.netty.util.CharsetUtil;

import java.util.Arrays;

/**
 * @author kanglele
 * @version $Id: MyClient, v 0.1 2023/3/1 17:02 kanglele Exp $
 */
public class MyClient {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            //创建bootstrap对象，配置参数
            Bootstrap bootstrap = new Bootstrap();
            //设置线程组
            bootstrap.group(eventExecutors)
                    //设置客户端的通道实现类型
                    .channel(NioSocketChannel.class)
                    //使用匿名内部类初始化通道
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //添加客户端通道的处理器
                            ch.pipeline().addLast(new MyClientHandler());
                        }
                    });
            System.out.println("客户端准备就绪，随时可以起飞~");
            //连接服务端 sync会同步等待连接结果
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6666).sync();
            //添加监听器
            channelFuture.addListener(new ChannelFutureListener() {
                //使用匿名内部类，ChannelFutureListener接口
                //重写operationComplete方法
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    //判断是否操作成功
                    if (future.isSuccess()) {
                        System.out.println("连接成功");
                    } else {
                        System.out.println("连接失败");
                    }
                }
            });
            //对通道关闭进行监听 阻塞等待成功。注意，此处不是关闭服务器，而是channel的监听关闭。
            channelFuture.channel().closeFuture().sync();
        } finally {
            //关闭线程组
            eventExecutors.shutdownGracefully();
        }
    }

    private static class MyClientHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //发送消息到服务端
            ctx.writeAndFlush(Unpooled.copiedBuffer("歪比巴卜~茉莉~Are you good~马来西亚~", CharsetUtil.UTF_8));
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //接收服务端发送过来的消息
            ByteBuf byteBuf = (ByteBuf) msg;
            System.out.println("收到服务端" + ctx.channel().remoteAddress() + "的消息：" + byteBuf.toString(CharsetUtil.UTF_8));

            // 1.创建一个非池化的ByteBuf，大小为14个字节
            ByteBuf buffer = Unpooled.buffer(14);
            System.out.println("1.创建一个非池化的ByteBuf，大小为14个字节");
            System.out.println("ByteBuf空间大小：" + buffer.capacity());
            // 2.写入3个字节
            buffer.writeByte(62);
            buffer.writeByte(75);
            buffer.writeByte(67);
            System.out.println("\r\n2.写入3个字节");
            System.out.println("readerIndex位置：" + buffer.readerIndex());
            System.out.println("writerIndex位置：" + buffer.writerIndex());
            // 3.写入一段字节
            byte[] bytes = {73, 74, 61, 63, 0x6B};
            buffer.writeBytes(bytes);
            System.out.println("\r\n3.写入一段字节");
            System.out.println("readerIndex位置：" + buffer.readerIndex());
            System.out.println("writerIndex位置：" + buffer.writerIndex());
            // 4.读取全部内容
            byte[] allBytes = new byte[buffer.readableBytes()];
            buffer.readBytes(allBytes);
            System.out.println("\r\n4.读取全部内容");
            System.out.println("readerIndex位置：" + buffer.readerIndex());
            System.out.println("writerIndex位置：" + buffer.writerIndex());
            System.out.println("读取全部内容：" + Arrays.toString(allBytes));
            // 5.重置指针位置
            buffer.resetReaderIndex();
            System.out.println("\r\n5.重置指针位置");
            System.out.println("readerIndex位置：" + buffer.readerIndex());
            System.out.println("writerIndex位置：" + buffer.writerIndex());
            // 6.读取3个字节
            byte b0 = buffer.readByte();
            byte b1 = buffer.readByte();
            byte b2 = buffer.readByte();
            System.out.println("\r\n6.读取3个字节");
            System.out.println("readerIndex位置：" + buffer.readerIndex());
            System.out.println("writerIndex位置：" + buffer.writerIndex());
            System.out.println("读取3个字节：" + Arrays.toString(new byte[]{b0, b1, b2}));
            // 7.读取一段字节
            ByteBuf byteBuf2 = buffer.readBytes(5);
            byte[] dst = new byte[5];
            byteBuf2.readBytes(dst);
            System.out.println("\r\n7.读取一段字节");
            System.out.println("readerIndex位置：" + byteBuf2.readerIndex());
            System.out.println("writerIndex位置：" + byteBuf2.writerIndex());
            System.out.println("读取一段字节：" + Arrays.toString(dst));
            // 8.丢弃已读内容
            buffer.discardReadBytes();
            System.out.println("\r\n8.丢弃已读内容");
            System.out.println("readerIndex位置：" + buffer.readerIndex());
            System.out.println("writerIndex位置：" + buffer.writerIndex());
            // 9.清空指针位置
            buffer.clear();
            System.out.println("\r\n9.清空指针位置");
            System.out.println("readerIndex位置：" + buffer.readerIndex());
            System.out.println("writerIndex位置：" + buffer.writerIndex());
            // 10.ByteBuf中还有很多其他方法；拷贝、标记、跳过字节，多用于自定义解码器进行半包粘包处理

        }
    }
}
