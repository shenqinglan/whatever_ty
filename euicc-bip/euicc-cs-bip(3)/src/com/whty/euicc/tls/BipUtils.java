package com.whty.euicc.tls;

import java.util.Timer;

import com.whty.euicc.pcsinterface.PCSInterface5java;
import com.whty.euicc.tls.AnalyseUtils;
import com.whty.euicc.constant.CmdType;
import com.whty.euicc.constant.ConstantParam;

public class BipUtils {

	public static String resp = "";// 用于存储卡返回命令
	public static String tkResp = "";// 用于存储卡上一次返回的命令
	public static String Bip_senddata = "";
	public static String Tag35_Value = "";
	public static String Tag39_Value = "";
	public static String Tag3E_Value_OA = "";
	public static String Tag3C_Value = "";
	public static String Tag3E_Value_DDA = "";
	public static String Channel_number = "";
	public static String Cardresp = "";
	public static int channel_number = 1;
	public static String TimerTrigger_Exist = "false";
	public static String returnStatus = "";
	public static String bufferSize = "";
	public static String port = "";
	public static String ipAddress = "";
	public static String channelData = "";
	public static String sw1 = "";
	public static int channelDataLength;
	public static String buffer = "";

	/*
	 * open channel 过程 对各项参数进行初始化
	 */

	// 暂无使用
	public static void OpenChannel_CommonPara() throws Exception {
		Tag35_Value = "03";
		Tag39_Value = "258E";
		Tag3E_Value_OA = "210A3A20A1";
		Tag3C_Value = "010001";
		Tag3E_Value_DDA = "210A3A20A1";
		channel_number = AnalyseUtils.randi(7) + 1;
		Channel_number = AnalyseUtils.itoa(channel_number + 0x20, 1);
	}

	/*
	 * 对get local infor获取手机时间给响应，并得到unix格式时间
	 */
	// 暂无使用
	public static void Get_Local_infor_time() throws Exception {
		if (TimerTrigger_Exist.equals("true")) {
			String time = AnalyseUtils.getTime();
			String Localtime = AnalyseUtils.localTimeFormat(time);
			resp = tkResp;
			// 此处的data应为卡片的前一个卡片主动式命令，此方法比较前一个卡片主动式命令中是否包含对应的TLV，
			// 与cmptlvs命令不同的是，cmptlvs08忽略比较Tag的最高位。
			String data = "";
			// String returnStatus = BipCommandUtils.cmptlvs08(data,
			// AnalyseUtils.totlv("D0",AnalyseUtils.totlv("81", "012603") +
			// ConstantParam.UICC_TO_TERMINAL));
			if (returnStatus.equals("9000")) {
				String downdata = "A0140000"
						+ AnalyseUtils.totlv("",
								AnalyseUtils.totlv("81", "012603")
										+ ConstantParam.UICC_TO_TERMINAL
										+ ConstantParam.TERMRESP00 + "A607"
										+ Localtime, 0);
				// resp = PCSInterface5java.sendText(downdata);
				tkResp = resp;
			}
		}
	}

	// 获取卡片上行的data数据
	public static void Get_Senddata_OK() throws Exception {
		// BipCommandUtils.clrbipupdata();//clrbipupdata 清当前程序记忆的BIP上行数据包内容
		// 继续发送801400000F810301430182028281830100B701FF 查看卡片返回状态码
		Cardresp = PCSInterface5java.sendText(
				"801400000F810301430182028281830100B701FF",
				CmdType.TetminalResponse);
		sw1 = AnalyseUtils.LastByte(Cardresp);
		while (!sw1.equals("")) {
			Cardresp = PCSInterface5java.sendText("80120000" + sw1,
					CmdType.FETCh);
			AnalyseUtils.bipsixthByte(Cardresp);
			Cardresp = PCSInterface5java.sendText(
					"801400000F810301430182028281830100B701FF",
					CmdType.TetminalResponse);
			sw1 = AnalyseUtils.LastByte(Cardresp);
		}
		System.out.println("此次卡片SendDta数据已经取完取完");
		// resp = tkResp;//D0 48 81 03 01 43 01 82 02 81 27 B6 3D 16 03 03 00 38
		// 01 00 00 34 03 03 00 01 02 03 00 01 02 03 04 05 06 07 08 09 0A 0B 0C
		// 0D 0E 0F 10 11 12 13 14 15 16 17 18 19 1A 1B 00 00 06 00 AE 00 A8 00
		// B0 01 00 00 05 00 01 00 01 02 9000
		// String commanddetail = BipCommandUtils.gettlv(resp, "81");//81 03 01
		// 43 01 9000
		/*
		 * while (AnalyseUtils.strmidh(commanddetail, 3, 1).equals("43")) {
		 * BipCommandUtils.clrbipupdata();//clrbipupdata 清当前程序记忆的BIP上行数据包内容 resp
		 * = tkResp; String returnStatus =
		 * BipCommandUtils.cmptlvs08(resp,ConstantParam
		 * .UICC_TO_CHANNEL_[channel_number-1]);
		 * if(returnStatus.equals("9000")){ resp = tkResp;
		 * //此处getbipupdata应返回bip通道中的上行数据 resp = BipCommandUtils.getbipupdata();
		 * Bip_senddata = Bip_senddata + resp; resp = tkResp;
		 */
		// String downdata =
		// BipCommandUtils.termrespfull(ConstantParam.CLA80,"1400000F",commanddetail,
		// ConstantParam.TERMINAL_TO_UICC + ConstantParam.TERMRESP00+"B701FF");
		// resp = PCSInterface5java.sendText(downdata);

		/*
		 * tkResp=resp; if (AnalyseUtils.strlen(tkResp) == 0) { commanddetail =
		 * "8103000000"; }else{ resp = tkResp; commanddetail =
		 * BipCommandUtils.gettlv(resp, "81"); } } }
		 */
	}

