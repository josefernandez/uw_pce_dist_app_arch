package com.nozama.custemailer;

public class CustomerEmailerCheckoutReceivedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CustomerEmailerCheckoutReceivedException(String orderId) {
		super(orderId);
	}

}
