package com.whty.euicc.pcsinterface;

public class CmdType {
	public static int init = 0;//卡片初始化
    public static int SMS_0348 = 1;//0348 短信触发
    public static int getSendData = 2;//获取卡片sendData命令
    public static int DataAvaliable = 3;//
    public static int TetminalResponse = 4;//终端回复
    public static final int FETCh = 5;//捕获
    public static final int TerminalProfile = 6;//Terminal Profile
}
