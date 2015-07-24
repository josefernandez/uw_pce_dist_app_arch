package com.nozama.messaging.holds;

import java.io.Serializable;

public abstract class HoldRequest implements Serializable {
	private static final long serialVersionUID = -3034546136096565955L;

	private String orderId;
	
	public HoldRequest(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

}
