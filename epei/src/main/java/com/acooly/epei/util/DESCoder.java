/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.acooly.epei.util;

import java.io.PrintStream;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESCoder extends Coder {
	public static final String ALGORITHM = "DES";

	private static Key toKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}

	public static byte[] decrypt(byte[] data, String key) throws Exception {
		Key k = toKey(decryptBASE64(key));
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(2, k);
		return cipher.doFinal(data);
	}

	public static byte[] encrypt(byte[] data, String key) throws Exception {
		Key k = toKey(decryptBASE64(key));
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(1, k);
		return cipher.doFinal(data);
	}

	public static String initKey() throws Exception {
		return initKey(null);
	}

	public static String initKey(String seed) throws Exception {
		SecureRandom secureRandom = null;
		if (seed != null)
			secureRandom = new SecureRandom(decryptBASE64(seed));
		else {
			secureRandom = new SecureRandom();
		}
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(secureRandom);
		SecretKey secretKey = kg.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	public static void main(String[] d) throws Exception {
		String inputStr = "-89.05";
		String key = initKey("2143213213");
		System.err.println("原文:\t" + inputStr);
		System.err.println("密钥:\t" + key);

		byte[] inputData = inputStr.getBytes();
		inputData = encrypt(inputData, key);
		String dd = encryptBASE64(inputData);

		System.err.println("加密后:\t" + dd);

		byte[] outputData = decrypt(decryptBASE64(dd), key);
		String outputStr = new String(outputData);

		System.err.println("解密后:\t" + outputStr);
	}
}