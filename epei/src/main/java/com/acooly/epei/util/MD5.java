package com.acooly.epei.util;

import java.security.MessageDigest;

import com.acooly.core.utils.mapper.JsonMapper;

public class MD5 {
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * 转换byte到16进制
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5编码
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(resultString.getBytes("UTF-8"));
            resultString = byteArrayToHexString(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }
    
    public static void main(String[] dd){
		String xml = "<xml>"
	   +"<return_code>SUCCESS</return_code><return_msg>OK</return_msg>"
	   +"<appid>wx2421b1c4370ec43b</appid><mch_id>10000100</mch_id>"
	   +"<nonce_str>IITRi8Iabbblz1Jc</nonce_str>"
	   +"<sign>7921E432F65EB8ED0CE9755F0E86D72F</sign><result_code>SUCCESS</result_code>"
	   +"<prepay_id>wx201411101639507cbf6ffd8b0779950874</prepay_id><trade_type>JSAPI</trade_type>"
	   +"</xml>";
		
		UnifiedOrderResult unifiedOrderResult = JaxbUtil.converyToJavaBean(xml, UnifiedOrderResult.class);
		System.out.println(JsonMapper.nonDefaultMapper().toJson(unifiedOrderResult));
    	UnifiedOrderResult d = new UnifiedOrderResult();
    	d.setAppid("qeqweqwe");
    	System.out.println(JaxbUtil.convertToXml(d));
	}

}
