package com.app.dianti.util;

import java.util.HashMap;
import java.util.Map;

public class DataUtil {
	public static String mapGetString(Map<String, Object> data, String key) {
		if (data == null) {
			return "";
		}

		Object obj = data.get(key);
		if (obj == null) {
			return "";
		}

		return obj.toString();

	}

	public static Map<String, String> getColMap(String name, String value){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("value", value);
		return map;
	}
}
