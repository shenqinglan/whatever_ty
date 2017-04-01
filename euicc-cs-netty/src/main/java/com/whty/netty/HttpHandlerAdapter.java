package com.whty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.HttpHeaders.Values;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
/**
 * netty数据处理类
 * @author Administrator
 *
 */
public class HttpHandlerAdapter extends ChannelInboundHandlerAdapter {
	private NettyHttpHandler httpHandler;
	
	private ByteBuf temp;

	private boolean end = true;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
        System.out.println("server receive msg:{}"+msg);
		if (msg instanceof HttpRequest) {
			HttpRequest request = (HttpRequest) msg;
			if (HttpHeaders.isContentLengthSet(request)) {
				temp = Unpooled.buffer((int) HttpHeaders.getContentLength(request));
			}
		}

		if (msg instanceof HttpContent) {
			HttpContent httpContent = (HttpContent) msg;
			ByteBuf content = httpContent.content();
			reading(content);
			content.release();

			if (isEnd()) {
				String requestStr = new String(readFull());
				byte[] responseBuffer = handelRequestString(requestStr);
				FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK,
						Unpooled.wrappedBuffer(responseBuffer));
				response.headers().set(CONTENT_TYPE, "text/plain");
				response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
				response.headers().set(CONNECTION, Values.KEEP_ALIVE);
				ctx.write(response);
				ctx.flush();
			}
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		//logger.error(cause.getMessage());
	}

	public void setHttpHandler(NettyHttpHandler httpHandler) {
		this.httpHandler = httpHandler;
	}
	
	private byte[] handelRequestString(String request) {
		if (httpHandler != null) {
			return httpHandler.handle(request);
		} else {
			return request.getBytes();
		}
	}

	private void reading(ByteBuf datas) {
		datas.readBytes(temp, datas.readableBytes());
		if (this.temp.writableBytes() != 0) {
			end = false;
		} else {
			end = true;
		}
	}

	private boolean isEnd() {
		return end;
	}

	private byte[] readFull() {
		if (isEnd()) {
			byte[] contentByte = new byte[this.temp.readableBytes()];
			this.temp.readBytes(contentByte);
			this.temp.release();
			return contentByte;
		} else {
			return null;
		}
	}

	
}
