/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-20
 * Id: SmppPduTranscoderImpl.java,v 1.0 2017-1-20 上午9:23:36 Administrator
 */
package com.whty.smpp.netty.transcoder;

import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffer;

import com.cloudhopper.commons.util.HexUtil;
import com.whty.smpp.netty.constants.SmppConstants;
import com.whty.smpp.netty.pdu.AlertNotification;
import com.whty.smpp.netty.pdu.BindReceiver;
import com.whty.smpp.netty.pdu.BindReceiverResp;
import com.whty.smpp.netty.pdu.BindTransceiver;
import com.whty.smpp.netty.pdu.BindTransceiverResp;
import com.whty.smpp.netty.pdu.BindTransmitter;
import com.whty.smpp.netty.pdu.BindTransmitterResp;
import com.whty.smpp.netty.pdu.CancelSm;
import com.whty.smpp.netty.pdu.CancelSmResp;
import com.whty.smpp.netty.pdu.DataSm;
import com.whty.smpp.netty.pdu.DataSmResp;
import com.whty.smpp.netty.pdu.DeliverSm;
import com.whty.smpp.netty.pdu.DeliverSmResp;
import com.whty.smpp.netty.pdu.EnquireLink;
import com.whty.smpp.netty.pdu.EnquireLinkResp;
import com.whty.smpp.netty.pdu.GenericNack;
import com.whty.smpp.netty.pdu.PartialPdu;
import com.whty.smpp.netty.pdu.PartialPduResp;
import com.whty.smpp.netty.pdu.Pdu;
import com.whty.smpp.netty.pdu.PduResponse;
import com.whty.smpp.netty.pdu.QuerySm;
import com.whty.smpp.netty.pdu.QuerySmResp;
import com.whty.smpp.netty.pdu.ReplaceSm;
import com.whty.smpp.netty.pdu.ReplaceSmResp;
import com.whty.smpp.netty.pdu.SubmitSm;
import com.whty.smpp.netty.pdu.SubmitSmResp;
import com.whty.smpp.netty.pdu.Unbind;
import com.whty.smpp.netty.pdu.UnbindResp;
import com.whty.smpp.netty.util.PduUtil;

