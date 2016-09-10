package com.app.dianti.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	/**
	 * yyyy-MM-dd
	 * @param second
	 * @return
     */
	public static String secondToDateStr(String second) {
		String format;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			date.setTime(Long.parseLong(second) * 1000);
			format = dateFormat.format(date);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "";
		}
		return format;
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @param second
	 * @return
     */
	public static String secondToDateStr2(String second) {
		String format;
		try {
			if("".equals(second) || "0".equals(second)){
				return "";
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			date.setTime(Long.parseLong(second) * 1000);
			format = dateFormat.format(date);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "";
		}
		return format;
	}

	public static String unixTimeStamp2Date(String timestampString, String formats) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new SimpleDateFormat(formats).format(new Date(timestamp));
		return date;
	}
}
