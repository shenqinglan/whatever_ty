package com.whty.ga.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName StringUtil
 * @author Administrator
 * @date 2017-3-3 上午9:56:22
 * @Description TODO(字符串转换工具类)
 */
public class StringUtil {

	/**
	 * @author Administrator
	 * @date 2017-3-2
	 * @param json
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	public static JSONObject trimJSONString(JSONObject json) {
		JSONObject result = new JSONObject();
		for (String key : json.keySet()) {
			result.put(key, json.getString(key).trim());
		}
		return result;
	}
	
	/**
	 * @author Administrator
	 * @date 2017-3-2
	 * @param bytes
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	public static String bytes2HexString(byte[] bytes) {
		StringBuilder sb=new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            int tempI=bytes[i] & 0xFF;//byte:8bit,int:32bit;高位相与.
            String str = Integer.toHexString(tempI);
            if(str.length()<2){
                sb.append(0).append(str);//长度不足两位，补齐：如16进制的d,用0d表示。
            }else{
                sb.append(str);
            }
        }
        return sb.toString();
    }

	 /**
     * 将普通字符串用16进制描述
     * 如"WAZX-B55SY6-S6DT5" 描述为："57415a582d4235355359362d5336445435"
     * */
    public static String strToHex(String str){
         byte[] bytes = str.getBytes(); 
         return bytesToHex(bytes);
    }
    
    /**将16进制描述的字符串还原为普通字符串
     * 如"57415a582d4235355359362d5336445435" 还原为："WAZX-B55SY6-S6DT5"
     * */
    public static String hexToStr(String hex){
        byte[] bytes=hexToBytes(hex);
        return new String(bytes);
    }
    
    
    /**16进制转byte[]*/
    public static byte[] hexToBytes(String hex){
        int length = hex.length() / 2;
        byte[] bytes=new byte[length];
        for(int i=0;i<length;i++){
            String tempStr=hex.substring(2*i, 2*i+2);//byte:8bit=4bit+4bit=十六进制位+十六进制位
            bytes[i]=(byte) Integer.parseInt(tempStr, 16);
        }
        return bytes;
    }
    
    /**byte[]转16进制*/
    public static String bytesToHex(byte[] bytes){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            int tempI=bytes[i] & 0xFF;//byte:8bit,int:32bit;高位相与.
            String str = Integer.toHexString(tempI);
            if(str.length()<2){
                sb.append(0).append(str);//长度不足两位，补齐：如16进制的d,用0d表示。
            }else{
                sb.append(str);
            }
        }
        return sb.toString();
    }
    
    public static void main(String args[]) {  
        System.out.println(hexToStr("5a4554412b514e39303230"));  
        System.out.println(hexToStr("303030313032303330343035"));
        System.out.println(strToHex("000102030405060708090A0B0C0D0E0F"));
    } 
}
