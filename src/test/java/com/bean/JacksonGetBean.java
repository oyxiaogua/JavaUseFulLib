package com.bean;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class JacksonGetBean {

	private long id;
	private Map<String, Object> dataMap;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonAnyGetter
	public void setOtherKeyToMap(String key,Object value){
		dataMap.put(key, value);
	}
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	@JsonAnySetter
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public String toString() {
		return "JacksonGetBean [id=" + id + ", dataMap=" + dataMap + "]";
	}

	
}
