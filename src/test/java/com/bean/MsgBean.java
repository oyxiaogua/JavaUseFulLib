package com.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class MsgBean {
	private Long globalId;
	private Long __time__;

	public Long getGlobalId() {
		return globalId;
	}

	public void setGlobalId(Long globalId) {
		this.globalId = globalId;
	}
	 @JSONField(name="time2_")  
	public Long get__time__() {
		return __time__;
	}
	@JSONField(name="time_")
	public void set__time__(Long __time__) {
		this.__time__ = __time__;
	}

	public String toString() {
		return "MsgBean [globalId=" + globalId + ", __time__=" + __time__ + "]";
	}

}
