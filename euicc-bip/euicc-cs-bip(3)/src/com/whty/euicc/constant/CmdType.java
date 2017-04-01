package com.whty.euicc.constant;

public class CmdType {
	public static final int init = 0;//卡片初始化
    public static final int SMS_0348 = 1;//0348 短信触发
    public static final int getSendData = 2;//获取卡片sendData命令
    public static final int DataAvaliable = 3;//
    public static final int TetminalResponse = 4;//终端回复
    public static final int FETCh = 5;//捕获
    public static final int TerminalProfile = 6;//Terminal Profile
    public static final int select = 7;
}
