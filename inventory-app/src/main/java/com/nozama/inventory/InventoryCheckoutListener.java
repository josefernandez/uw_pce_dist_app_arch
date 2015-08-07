package com.nozama.inventory;

import com.nozama.messaging.checkout.CheckoutListener;

//make this a message driven bean
public class InventoryCheckoutListener extends CheckoutListener {

	//FIXME:
	private InventoryService serv;

	public void handleCheckout(String orderId) {
		serv.processHolds(orderId);

		// this throws an exception so you can check the logs
		// to make sure it works
		throw new InventoryCheckoutReceivedException(orderId);
	}

}
