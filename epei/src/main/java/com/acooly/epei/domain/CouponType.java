package com.acooly.epei.domain;

/**
 * 修订记录：
 * liubin 2015-10-30 18:13 创建
 */
public enum CouponType {
	/**
	 * 首单免费
	 */
	FIRST_FREE("FIRST_FREE","首单免费");

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
	private CouponType(String code, String message) {
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
	 * @return CouponType
	 */
	public static CouponType getByCode(String code) {
		for (CouponType _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举
	 *
	 * @return List<CouponType>
	 */
	public static java.util.List<CouponType> getAllEnum() {
		java.util.List<CouponType> list = new java.util.ArrayList<CouponType>(values().length);
		for (CouponType _enum : values()) {
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
		for (CouponType _enum : values()) {
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
		CouponType _enum = getByCode(code);
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
	public static String getCode(CouponType _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
