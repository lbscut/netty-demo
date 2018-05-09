package com.lb.netty.time;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 继承ChannelInboundHandlerAdapter
 * 可以选择性重写一些inbound的回调方法
 * @author lb
 *
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter{
	

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf b = (ByteBuf)msg;
		System.out.println(b.readLong());
		ctx.close();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		//ChannelHandlerContext， handler的上下文
		//可通过ctx与其他handler交互
		//如果发生异常，要关闭channel
		ctx.close();
	}
	
}
