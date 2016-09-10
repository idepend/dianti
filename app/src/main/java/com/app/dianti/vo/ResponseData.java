package com.app.dianti.vo;

import java.util.List;
import java.util.Map;

public class ResponseData {
	private String code;
	private String msg;
	private Map<String, Object> data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getDataString(String key) {
		Object object = data.get(key);
		if (object == null) {
			return "";
		} else {
			return data.get(key).toString();
		}
	}

	public Integer getDataInt(String key) {
		Object object = data.get(key);
		if (object == null) {
			return null;
		} else {
			return Integer.parseInt(data.get(key).toString());
		}
	}

	public Long getDataLong(String key) {
		Object object = data.get(key);
		if (object == null) {
			return null;
		} else {
			return Long.parseLong(data.get(key).toString());
		}
	}

	public List<Map<String, Object>> getDataMap(String key) {
		return (List<Map<String, Object>>) data.get(key);
	}
}
