package com.nozama.messaging.checkout;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public abstract class CheckoutListener implements MessageListener {

	public abstract void handleCheckout(String orderId);
	
    @Override
    public void onMessage(Message msg) {
    	TextMessage tm = (TextMessage) msg;

    	try {
			handleCheckout(tm.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }

}
