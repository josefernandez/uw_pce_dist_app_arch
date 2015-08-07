package com.nozama.inventory;

public class InventoryCheckoutReceivedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InventoryCheckoutReceivedException(String orderId) {
		super(orderId);
	}

}
