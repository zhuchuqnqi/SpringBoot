package com.example.tcp;

import io.netty.bootstrap.Bootstrap;
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
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NettyClient {

	public static void main(String[] args) throws InterruptedException {
		new NettyClient().connect(8086, "127.0.0.1");
	}

	public void connect(int port, String host) throws InterruptedException {
		// 配置客户端nio线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel channel)
								throws Exception {
							channel.pipeline().addLast(
									new DelimiterBasedFrameDecoder(2048,
											Unpooled.copiedBuffer(System
													.getProperty(
															"line.separator")
													.getBytes())));
							channel.pipeline().addLast(new StringDecoder());
							channel.pipeline()
									.addLast(new NettyClientHandler());
						}
					});

			// 发起异步连接操作
			ChannelFuture f = b.connect(host, port).sync();

			// 等待客户端链路关闭
			f.channel().closeFuture().sync();
		} finally {
			// 释放nio线程组
			group.shutdownGracefully();
		}
	}

	private class NettyClientHandler extends ChannelHandlerAdapter {
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			System.out.println("enter");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String line = br.readLine();
			System.out.println("enter1:" + line);
			while (!"EOF".equals(line)) {
				byte[] bytes = (line + System.getProperty("line.separator"))
						.getBytes();
				ByteBuf buf = Unpooled.buffer(bytes.length);
				buf.writeBytes(bytes);
				ctx.writeAndFlush(buf);
				line = br.readLine();
				System.out.println("enter1:" + line);
			}
			// byte[]
			// bytes=("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
			// ByteBuf
			// buf=Unpooled.buffer(bytes.length);
			// buf.writeBytes(bytes);
			// ctx.writeAndFlush(buf);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			System.out.println(msg);
		}
	}
}
