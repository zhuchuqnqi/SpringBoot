package com.example.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class NettyServer {
	public static void main(String[] args) throws Exception {
		new NettyServer().bind(8086);
	}

	public void bind(int port) throws Exception {
		// 配置服务端的nio线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			// 设置启动辅助类
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					// 指定使用一个NIO传输Channel
					.option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChildChannelHandler());

			//异步的绑定服务器,sync()一直等到绑定完成.
			ChannelFuture f = b.bind(port).sync();

			//获得这个channel的CloseFuture,阻塞当前线程直到关闭操作完成
			f.channel().closeFuture().sync();
		} finally {
			// 释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel channel) throws Exception {
			// 指定结尾标识符号
			channel.pipeline().addLast(
					new DelimiterBasedFrameDecoder(2048, Unpooled
							.copiedBuffer(System.getProperty("line.separator")
									.getBytes())));
			// channel.pipeline().addLast(new FixedLengthFrameDecoder(17));//定长
			channel.pipeline().addLast(new StringDecoder());
			// channel.pipeline().addLast(new TimeServerHandler());
			channel.pipeline().addLast(new NettyServerHandler());

		}
	}

	private class NettyServerHandler extends ChannelHandlerAdapter {
		/**
		 * 每次收到消息的时候被调用;
		 */
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			System.out.print(msg);
			byte[] result = ("server:" + msg + System
					.getProperty("line.separator")).getBytes();
			ByteBuf buf = Unpooled.copiedBuffer(result);
			ctx.writeAndFlush(buf);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx)
				throws Exception {
			ctx.flush();
		}

		/**
		 * 在读操作异常被抛出时被调用
		 */
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			cause.printStackTrace();
			ctx.close();
		}
	}

}
