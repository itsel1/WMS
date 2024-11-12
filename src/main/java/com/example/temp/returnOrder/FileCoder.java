package com.example.temp.returnOrder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;


public class FileCoder {

	private static final String algorithm = "AES";
	private static final String transformation = algorithm+"/CBC/PKCS5Padding";
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	
	private Key key;
	
	public FileCoder(Key key) {
		this.key = key;
	}
	
	public void encrypt(File source, File dest) throws Exception {
		crypt(Cipher.ENCRYPT_MODE, source, dest);
	}
	
	public void decrypt(File source, File dest) throws Exception {
		crypt(Cipher.DECRYPT_MODE, source, dest);
	}

	public void encrypt(InputStream is, OutputStream os) throws Exception {
		crypt(Cipher.ENCRYPT_MODE, is, os);
	}
	
	public void decrypt(InputStream is, OutputStream os) throws Exception {
		crypt(Cipher.DECRYPT_MODE, is, os);
	}
	
	private void crypt(int mode, InputStream is, OutputStream os) throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(mode, key);
		
		InputStream input = is;
		OutputStream output = os;
		
		byte[] buffer = new byte[1024];
		int read = -1;
		while((read=input.read(buffer)) != -1) {
			output.write(cipher.update(buffer, 0, read));
		}
		output.write(cipher.doFinal());
	}
	
	private void crypt(int mode, File source, File dest) throws Exception {
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		Cipher cipher = Cipher.getInstance(transformation);
		//cipher.init(mode, key);
		cipher.init(mode, key, ivSpec);
		InputStream input = null;
		OutputStream output = null;
		
		try {
			input = new BufferedInputStream(new FileInputStream(source));
			output = new BufferedOutputStream(new FileOutputStream(dest));
			byte[] buffer = new byte[1024];
			int read = -1;
			while((read = input.read(buffer)) != -1) {
				output.write(cipher.update(buffer, 0, read));
			}
			output.write(cipher.doFinal());
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