	/*
	 * 检查Clientdata
	 */

	// 暂无使用
	public static String CheckClientdata() {// 待调试
		// setresp %sBip_senddata
		// comptxta %sExpectdata 9000
		// <--! %sHSdata1=@Handshake_Clienthello()
		// <--! %sExpectdata=@Hand_RP(%sHSdata1)
		resp = Bip_senddata;
		// 此处应由@Handshake_Clienthello()和@Hand_RP(%sHSdata1)来获得Expectdata
		String Expectdata = "";
		return BipCommandUtils.comptxta(resp, Expectdata);
	}

	/*
	 * 16030300310200002d0303。。。。。。。。。。[服务器下发数据] 先进行判断其长度是否大于下发的数据否则就直接termres
	 */

	public static void recievedataOKClosetimer(String Serverdata)
			throws Exception {

		System.out.println("指令来了=-=" + Serverdata);
		int Tosend_leftLen = Serverdata.length() / 2;
		String Tosend_left = Serverdata;
		int Tosend_offset = 0;
		String Recevie_CMD_Number = "01";
		String Timer_status = "true";
		String s0 = "";// 0103014200
		String s1 = "";// 82028125
		String s2 = "";// 370178
		String term1 = "";
		String term0 = "";
		int tagIf = -1;
		if (Tosend_leftLen > 255) {
			String DataAvailableApdu = BipCommandUtils.dataAvailable("FF",
					ConstantParam.CHANNEL_OK_[channel_number - 1]);
			System.out.println(DataAvailableApdu);
			Cardresp = PCSInterface5java.sendText(DataAvailableApdu,
					CmdType.DataAvaliable);
			sw1 = AnalyseUtils.LastByte(Cardresp);
			Cardresp = PCSInterface5java.sendText("80120000" + sw1,
					CmdType.FETCh);
			tagIf = AnalyseUtils.bipsixthByte(Cardresp);
		} else {
			String DataAvailableApdu = BipCommandUtils.dataAvailable(
					AnalyseUtils.itoa(Tosend_leftLen, 1),
					ConstantParam.CHANNEL_OK_[channel_number - 1]);// 下发==8100
			// System.out.println(DataAvailableApdu);
			Cardresp = PCSInterface5java.sendText(DataAvailableApdu,
					CmdType.DataAvaliable);
			sw1 = AnalyseUtils.LastByte(Cardresp);
			Cardresp = PCSInterface5java.sendText("80120000" + sw1,
					CmdType.FETCh);
			tagIf = AnalyseUtils.bipsixthByte(Cardresp);
			// resp = PCSInterface5java.sendText(apdu);
			//
		}
		if (tagIf == 0) {
			tkResp = resp;
			if (Timer_status.equals("true")) {
				TimerUtils.closeTimerOK();
			}

			if (tkResp.length() >= 0) {
				s0 = AnalyseUtils.totlv(ConstantParam.COMMAND_DETAILS_TAG,
						Recevie_CMD_Number + "4200");// 0103014200
				System.out.println("s0-tlv" + s2);
				s1 = ConstantParam.UICC_TO_CHANNEL_[channel_number - 1];// 82028122通道号
			}
			if (Tosend_leftLen > 255) {
				s2 = AnalyseUtils.totlv(ConstantParam.CHANNEL_DATA_LENGTH_TAG,
						"FF");// 3701FF
				// System.out.println("s2-tlv"+s2);
			} else {
				s2 = AnalyseUtils.totlv(ConstantParam.CHANNEL_DATA_LENGTH_TAG,
						AnalyseUtils.itoa(Tosend_leftLen, 1));// 370136
				// System.out.println("测试s2"+s2);
			}
			// 对卡片返回过来的resp里面的
			// 期望数据长度、等等进行比较，如果下面为真，则执行termenalresponse的方法向卡片发送数据
			// if(BipCommandUtils.cmptlvs08(resp,s0,s1,s2).equals("9000")){
			while (Tosend_leftLen > AnalyseUtils
					.atoi(ConstantParam.UICC_CHANNEL_LENGTH)) {
				term0 = AnalyseUtils
						.totlv(ConstantParam.CHANNEL_DATA_TAG,
								AnalyseUtils
										.strmidh(
												Serverdata,
												Tosend_offset,
												AnalyseUtils
														.atoi(ConstantParam.UICC_CHANNEL_LENGTH)));
				System.out
						.println("totlv--"
								+ AnalyseUtils
										.strmidh(
												Serverdata,
												Tosend_offset,
												AnalyseUtils
														.atoi(ConstantParam.UICC_CHANNEL_LENGTH))
										.length());
				Tosend_offset = Tosend_offset
						+ AnalyseUtils.atoi(ConstantParam.UICC_CHANNEL_LENGTH);// 初步偏移量
																				// 237
				Tosend_leftLen = Tosend_leftLen
						- AnalyseUtils.atoi(ConstantParam.UICC_CHANNEL_LENGTH);
				System.out.println(Tosend_leftLen + "___" + Tosend_offset);
				if (Tosend_leftLen >= 255) {
					term1 = AnalyseUtils.totlv(
							ConstantParam.CHANNEL_DATA_LENGTH_TAG, "FF");// 3701FF
				} else {
					term1 = AnalyseUtils.totlv(
							ConstantParam.CHANNEL_DATA_LENGTH_TAG,
							AnalyseUtils.itoa(Tosend_leftLen, 1));
				}
				// System.out.println(term0);
				// System.out.println(term1);
				resp = tkResp;
				String downData = s0 + ConstantParam.TERMINAL_TO_UICC
						+ ConstantParam.TERMRESP00 + term0 + term1;

				// System.out.println("=-=-=-="+downData);

				String apdu = AnalyseUtils.totlv("80140000", downData, 0);
				System.out.println("下发指令" + apdu);
				Cardresp = PCSInterface5java.sendText(apdu,
						CmdType.TetminalResponse);
				sw1 = AnalyseUtils.LastByte(Cardresp);
				if (!sw1.equals("")) {
					Cardresp = PCSInterface5java.sendText("80120000" + sw1,
							CmdType.FETCh);
					AnalyseUtils.bipsixthByte(Cardresp);// D060810301430182028126B65516030300501000004C004A30313031303131303041383935323032303031393837363534333231313836313134443930303439413846464439384334343041373638373046324642373034323436373632344445449000
					// 这个地方也会会得上传的senddata
				}

				// System.out.println("大数据包 :" + Cardresp);
				//
				tkResp = resp;
				Recevie_CMD_Number = AnalyseUtils.increase_variable(
						Recevie_CMD_Number, 0x01);
				System.out.println("数据分包数" + Recevie_CMD_Number);
				s0 = AnalyseUtils.totlv(ConstantParam.COMMAND_DETAILS_TAG,
						Recevie_CMD_Number + "4200");// 0106024200
				// System.out.println("-0-0-0"+s0);
				s1 = ConstantParam.UICC_TO_CHANNEL_[channel_number - 1];// 82028121
				if (Tosend_leftLen >= 255) {
					s2 = AnalyseUtils.totlv(
							ConstantParam.CHANNEL_DATA_LENGTH_TAG, "FF");// 3701FF
				} else {
					s2 = AnalyseUtils.totlv(
							ConstantParam.CHANNEL_DATA_LENGTH_TAG,
							AnalyseUtils.itoa(Tosend_leftLen, 1));
				}
				// returnStatus = BipCommandUtils.cmptlvs08(resp,s0,s1,s2);
			}
			// }

			// 处理最后一个Receive data
			term0 = AnalyseUtils.totlv(ConstantParam.CHANNEL_DATA_TAG,
					AnalyseUtils.strmidh(Serverdata, Tosend_offset,
							Tosend_leftLen));// 36 36
												// 16030300310200002d030355555555555555555555555555555555555555555555555555555555555555550000AE0000050001000102
			// System.out.println("=-="+term0);
			// 在这个地方有问题，关于字节的截取与转换的长度问题咯·
			// System.out.println("iiiiiiiii-------"+term0);
			term1 = AnalyseUtils.totlv(ConstantParam.CHANNEL_DATA_LENGTH_TAG,
					"00");
			String downData = s0 + ConstantParam.TERMINAL_TO_UICC
					+ ConstantParam.TERMRESP00 + term0 + term1;
			// System.out.println("downData=-="+downData);
			String apdu = AnalyseUtils.totlv("80140000", downData, 0);
			System.out.println("Terminal最后一个数据-----=-" + apdu);
			Cardresp = PCSInterface5java.sendText(apdu,
					CmdType.TetminalResponse);// 在这个地方 卡片返回的数据为

			sw1 = AnalyseUtils.LastByte(Cardresp);// 9162
			if (!sw1.equals("")) {
				Cardresp = PCSInterface5java.sendText("80120000" + sw1,
						CmdType.FETCh);
				AnalyseUtils.bipsixthByte(Cardresp);// D060810301430182028126B65516030300501000004C004A30313031303131303041383935323032303031393837363534333231313836313134443930303439413846464439384334343041373638373046324642373034323436373632344445449000
				// 这个地方也会会得上传的senddata
				Get_Senddata_OK();// 调用getsendData
			}
			System.out.println("final  :" + Cardresp);
		}
		// }
	}

