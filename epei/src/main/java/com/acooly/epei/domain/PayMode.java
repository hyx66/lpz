package com.acooly.epei.domain;

/**
 * 修订记录：
 * liubin 2015-11-23 13:59 创建
 */
public enum PayMode {
	/**
	 * 首单免费
	 */
	FIRST_FREE("FIRST_FREE","首单免费"),
	/**
	 * 线下收取
	 */
	OFFLINE("OFFLINE","线下收取");

	/**
	 * 枚举值
	 */
	private final String code;

	/**
	 * 枚举描述
	 */
	private final String message;

	/**
	 * @param code    枚举值
	 * @param message 枚举描述
	 */
	private PayMode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}

	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}

	/**
	 * 通过枚举<code>code</code>获得枚举
	 *
	 * @param code
	 * @return PayMode
	 */
	public static PayMode getByCode(String code) {
		for (PayMode _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举
	 *
	 * @return List<PayMode>
	 */
	public static java.util.List<PayMode> getAllEnum() {
		java.util.List<PayMode> list = new java.util.ArrayList<PayMode>(values().length);
		for (PayMode _enum : values()) {
			list.add(_enum);
		}
		return list;
	}

	/**
	 * 获取全部枚举值
	 *
	 * @return List<String>
	 */
	public static java.util.List<String> getAllEnumCode() {
		java.util.List<String> list = new java.util.ArrayList<String>(values().length);
		for (PayMode _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}

	/**
	 * 通过code获取msg
	 *
	 * @param code 枚举值
	 * @return
	 */
	public static String getMsgByCode(String code) {
		if (code == null) {
			return null;
		}
		PayMode _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getMessage();
	}

	/**
	 * 获取枚举code
	 *
	 * @param _enum
	 * @return
	 */
	public static String getCode(PayMode _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
