package com.lb.netty.timepojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class TimeEncoder extends ChannelOutboundHandlerAdapter{

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		Long time = (Long)msg;
		ByteBuf encoded = ctx.alloc().buffer(8);
		encoded.writeLong(time);
		ctx.write(msg, promise);
	}
}
