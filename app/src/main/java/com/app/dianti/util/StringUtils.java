package com.app.dianti.util;

import java.util.List;

public class StringUtils {
	public static boolean isBlank(String str) {
		if (str == null || str.equals("") || str.trim().equals("")) {
			return true;
		}

		return false;
	}

	public static boolean isNotBlank(String str) {
		if (str != null && !str.equals("") && !str.trim().equals("")) {
			return true;
		}

		return false;
	}
	
	public static String listJoin(List<String> checkItemList){
		StringBuilder sb = new StringBuilder();
		for (Object object : checkItemList) {
			sb.append(",").append(object.toString());
		}
		
		return sb.substring(1, sb.length());
	}
	
}
