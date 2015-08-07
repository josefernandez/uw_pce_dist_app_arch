package com.nozama.messaging.holds;

import java.io.Serializable;

public class CancelHoldRequest extends HoldRequest implements Serializable {
	private static final long serialVersionUID = -3034546136096565955L;

	private long itemId;
	
	public CancelHoldRequest(String orderId, long itemId) {
		super(orderId);
		this.itemId = itemId;
	}
	
	public long getItemId() {
		return itemId;
	}

}
