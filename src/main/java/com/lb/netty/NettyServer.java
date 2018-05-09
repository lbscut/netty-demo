package com.lb.netty;

import com.lb.netty.timepojo.TimeDecoder;
import com.lb.netty.timepojo.TimeEncoder2;
import com.lb.netty.timepojo.TimeUsePOJOHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
	public static void main(String[] args) {
		//EventLoopGroup是多个线程去监听重复循环事件，处理IO操作
		//master用于接受连接
		EventLoopGroup master = new NioEventLoopGroup();
		//当master接受了连接，并向worker注册了该连接，worker需要处理这些连接的输入输出
		EventLoopGroup worker = new NioEventLoopGroup();
		
		//ServerBootstrap帮助server的建立
		ServerBootstrap b = new ServerBootstrap();
		b.group(master,worker)
			//设置serverchannel，使用nio实现的channel
			.channel(NioServerSocketChannel.class)
			//ChannelInitializer用于帮助配置channel，可设置多个handlers
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
//					ch.pipeline().addLast(new DiscardHandler());
//					ch.pipeline().addLast(new EchoHandler());
//					ch.pipeline().addLast(new TimeHandler());
					ch.pipeline().addLast("encoder",new TimeEncoder2());
					ch.pipeline().addLast("handler",new TimeUsePOJOHandler());
				}
			})
			//option的参数用于配置接受连接的channel 
			//SO_BACKLOG，TCP连接数限制
			.option(ChannelOption.SO_BACKLOG, 128)
			//childOption的参数用于配置已经被接受的channels 
			//keepalive为true，则长时间没通讯，会发送报文测试连接状态
			.childOption(ChannelOption.SO_KEEPALIVE, true);
		
		try {
			ChannelFuture f = b.bind(9999).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			worker.shutdownGracefully();
			master.shutdownGracefully();
		}
	}
}
