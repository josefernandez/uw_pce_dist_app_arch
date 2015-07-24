package com.nozama.custemailer;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.Topic;

import com.nozama.messaging.JMSInfo;

public class CustomerEmailer {

    private JMSConsumer checkout;
    
    public CustomerEmailer() {

    }
    
    public String next() {
    	return //TODO: get the next message in a blocking fashion
    }
	
	public static void main(String[] args) {
		CustomerEmailer emailer = new CustomerEmailer();

		while (true) {
			System.out.println("Order checked out: " + emailer.next());
		}
	}

}
