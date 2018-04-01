package com.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderBean {

	@Expose(serialize = false)
	@SerializedName("user_id")
	private String userId;

	@Expose(deserialize = false)
	@SerializedName("goods_id")
	private String goodsId;

	private int price;
	@Expose
	@SerializedName("order_no")
	private String orderNo;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String toString() {
		return "OrderBean [userId=" + userId + ", goodsId=" + goodsId + ", price=" + price + ", orderNo=" + orderNo
				+ "]";
	}

}
