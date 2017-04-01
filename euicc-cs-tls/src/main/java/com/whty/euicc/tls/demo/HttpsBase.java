package com.whty.euicc.tls.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
/**
 * https父类
 * @author Administrator
 *
 */
public class HttpsBase {
	static PrivateKey privateKey;  
    static PublicKey publicKey;  
  
  
    public static boolean byteEquals(byte a[],byte[] b){  
        boolean equals=true;  
        if(a==null || b==null){  
            equals=false;  
        }  
  
        if(a!=null && b!=null){  
            if(a.length!=b.length){  
                equals=false;  
            }else{  
                for(int i=0;i<a.length;i++){  
                    if(a[i]!=b[i]){  
                        equals=false;  
                        break;  
                    }  
                }  
            }  
  
        }  
        return equals;  
    }  
  
    public static byte[] decrypt(byte data[]) throws Exception{  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        return cipher.doFinal(data);  
    }  
  
    public static byte[] decrypt(byte data[],SecureRandom seed) throws Exception{  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, privateKey,seed);  
        return cipher.doFinal(data);  
    }  
  
    public static byte[] decryptByPublicKey(byte data[],SecureRandom seed) throws Exception{  
        if(publicKey==null){  
            publicKey=CertifcateUtils.readPublicKeys();  
        }  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());  
        if(seed==null){  
            cipher.init(Cipher.DECRYPT_MODE, publicKey);  
        }else{  
            cipher.init(Cipher.DECRYPT_MODE, publicKey,seed);  
        }  
  
        return cipher.doFinal(data);  
    }  
  
    public static byte[] decryptByDes(byte data[],SecureRandom seed) throws Exception{  
        if(publicKey==null){  
            publicKey=CertifcateUtils.readPublicKeys();  
        }  
        // 对数据解密  
        Cipher cipher = Cipher.getInstance("DES");  
        if(seed==null){  
            cipher.init(Cipher.DECRYPT_MODE, publicKey);  
        }else{  
            cipher.init(Cipher.DECRYPT_MODE, publicKey,seed);  
        }  
  
        return cipher.doFinal(data);  
    }  
  
  
  
  
    public static byte[] encryptByPublicKey(byte[] data, SecureRandom seed)  
            throws Exception {  
        if(publicKey==null){  
            publicKey=CertifcateUtils.readPublicKeys();  
        }  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());  
        if(seed==null){  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        }else{  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey,seed);  
        }  
  
        return cipher.doFinal(data);  
    }  
  
    public static String byte2hex(byte[] b) {  
        String hs = "";  
        String stmp = "";  
        for (int n = 0; n < b.length; n++) {  
            stmp = (Integer.toHexString(b[n] & 0XFF));  
            if (stmp.length() == 1) {  
                hs = hs + "0" + stmp;  
            } else {  
                hs = hs +"  " + stmp;  
            }  
        }  
        return hs.toUpperCase();  
    }  
  
    public static byte[] cactHash(byte[] bytes) {  
        byte[] _bytes = null;  
        try {  
            MessageDigest md = MessageDigest.getInstance("SHA1");  
            md.update(bytes);  
            _bytes = md.digest();  
        } catch (NoSuchAlgorithmException ex) {  
            ex.printStackTrace();  
        }  
        return _bytes;  
    }  
  
  
  
    static String random(){  
        StringBuilder builder=new StringBuilder();  
        Random random=new Random();  
        int seedLength=10;  
        for(int i=0;i<seedLength;i++){  
            builder.append(digits[random.nextInt(seedLength)]);  
        }  
  
        return builder.toString();  
    }  
  
    static char[] digits={  
            '0','1','2','3','4',  
            '5','6','7','8','9',  
            'a','b','c','d','e',  
            'f','g','h','i','j'  
    };  
}