	// closeChannel 处理
	public static String closeChannel() throws Exception {
		resp = tkResp;
		// CMPTLVS08 @totlv(01,014100) @totlv(82,81+%sChannel_number)
		String st1 = AnalyseUtils.totlv("01", "014100");
		String st2 = AnalyseUtils.totlv("82", "81" + Channel_number);
		// String returnStatus = BipCommandUtils.cmptlvs08(resp, st1,st2);
		String apdu = "801400"
				+ AnalyseUtils.totlv("", "810301410082028281830100", 0);

		return apdu;

		// resp = PCSInterface5java.sendText(apdu);

	}

	// setresp %stkresp

	// Terminal profiletrigger通知事件,暂无使用
	public static void terminalProfile(String terminalStr) throws Exception {
		String apdu = AnalyseUtils.totlv("80100000", terminalStr);
		System.out.println("trigger事件咯" + apdu);
		Cardresp = PCSInterface5java.sendText(apdu, CmdType.SMS_0348);
		sw1 = AnalyseUtils.LastByte(Cardresp);// 911E
		while (!sw1.equals("")) {
			Cardresp = PCSInterface5java.sendText("80120000" + sw1,
					CmdType.FETCh);
			AnalyseUtils.bipsixthByte(Cardresp);
			Cardresp = PCSInterface5java.sendText(
					"801400000C810301050002028281830100", // 终端响应，出自eUICC函数定义
					CmdType.TetminalResponse);
			sw1 = AnalyseUtils.LastByte(Cardresp);// 911E
		}

		String termiminalX = "80F2000C00";// 根据脚本，未知用途

		Cardresp = PCSInterface5java.sendText(termiminalX,
				CmdType.TetminalResponse);
		sw1 = AnalyseUtils.LastByte(Cardresp);
		// Toolkit_initialization_Send_event_download_location_status_If_required
	}

