package com.nozama.messaging.checkout;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class CheckoutListener implements MessageListener {
	private CheckoutHandler handler;
	
	public CheckoutListener(CheckoutHandler handler) {
		this.handler = handler;
	}
	
    @Override
    public void onMessage(Message msg) {
    	TextMessage tm = (TextMessage) msg;

    	try {
			handler.handleCheckout(tm.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }
}