/**
 * @ClassName SmppPduTranscoderImpl
 * @author Administrator
 * @date 2017-1-20 上午9:23:36
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SmppPduTranscoderImpl implements ISmppPduTranscoder {

	private final ISmppPduTranscoderContext context;

	/**
	 * @param context
	 */
	public SmppPduTranscoderImpl(ISmppPduTranscoderContext context) {
		this.context = context;
	}

	/**
	 * @author Administrator
	 * @date 2017-1-20
	 * @param pdu
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.netty.transcoder.ISmppPduTranscoder#encode(com.whty.smpp.data.pdu.Pdu)
	 */
	@Override
	public ChannelBuffer encode(Pdu pdu) {
		// 如果是response消息，设置结果消息
		if (pdu instanceof PduResponse) {
			PduResponse response = (PduResponse) pdu;
			if (response.getResultMessage() == null) {
				response.setResultMessage(context.lookupResultMessage(pdu
						.getCommandStatus()));
			}
		}

		// 计算pdu的长度
		if (!pdu.hasCommandLengthCalculated()) {
			pdu.calculateAndSetCommandLength();
		}

		// 创建buffer
		ChannelBuffer buffer = new BigEndianHeapChannelBuffer(
				pdu.getCommandLength());

		buffer.writeInt(pdu.getCommandLength());
		buffer.writeInt(pdu.getCommandId());
		buffer.writeInt(pdu.getCommandStatus());
		buffer.writeInt(pdu.getSequenceNumber());

		// 写入pdu消息头
		try {
			pdu.writeBody(buffer);
			pdu.writeOptionalParameters(buffer, context);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 校验长度是否一致
		if (buffer.readableBytes() != pdu.getCommandLength()) {
			throw new RuntimeException(
					"During PDU encoding the expected commandLength did not match the actual encoded (a serious error with our own encoding process)");
		}

		return buffer;
	}

	/**
	 * @author Administrator
	 * @date 2017-1-20
	 * @param buffer
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.netty.transcoder.ISmppPduTranscoder#decode(org.jboss.netty.buffer.ChannelBuffer)
	 */
	@Override
	public Pdu decode(ChannelBuffer buffer) {

		// 校验buffer的长度
		if (buffer.readableBytes() < SmppConstants.PDU_INT_LENGTH) {
			return null;
		}

		// 获取到buffer的长度
		int commandLength = buffer.getInt(buffer.readerIndex());

		// 校验command的长度
		if (commandLength < SmppConstants.PDU_HEADER_LENGTH) {
			throw new RuntimeException("Invalid PDU length [0x"
					+ HexUtil.toHexString(commandLength) + "] parsed");
		}

		// 校验buffer的长度
		if (buffer.readableBytes() < commandLength) {
			return null;
		}

		// 通过commandLength读取到buffer
		ChannelBuffer buffer0 = buffer.readSlice(commandLength);

		return doDecode(commandLength, buffer0);
	}

	protected Pdu doDecode(int commandLength, ChannelBuffer buffer) {

		// 跳过最开始解析的字节
		buffer.skipBytes(SmppConstants.PDU_INT_LENGTH);

		// 读取消息头
		int commandId = buffer.readInt();
		int commandStatus = buffer.readInt();
		int sequenceNumber = buffer.readInt();

		Pdu pdu = null;

		// 根据commandId字段解析出对应的消息
		if (PduUtil.isRequestCommandId(commandId)) {
			if (commandId == SmppConstants.CMD_ID_ENQUIRE_LINK) {
				pdu = new EnquireLink();
			} else if (commandId == SmppConstants.CMD_ID_DELIVER_SM) {
				pdu = new DeliverSm();
			} else if (commandId == SmppConstants.CMD_ID_SUBMIT_SM) {
				pdu = new SubmitSm();
			} else if (commandId == SmppConstants.CMD_ID_DATA_SM) {
				pdu = new DataSm();
			} else if (commandId == SmppConstants.CMD_ID_CANCEL_SM) {
				pdu = new CancelSm();
			} else if (commandId == SmppConstants.CMD_ID_QUERY_SM) {
				pdu = new QuerySm();
			} else if (commandId == SmppConstants.CMD_ID_REPLACE_SM) {
				pdu = new ReplaceSm();
			} else if (commandId == SmppConstants.CMD_ID_BIND_TRANSCEIVER) {
				pdu = new BindTransceiver();
			} else if (commandId == SmppConstants.CMD_ID_BIND_TRANSMITTER) {
				pdu = new BindTransmitter();
			} else if (commandId == SmppConstants.CMD_ID_BIND_RECEIVER) {
				pdu = new BindReceiver();
			} else if (commandId == SmppConstants.CMD_ID_UNBIND) {
				pdu = new Unbind();
			} else if (commandId == SmppConstants.CMD_ID_ALERT_NOTIFICATION) {
				pdu = new AlertNotification();
			} else {
				pdu = new PartialPdu(commandId);
			}
		} else { // 如果是返回消息
			if (commandId == SmppConstants.CMD_ID_SUBMIT_SM_RESP) {
				pdu = new SubmitSmResp();
			} else if (commandId == SmppConstants.CMD_ID_DELIVER_SM_RESP) {
				pdu = new DeliverSmResp();
			} else if (commandId == SmppConstants.CMD_ID_DATA_SM_RESP) {
				pdu = new DataSmResp();
			} else if (commandId == SmppConstants.CMD_ID_CANCEL_SM_RESP) {
				pdu = new CancelSmResp();
			} else if (commandId == SmppConstants.CMD_ID_QUERY_SM_RESP) {
				pdu = new QuerySmResp();
			} else if (commandId == SmppConstants.CMD_ID_REPLACE_SM_RESP) {
				pdu = new ReplaceSmResp();
			} else if (commandId == SmppConstants.CMD_ID_ENQUIRE_LINK_RESP) {
				pdu = new EnquireLinkResp();
			} else if (commandId == SmppConstants.CMD_ID_BIND_TRANSCEIVER_RESP) {
				pdu = new BindTransceiverResp();
			} else if (commandId == SmppConstants.CMD_ID_BIND_RECEIVER_RESP) {
				pdu = new BindReceiverResp();
			} else if (commandId == SmppConstants.CMD_ID_BIND_TRANSMITTER_RESP) {
				pdu = new BindTransmitterResp();
			} else if (commandId == SmppConstants.CMD_ID_UNBIND_RESP) {
				pdu = new UnbindResp();
			} else if (commandId == SmppConstants.CMD_ID_GENERIC_NACK) {
				pdu = new GenericNack();
			} else {
				pdu = new PartialPduResp(commandId);
			}
		}

		// 对pdu的消息头字段进行赋值
		pdu.setCommandLength(commandLength);
		pdu.setCommandStatus(commandStatus);
		pdu.setSequenceNumber(sequenceNumber);

		// 如果不是文档规范中规定的消息请求和消息返回类型，抛出错误信息
		if (pdu instanceof PartialPdu) {
			throw new RuntimeException(
					"Unsupported or unknown PDU request commandId [0x"
							+ HexUtil.toHexString(commandId) + "]");
		} else if (pdu instanceof PartialPduResp) {
			throw new RuntimeException(
					"Unsupported or unknown PDU response commandId [0x"
							+ HexUtil.toHexString(commandId) + "]");
		}

		// 从数据库里面根据预定的commandStatus返回查询到结果信息
		if (pdu instanceof PduResponse) {
			PduResponse response = (PduResponse) pdu;
			response.setResultMessage(context
					.lookupResultMessage(commandStatus));
		}

		try {
			// 读取消息体必须字段
			pdu.readBody(buffer);
			// 读取消息体可选字段
			pdu.readOptionalParameters(buffer, context);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pdu;
	}

}
