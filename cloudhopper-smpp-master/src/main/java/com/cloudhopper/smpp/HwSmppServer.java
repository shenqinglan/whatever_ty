package com.cloudhopper.smpp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ServerSocketFactory;

import com.cloudhopper.commons.util.windowing.WindowFuture;
import com.cloudhopper.smpp.bean.MsgSend;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.DeliverSm;
import com.cloudhopper.smpp.pdu.EnquireLink;
import com.cloudhopper.smpp.pdu.EnquireLinkResp;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.pdu.SubmitSmResp;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.util.HttpUtil;
import com.cloudhopper.smpp.util.PropertiesUtil;
import com.google.gson.Gson;

public class HwSmppServer {
    private final int POOL_SIZE = 10;
    private static SmppSession session;
    private DefaultSmppClient clientBootstrap;
    private DefaultSmppSessionHandler sessionHandler;
    private SmppSessionConfiguration config;
    private SafeThread heartbeat;
    
    public HwSmppServer(){
		ScheduledThreadPoolExecutor monitorExecutor = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(1, new ThreadFactory() {
            private AtomicInteger sequence = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("SmppClientSessionWindowMonitorPool-" + sequence.getAndIncrement());
                return t;
            }
        });
		
		clientBootstrap = new DefaultSmppClient(Executors.newCachedThreadPool(), 1, monitorExecutor);
		sessionHandler = new ClientSmppSessionHandler();
		config = new SmppSessionConfiguration();
        config.setWindowSize(1);
        config.setName("bind_transceiver");
        config.setType(SmppBindType.TRANSCEIVER);
        config.setHost(PropertiesUtil.getValue("sms_host"));
        config.setPort(Integer.parseInt(PropertiesUtil.getValue("sms_port")));
        config.setConnectTimeout(10000);
        config.setSystemId(PropertiesUtil.getValue("system_id"));
        config.setPassword(PropertiesUtil.getValue("password"));
        config.setSystemType(null);
        config.setInterfaceVersion((byte)0x34);
        config.setAddressRange(new Address((byte)0x00, (byte)0x00, null));
        config.getLoggingOptions().setLogBytes(true);
        config.setRequestExpiryTimeout(30000);
        config.setWindowMonitorInterval(15000);
        config.setCountersEnabled(true);
    }
    
    private void bind() {
    	try {
			session = clientBootstrap.bind(config, sessionHandler);
		} catch (Exception e) {
			throw new RuntimeException("bind failed");
		}
    }

	private void heartbeat() {
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				try {
					WindowFuture<Integer,PduRequest,PduResponse> future = session.sendRequestPdu(new EnquireLink(), 10000, true);
		            if (!future.await()) {
		            	System.out.println("Wait receive enquire_link_resp");
		            } else if (future.isSuccess()) {
		                EnquireLinkResp enquireLinkResp = (EnquireLinkResp)future.getResponse();
		                System.out.println("receive enquire_link_resp");
		                if (enquireLinkResp.getCommandStatus() != 0) {
		                	if (session == null) {
		                		session = clientBootstrap.bind(config, sessionHandler);
		                	} else {
		                		if (!session.isBound()) {
		                			session.close();
		                			session.destroy();
		                			session = clientBootstrap.bind(config, sessionHandler);
		                		}
		                	}
		                }
		            } else {
		            	System.out.println("Failed to properly receive enquire_link_resp: " + future.getCause());
		            	if (session == null) {
	                		session = clientBootstrap.bind(config, sessionHandler);
	                	} else {
	                		if (!session.isBound()) {
	                			session.close();
	                			session.destroy();
	                			session = clientBootstrap.bind(config, sessionHandler);
	                		}
	                	}
		            } 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
        Timer timer = new Timer();
        timer.schedule(timerTask, 180000, 180000);
	}
    
    private void service() {
    	ServerSocket serverSocket = null;
        ExecutorService executorService = null;
    	try {
	    	serverSocket = ServerSocketFactory.getDefault().createServerSocket(Integer.parseInt(PropertiesUtil.getValue("smpp_server_port")));
	    	executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
	                .availableProcessors() * POOL_SIZE);
	    	System.out.println("Server started...");
	    	System.out.println("Wait client connect...");
	    	while (true) {
    		 
	        	 final Socket socket = serverSocket.accept();
	             System.out.println("A client has connected");
	             executorService.execute(new Runnable() {
					
					@Override
					public void run() {
						try {
				        	DataInputStream input = new DataInputStream(socket.getInputStream());
				        	String line = input.readUTF();
				        	System.out.println("submit data:"+line);
				        	
				        	Gson gson = new Gson();
				        	MsgSend msgSend = gson.fromJson(line, MsgSend.class);
		
				            SubmitSm submit = new SubmitSm();
				            submit.setServiceType("");
				            submit.setSourceAddress(new Address((byte)0x00, (byte)0x00, msgSend.getSrc()));
				            submit.setDestAddress(new Address((byte)0x00, (byte)0x00, msgSend.getDest()));
				            submit.setEsmClass((byte)0x40);
				            submit.setProtocolId((byte)0x00);
				            submit.setPriority((byte)0x00);
				            submit.setScheduleDeliveryTime(null);
				            submit.setValidityPeriod(null);
				        	submit.setRegisteredDelivery("1".equals(msgSend.getReport()) ? SmppConstants.REGISTERED_DELIVERY_SMSC_RECEIPT_REQUESTED : SmppConstants.REGISTERED_DELIVERY_SMSC_RECEIPT_NOT_REQUESTED);
				        	submit.setReplaceIfPresent((byte)0x00);
				        	submit.setDataCoding((byte)0x01);
				        	submit.setDefaultMsgId((byte)0x00);
				        	submit.setShortMessage(msgSend.getMsg().getBytes());
				        	
				            SubmitSmResp submitResp = session.submit(submit, 30000);
				            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				            out.writeUTF(gson.toJson(submitResp));
				        	input.close();
				        	out.close();
				            socket.close();
						} catch (Exception e) {
		                 e.printStackTrace();
						}
					}
	             });
             
    		 }		
    	}catch (Exception e) {
			 e.printStackTrace();
		 }
    }
    
    private void startThreads() {
    	this.heartbeat = new SafeThread(new Runnable() {
			
			@Override
			public void run() {
				heartbeat();
			}
		}, "heartbeat");
    	this.heartbeat.start();
    }

    private static void conn() {
    	HwSmppServer hwSmppServer = new HwSmppServer();
		hwSmppServer.bind();
		hwSmppServer.heartbeat();
		hwSmppServer.service();
    }
    
	public static void main(String[] args) {
		conn();
	}
	
	public static class ClientSmppSessionHandler extends DefaultSmppSessionHandler {

        @Override
        public PduResponse firePduRequestReceived(PduRequest pduRequest) {
            PduResponse response = pduRequest.createResponse();

            if (pduRequest.getCommandId() == SmppConstants.CMD_ID_DELIVER_SM) {

                final DeliverSm mo = (DeliverSm) pduRequest;
                System.out.println("receive deliver_sm");
                String receiveSmsUrl = PropertiesUtil.getValue("receive_sms_url");
        		Map<String, String> argsMap = new HashMap<String, String>() {{ 
        		    put( "src" , mo.getSourceAddress().getAddress()); 
        		    put( "msg" , new String(mo.getShortMessage()));
        		}}; 
        		String resp = HttpUtil.post(receiveSmsUrl, argsMap);
        		System.out.println(resp);
            }

            return response;
        }
    }
	
	private final class SafeThread extends Thread {
        private boolean alive = true;

        public SafeThread(Runnable target, String name) {
            super(target, name);
            setDaemon(false);
        }

        public void kill() {
            //安全退出线程
            this.alive = false;
        }

        @Override
        public final void run() {
            while (alive) {
                try {
                    super.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
