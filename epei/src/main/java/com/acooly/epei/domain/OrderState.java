package com.acooly.epei.domain;

/**
 * 修订记录：
 * liubin 2015-10-27 11:27 创建
 */
public enum OrderState {
	ORDERED("ORDERED","预约"),
	CANCELED("CANCELED","取消"),
	CONFIRMED("CONFIRMED","确认"),
	FINISHED("FINISHED","完成");

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
	private OrderState(String code, String message) {
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
	 * @return OrderState
	 */
	public static OrderState getByCode(String code) {
		for (OrderState _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举
	 *
	 * @return List<OrderState>
	 */
	public static java.util.List<OrderState> getAllEnum() {
		java.util.List<OrderState> list = new java.util.ArrayList<OrderState>(values().length);
		for (OrderState _enum : values()) {
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
		for (OrderState _enum : values()) {
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
		OrderState _enum = getByCode(code);
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
	public static String getCode(OrderState _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
