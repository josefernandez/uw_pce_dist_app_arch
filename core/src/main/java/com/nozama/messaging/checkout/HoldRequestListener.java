package com.nozama.messaging.checkout;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.nozama.messaging.holds.HoldRequest;

public abstract class HoldRequestListener implements MessageListener {

	public abstract void handleHoldRequest(HoldRequest hr);
	
    @Override
    public void onMessage(Message msg) {
		try {
	    	HoldRequest hr = msg.getBody(HoldRequest.class);
	    	handleHoldRequest(hr);
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }

}
