package com.nozama.custemailer;

import com.nozama.messaging.checkout.CheckoutListener;

// make this a message driven bean
public class CustomerEmailer extends CheckoutListener {

	public void handleCheckout(String orderId) {
		// this throws an exception so you can check the logs
		// to make sure it works
		throw new CustomerEmailerCheckoutReceivedException(orderId);
	}

}
