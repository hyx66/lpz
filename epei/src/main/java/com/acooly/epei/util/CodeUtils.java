package com.acooly.epei.util;

import com.acooly.core.utils.Ids;
import com.acooly.epei.domain.OrderOriginalEnum;

import java.util.Random;

/**
 * 修订记录：
 * liubin 2015-10-27 10:22 创建
 */
public class CodeUtils {

	private static final String LOWER_CAHR_NUMBER = "0123456789";

	/**
	 * 根据订单的来源生成订单编号
	 * @param originalEnum
	 * @return
	 */
	public static String getOrderNo(OrderOriginalEnum originalEnum){
		String strNo = Ids.getDid();
		String noExcludeIp = strNo.substring(0,strNo.length()-6)+strNo.substring(strNo.length()-3);
		String noHasOrignal = noExcludeIp+(originalEnum.ordinal()+1);
		return  noHasOrignal;
	}

	public static String getRandCode(int codeLength){
		final StringBuffer sb = new StringBuffer();
		final Random random = new Random();

		for (int i = 0; i < codeLength; i++) {
			sb.append(LOWER_CAHR_NUMBER.charAt(random.nextInt(LOWER_CAHR_NUMBER.length())));
		}

		return sb.toString();
	}
}
