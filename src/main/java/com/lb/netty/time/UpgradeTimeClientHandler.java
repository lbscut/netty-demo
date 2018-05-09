package com.lb.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 优化TimeClientHandler，以防数据被分片
 * @author lb
 *
 */
public class UpgradeTimeClientHandler extends ChannelInboundHandlerAdapter{
	
	public ByteBuf buf;
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		buf = ctx.alloc().buffer(8);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		buf.release();
		buf = null;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf b = (ByteBuf)msg;
		buf.writeBytes(b);
		if(buf.readableBytes()>=8){
			System.out.println(buf.readLong());
			ctx.close();
		}
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
