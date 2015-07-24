package com.nozama.messaging.holds;

import java.io.Serializable;

public class HoldResponse implements Serializable {
	private static final long serialVersionUID = -3034546136096565955L;

	private String orderId;
	private long itemId;
	private HoldResponseStatus status;
	
	public HoldResponse(String orderId, long itemId, HoldResponseStatus status) {
		this.orderId = orderId;
		this.itemId = itemId;
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}
	
	public long getItemId() {
		return itemId;
	}

	public HoldResponseStatus getStatus() {
		return status;
	}

}
