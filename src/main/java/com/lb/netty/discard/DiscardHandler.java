package com.lb.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 继承ChannelInboundHandlerAdapter
 * 可以选择性重写一些inbound的回调方法
 * @author lb
 *
 */
public class DiscardHandler extends ChannelInboundHandlerAdapter{
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//ByteBuf是0个或若干个byte的byte数组
		//relase释放其引用
		((ByteBuf)msg).release();
		
		//也可以打印下来，测试用
//		ByteBuf in = (ByteBuf) msg;
//	    try {
//	    	System.out.println(in.toString(io.netty.util.CharsetUtil.UTF_8));
//	    	System.out.flush();
//	    } finally {
//	    	in.release();
//	    }
		
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
