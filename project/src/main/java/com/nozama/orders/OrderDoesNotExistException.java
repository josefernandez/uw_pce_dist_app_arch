package com.nozama.orders;

public class OrderDoesNotExistException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OrderDoesNotExistException(String orderId) {
		super(orderId);
	}

}