	// 终端主动trigger事件
	public static void download_location_status() throws Exception {
		String apdu = AnalyseUtils.totlv("80C20000",
				"D615990103820282819B0100130902F801BAB8A33B008F");
		// "D613190103830282811B0100130705060101020304");
		System.out.println("触发事件" + apdu);
		Cardresp = PCSInterface5java.sendText(apdu, CmdType.TetminalResponse);
		sw1 = AnalyseUtils.LastByte(Cardresp);// 911E
		while (!sw1.equals("")) {
			Cardresp = PCSInterface5java.sendText("80120000" + sw1,
					CmdType.FETCh);
			Cardresp = PCSInterface5java.sendText(
					"801400001681030126010202828183010014081122334455667788",// "801400000F810301430082028281830100B701FF",
					CmdType.TetminalResponse);
			sw1 = AnalyseUtils.LastByte(Cardresp);// 911E
			Cardresp = PCSInterface5java.sendText("80120000" + sw1,
					CmdType.FETCh);
			AnalyseUtils.sms(Cardresp);
			sw1 = AnalyseUtils.LastByte(Cardresp);// baicheng                      
		}
		// Cardresp = PCSInterface5java.sendText("00C0000000", CmdType.FETCh);
		// AnalyseUtils.bipsixthByte(Cardresp);

		// 打开计时器
		TimerUtils.openTiemr();
	}

}
