package com.example.temp.common.encryption;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES256Cipher {
	
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };


	public static String AES_Encode(String str, String key)	throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,	IllegalBlockSizeException, BadPaddingException {
		
		byte[] textBytes = str.getBytes("UTF-8");
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		     SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		     Cipher cipher = null;
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
		return Base64.encodeBase64String(cipher.doFinal(textBytes));
	}

	public static String AES_Decode(String str, String key)	throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		byte[] textBytes = Base64.decodeBase64(str);
		//byte[] textBytes = str.getBytes("UTF-8");
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
		return new String(cipher.doFinal(textBytes), "UTF-8");
	}
	
//	public static String aes256Encode(String uploadFilePath, String inputFileName, String outputFileName, String key) throws IOException {
//		InputStream input = null;
//		OutputStream output = null;
//
//		SecretKeySpec keyspc = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
//		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
//
//		try {
//
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//			cipher.init(Cipher.ENCRYPT_MODE, keyspc, ivSpec);
//
//			input = new BufferedInputStream(new FileInputStream(uploadFilePath+inputFileName));
//    	    output = new BufferedOutputStream(new FileOutputStream(uploadFilePath+outputFileName, false));
//
//   	    	byte[] buffer = new byte[1024];
//  	    	int read = -1;
//        	while ((read = input.read(buffer)) != -1) {
//            	output.write(cipher.update(buffer, 0, read));
//        	}
//		} catch (Exception e) {
//			logger.error("Exception", e);
//			return "FAIL";
//		} 
//		input.close();
//		output.close();
//		return "SUCCESS";
//	}
//	
//
//	public static String aes256Decode(String savedFilePath, String downTargetName, String outputFileName, String key) throws IOException {
//		InputStream input = null;
//		OutputStream output = null;
//
//		SecretKeySpec keyspc = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
//		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
//
//		try {
//
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//			cipher.init(Cipher.DECRYPT_MODE, keyspc, ivSpec);
//
//			input = new BufferedInputStream(new FileInputStream(savedFilePath + downTargetName));
//			output = new BufferedOutputStream(new FileOutputStream(savedFilePath + outputFileName));
//
//			byte[] buffer = new byte[1024];
//			int read = -1;
//			while ((read = input.read(buffer)) != -1) {
//				output.write(cipher.update(buffer, 0, read));
//			}
//		} catch (Exception e) {
//			logger.error("Exception", e);
//			return "FAIL";
//		} 
//		input.close();
//		output.close();
//		return "SUCCESS";
//	}
}