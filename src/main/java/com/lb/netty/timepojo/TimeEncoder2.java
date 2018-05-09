package com.lb.netty.timepojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TimeEncoder2 extends MessageToByteEncoder<Long>{

	@Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) {
        out.writeLong(msg);
		System.out.println("encode");
		System.out.flush();
    }
}
