package com.lb.netty.timepojo;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class TimeDecoder extends ReplayingDecoder<Void>{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//		if (in.readableBytes() < 8) {
//	        return;
//	    }
//		out.add(in.readBytes(8));
		out.add(in.readLong());
		System.out.println("decode");
		System.out.flush();
	}

}
