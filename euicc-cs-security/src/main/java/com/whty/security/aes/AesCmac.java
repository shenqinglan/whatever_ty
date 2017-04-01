package com.whty.security.aes;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.whty.security.util.Converts;
/**
 * 计算MAC（CC），校验数据完整性
 * @author shen
 *
 */

public class AesCmac {
	private static final byte CONSTANT = (byte) 0x87;
    private static final int BLOCK_SIZE = 16;//分组长度
    private static final IvParameterSpec ZERO_IV = new IvParameterSpec(new byte[16]);//初始向量


    private int macLength;
    private Cipher aesCipher;

    private byte[] buffer;
    private int bufferCount;

    private byte[] k1;
    private byte[] k2;

    public AesCmac() throws NoSuchAlgorithmException {
        this(BLOCK_SIZE);
    }

    public AesCmac(int length) throws NoSuchAlgorithmException {
        if (length > BLOCK_SIZE) {
            throw new NoSuchAlgorithmException("AES CMAC maximum length is " + BLOCK_SIZE);
        }

        try {
            macLength = length;
            aesCipher = Cipher.getInstance("AES/CBC/NOPADDING");
            buffer = new byte[BLOCK_SIZE];
        } catch (NoSuchPaddingException nspe) {
            nspe.printStackTrace();
        }
    }
    public String calcuCmac( String inputData, String nKey) throws Exception{
    	byte[]inputs = Converts.stringToBytes(inputData);
    	byte[]masterKey = Converts.stringToBytes(nKey);
    	SecretKey key = new SecretKeySpec(masterKey, "AES");
    	init(key);
    	updateBlock(inputs); 
    	return ((Converts.bytesToHex(doFinal())).toUpperCase()).substring(0, 16);
    	
    	
    	
    }
    public String calcuCmac1( String inputData, String nKey) throws Exception{
    	byte[]inputs = Converts.stringToBytes(inputData);
    	byte[]masterKey = Converts.stringToBytes(nKey);
    	SecretKey key = new SecretKeySpec(masterKey, "AES");
    	init(key);
    	updateBlock(inputs); 
    	return ((Converts.bytesToHex(doFinal())).toUpperCase()).substring(0, 32);
    	
    	
    	
    }
    /**
     * 生成子密钥
     * @param k用来生成下一个子密钥的密钥
     * @return
     */
    private byte[] doubleSubKey(byte[] k) {
        byte[] ret = new byte[k.length];

        boolean firstBitSet = ((k[0]&0x80) != 0);
        for (int i=0; i<k.length; i++) {
            ret[i] = (byte) (k[i] << 1);
            if (i+1 < k.length && ((k[i+1]&0x80) != 0)) {
                ret[i] |= 0x01;
            }
        }
        if (firstBitSet) {
            ret[ret.length-1] ^= CONSTANT;
        }
        return ret;
    }
    /**
     * 初始化，并获得子密钥k1,k2
     * @param key 安全密钥
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     */
    public final void init(Key key) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (!(key instanceof SecretKeySpec)) {
            throw new InvalidKeyException("Key is not of required type SecretKey.");
        }
        if (!((SecretKeySpec)key).getAlgorithm().equals("AES")) {
            throw new InvalidKeyException("Key is not an AES key.");
        }
        aesCipher.init(Cipher.ENCRYPT_MODE, key,ZERO_IV);

        // 首先计算k0
        byte[] k0 = new byte[BLOCK_SIZE];
        try {
        	
            aesCipher.update(k0, 0, k0.length, k0, 0);
        } catch (ShortBufferException sbe) {}

        // 计算k1,k2的值
        k1 = doubleSubKey(k0);
        k2 = doubleSubKey(k1);

        aesCipher.init(Cipher.ENCRYPT_MODE, key,ZERO_IV);
        bufferCount = 0;
    }

    /**
     * 对数据进行分组处理
     * @param data 数据
     */
    public final void updateBlock(byte[] data) {
        int currentOffset = 0;

        if (data.length < BLOCK_SIZE-bufferCount) {
            System.arraycopy(data, 0, buffer, bufferCount, data.length);
            bufferCount += data.length;
            return;
        } else if (bufferCount > 0) {
            System.arraycopy(data, 0, buffer, bufferCount, BLOCK_SIZE-bufferCount);
            try {
                aesCipher.update(buffer, 0, BLOCK_SIZE, buffer, 0);
            } catch (ShortBufferException sbe) {}
            currentOffset += BLOCK_SIZE-bufferCount;
            bufferCount = 0;
        }

        // 将分组完整的数据存入buffer
        while (currentOffset+BLOCK_SIZE < data.length) {
            try {
                aesCipher.update(data, currentOffset, BLOCK_SIZE, buffer, 0);
            } catch (ShortBufferException sbe) {}
            currentOffset += BLOCK_SIZE;
        }

        // 将分组后余下的不完整的数据存入buffer
        if (currentOffset != data.length) {
            System.arraycopy(data, currentOffset, buffer, 0, data.length-currentOffset);
            bufferCount = data.length-currentOffset;
        }
    }
    /**
     * 计算MAC
     * @return MAC 的值
     */

    public final byte[] doFinal() {
        byte[] subKey = k1;
        if (bufferCount < BLOCK_SIZE) {
            // Add padding and XOR with k2 instead
            buffer[bufferCount] = (byte) 0x80;
            for (int i=bufferCount+1; i<BLOCK_SIZE; i++)
                buffer[i] = (byte) 0x00;
            subKey = k2;
        }
        for (int i=0; i<BLOCK_SIZE; i++) {
            buffer[i] ^= subKey[i];
        }

        // 计算CMAC的值
        try {
            aesCipher.doFinal(buffer, 0, BLOCK_SIZE, buffer, 0);
        }
        // These should never happen because we pad manually
       
        catch (ShortBufferException sbe) {}
        catch (IllegalBlockSizeException ibse) {}
        catch (BadPaddingException ibse) {}
        
       
        bufferCount = 0;

        byte[] mac = new byte[macLength];
        System.arraycopy(buffer, 0, mac, 0, macLength);
        return  mac;
    }

   
	

}
