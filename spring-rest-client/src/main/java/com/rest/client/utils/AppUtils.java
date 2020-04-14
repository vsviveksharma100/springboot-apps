package com.rest.client.utils;

public class AppUtils {

	/**
	 * Validated emptiness of String
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isBlankStr(String value) {
		return (value == null || value.isEmpty());
	}

	/**
	 * Validates String is not empty
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNotBlankStr(String value) {
		return (!isBlankStr(value));
	}
}
