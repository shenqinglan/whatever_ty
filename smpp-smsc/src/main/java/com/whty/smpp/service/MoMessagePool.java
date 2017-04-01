package com.whty.smpp.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.exceptions.InvalidHexStringlException;
import com.whty.smpp.pdu.DeliverSM;
import com.whty.smpp.util.Utilities;
/**
 * @ClassName MoMessagePool
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(Smsc向ESME推送消息)
 */

public class MoMessagePool {

    private static Logger logger = LoggerFactory.getLogger(MoMessagePool.class);
    
	private Vector<DeliverSM> messages;

	private BufferedReader messagesReader;

	private String source_addr;

	private String destination_addr;

	private byte[] short_message;

	private int data_coding = 0;

	private int recno = 0;

	public MoMessagePool() {
	}

	public MoMessagePool(String filename) {
		String record = null;
		DeliverSM msg;
		messages = new Vector<DeliverSM>();
		try {
			messagesReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			logger.error("MoMessagePool: file not found: " + filename);
			e.printStackTrace();
		}

		do {
			try {
				record = messagesReader.readLine();
				String therecord;
				if (record == null)
					therecord = "null";
				else
					therecord = record;
				logger.debug("Read from file:<" + therecord + ">");
				if (record != null) {
					msg = new DeliverSM();
					try {
						getMessageAttributes(record);
					} catch (Exception e) {
						logger.error("Error processing delivery_messages file, record number"
										+ (recno + 1));
						logger.error(e.getMessage());
						e.printStackTrace();
						continue;
					}
					msg.setSource_addr(source_addr);
					msg.setDestination_addr(destination_addr);
					msg.setShort_message(short_message);
					msg.setData_coding(data_coding);
					messages.add(msg);
					recno++;
					logger.debug("Added delivery_message: " + source_addr
							+ "," + destination_addr + "," + new String(short_message));
				}
			} catch (Exception e) {
				logger.error("Error processing delivery_messages file");
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		} while (record != null);
		logger.debug("loaded " + recno + " delivery messages");
	}

	private void getMessageAttributes(String rec) throws Exception {
		int commaIX1;
		int commaIX2;
		String msg = "";
		commaIX1 = rec.indexOf(",");
		if (commaIX1 != -1) {
			source_addr = rec.substring(0, commaIX1);
			commaIX2 = rec.indexOf(",", commaIX1 + 1);
			if (commaIX2 != -1) {
				destination_addr = rec.substring(commaIX1 + 1, commaIX2);
				msg = rec.substring(commaIX2 + 1, rec.length());
				data_coding = 0;
				if (!msg.startsWith("0x")) 
					short_message = msg.getBytes();
				else {
					try {
						short_message = Utilities.getByteArrayFromHexString(msg.substring(2));
						data_coding = 4;
					} catch (InvalidHexStringlException e) {
						logger.error("Invalid hex string in MO service input file: <"+msg+">. Used as plain text instead.");
						short_message = msg.getBytes();
					}
				}
			} else {
				throw new Exception(
						"Invalid delivery message file format: record "
								+ (recno + 1));
			}
		} else {
			throw new Exception("Invalid delivery message file format: record "
					+ recno);
		}

	}

	// 随机从推送消息列表中选择一条消息
	protected DeliverSM getMessage() {
		int messageIX = (int) (Math.random() * recno);
		logger.debug("Selected delivery_message #" + messageIX);
		DeliverSM dsm = new DeliverSM();
		DeliverSM selected = messages.elementAt(messageIX);
		dsm = (DeliverSM) selected.clone();
		return dsm;
	}

}