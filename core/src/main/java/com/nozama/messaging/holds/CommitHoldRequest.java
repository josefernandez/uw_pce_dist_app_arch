package com.nozama.messaging.holds;

import java.io.Serializable;

public class CommitHoldRequest extends HoldRequest implements Serializable {
	private static final long serialVersionUID = -3034546136096565955L;
	
	public CommitHoldRequest(String orderId) {
		super(orderId);
	}

}
