package com.example.data;

import java.io.Serializable;

public class Order implements Serializable {
	
	private String orderId;
	private String address;
	private float orderValue;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public float getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(float orderValue) {
		this.orderValue = orderValue;
	}
	

}
