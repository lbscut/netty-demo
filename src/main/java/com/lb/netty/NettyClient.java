package com.lb.netty;

import com.lb.netty.timepojo.TimeClientUsePOJOHandler;
import com.lb.netty.timepojo.TimeDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	public static void main(String[] args) {
		EventLoopGroup worker = new NioEventLoopGroup();
		
		Bootstrap  b = new Bootstrap();
		b.group(worker)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("decoder",new TimeDecoder());
					ch.pipeline().addLast("handler",new TimeClientUsePOJOHandler());
				}
			})
			.option(ChannelOption.SO_KEEPALIVE, true);
		
		try {
			ChannelFuture f = b.connect("localhost", 9999).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			worker.shutdownGracefully();
		}
	}
}
