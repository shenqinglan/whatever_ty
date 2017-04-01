package com.whty.framework.netty;


import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
/**
 * @ClassName NettyServerHandlerAdapter
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class NettyServerHandlerAdapter extends ChannelInboundHandlerAdapter {
	
	Logger logger = LoggerFactory.getLogger(NettyServerHandlerAdapter.class);
	
	private ByteBuf temp;

	private boolean end = true;
	
	private NettySession session;
	
	/**
	 * @param session the session to set
	 */
	public void setSession(NettySession session) {
		this.session = session;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info("HelloServerInHandler.channelRead");    
		logger.info("msg >>> ",msg);
        
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
				logger.info("Client send: >>> {}", requestStr);
				byte[] responseBuffer = handelRequestString(requestStr);
				
				// 向客户端发送消息 
				logger.info("server response >>> {}", new String(responseBuffer));
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
	
	/**
	 * @author Administrator
	 * @date 2017-1-19
	 * @param request
	 * @return
	 * @throws SmppInvalidArgumentException
	 * @Description TODO(处理接收到的消息)
	 */
	private byte[] handelRequestString(String request) {
		Gson gson = new Gson();
    	MsgSend msgSend = gson.fromJson(request, MsgSend.class);
		byte[] response = session.sendMsg(msgSend, 30000);
        return response;
	}
	
	@Override  
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		logger.info("HelloServerInHandler.channelRead complete");
        ctx.flush();   
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
