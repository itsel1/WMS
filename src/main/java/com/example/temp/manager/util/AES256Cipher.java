package com.example.temp.manager.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import javax.crypto.Cipher;

public class AES256Cipher {
	
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	public static String aes256Encode(String uploadFilePath, String inputFileName, String outputFileName, String key) throws IOException {
		InputStream input = null;
		OutputStream output = null;
		
		SecretKeySpec keyspc = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
	
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keyspc, ivSpec);
			
			input = new BufferedInputStream(new FileInputStream(uploadFilePath+inputFileName));
			output = new BufferedOutputStream(new FileOutputStream(uploadFilePath+outputFileName, false));
			
			byte[] buffer = new byte[1024];
			int read = -1;
			while ((read = input.read(buffer)) != -1) {
				output.write(cipher.update(buffer, 0, read));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		}
		
		input.close();
		output.close();
		return "SUCCESS";
	}
	
	public static String aes256Decode(String savedFilePath, String downTargetName, String outputFileName, String key) throws IOException {
		InputStream input = null;
		OutputStream output = null;

		SecretKeySpec keyspc = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

		try {

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keyspc, ivSpec);

			input = new BufferedInputStream(new FileInputStream(savedFilePath + downTargetName));
			output = new BufferedOutputStream(new FileOutputStream(savedFilePath + outputFileName));

			byte[] buffer = new byte[1024];
			int read = -1;
			while ((read = input.read(buffer)) != -1) {
				output.write(cipher.update(buffer, 0, read));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		} 
		input.close();
		output.close();
		return "SUCCESS";
	}
}
