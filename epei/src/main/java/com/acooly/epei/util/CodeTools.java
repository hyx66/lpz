/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.acooly.epei.util;

import java.math.BigDecimal;

public class CodeTools {
	public static String encode(String content, String eid) {
		return AESCoder.AESEncode(getKey() + eid, content);
	}

	public static String dncode(String sky, String eid) {
		return AESCoder.AESDncode(getKey() + eid, sky);
	}

	private static String getKey() {
		String key = "ghkle#&45801lk#12*we1a0p";
		return key;
	}

	//2016-12-13新增
	public static String add(String a, String b, String eid){
		BigDecimal sumab = new BigDecimal(dncode(a,eid)).add(new BigDecimal(dncode(b,eid)));
		return encode(sumab.toString(),eid);
	}
	
	//2016-12-13新增
	public static String subtract(String big, String small, String eid){
		System.out.println("差数："+dncode(big,eid));
		System.out.println(dncode(small,eid));
		BigDecimal subbigsmall = new BigDecimal(dncode(big,eid)).subtract(new BigDecimal(dncode(small,eid)));
		System.out.println(subbigsmall);
		return encode(subbigsmall.toString(),eid);
	}
	
	public static void main(String[] d) {
		String c = "78962.02";
		String userId = "11000000000000000";
		System.out.println(c);
		String ccc = encode(c, userId);
		System.out.println(ccc);
		System.out.println(dncode(ccc, userId));
	}
}