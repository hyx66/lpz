/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.acooly.epei.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESCoder {
	public static String AESEncode(String encodeRules, String content) {
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("AES");

			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(encodeRules.getBytes());
			keygen.init(128, secureRandom);

			SecretKey original_key = keygen.generateKey();

			byte[] raw = original_key.getEncoded();

			SecretKey key = new SecretKeySpec(raw, "AES");

			Cipher cipher = Cipher.getInstance("AES");

			cipher.init(1, key);

			byte[] byte_encode = content.getBytes("utf-8");

			byte[] byte_AES = cipher.doFinal(byte_encode);

			String AES_encode = new String(Coder.encryptBASE64(byte_AES));

			return AES_encode;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String AESDncode(String encodeRules, String content) {
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("AES");

			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(encodeRules.getBytes());
			keygen.init(128, secureRandom);

			SecretKey original_key = keygen.generateKey();

			byte[] raw = original_key.getEncoded();

			SecretKey key = new SecretKeySpec(raw, "AES");

			Cipher cipher = Cipher.getInstance("AES");

			cipher.init(2, key);

			byte[] byte_content = Coder.decryptBASE64(content);

			byte[] byte_decode = cipher.doFinal(byte_content);
			String AES_decode = new String(byte_decode, "utf-8");
			return AES_decode;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("使用AES对称加密，请输入加密的规则");
		byte[] encodeRules1 = Coder.encryptHMAC("324214fewrew".getBytes(),
				"123");

		String encodeRules = encodeRules1.toString();
		String content = "-88888.2233333333333333";
		String en = AESEncode(encodeRules, content);
		System.out.println("长度：" + en.length());
		System.out.println("根据输入的规则:" + encodeRules + ":加密后的密文是:" + en);

		String de = AESDncode(encodeRules, en);
		System.out.println("根据输入的规则" + encodeRules + "解密后的明文是:"
				+ AESDncode(encodeRules, en));
		System.out.println(de.equals(content));
	}
}