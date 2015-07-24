package com.nozama.inventory;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.Topic;

import com.nozama.messaging.JMSInfo;
import com.nozama.messaging.checkout.CheckoutHandler;
import com.nozama.messaging.checkout.CheckoutListener;
import com.nozama.messaging.holds.CancelHoldRequest;
import com.nozama.messaging.holds.CommitHoldRequest;
import com.nozama.messaging.holds.HoldRequest;
import com.nozama.messaging.holds.HoldResponse;
import com.nozama.messaging.holds.HoldResponseStatus;
import com.nozama.messaging.holds.PlaceHoldRequest;

public class Inventory implements CheckoutHandler {

	private InventoryService serv;

	public Inventory(InventoryService serv) {
		this.serv = serv;
	}
	
	public void start() {
		//TODO: add CheckoutListener(this) to the checkouts topic
		
		while (true) {
			HoldRequest h = //TODO: get a hold request in a blocking fashion
			handleHold(h);
		}
	}
	
	private void handleHold(HoldRequest h) {
		if (h instanceof PlaceHoldRequest) {
			PlaceHoldRequest phr = (PlaceHoldRequest) h;
			System.out.println("Placing hold request: " + phr.getOrderId() + " : " + phr.getItemId());

			if (serv.placeHold(phr.getOrderId(), phr.getItemId())) {
				confirmHold(phr.getOrderId(), phr.getItemId());
			} else {
				denyHold(phr.getOrderId(), phr.getItemId());
			}
		} else if (h instanceof CancelHoldRequest) {
			CancelHoldRequest chr = (CancelHoldRequest) h;
			System.out.println("Cancelling hold request: " + chr.getOrderId() + " : " + chr.getItemId());
			serv.clearHold(chr.getOrderId(), chr.getItemId());
		} else if (h instanceof CommitHoldRequest) {
			System.out.println("Committing hold request: " + h.getOrderId());
			serv.processHolds(h.getOrderId());
		} else {
			throw new IllegalStateException("Message is unknown");
		}
	}

	private void confirmHold(String orderId, long itemId) {
		//TODO: add a hold response to the hold response queue confirming the hold
	}

	private void denyHold(String orderId, long itemId) {
		//TODO: add a hold response to the hold response queue denying the hold
	}
	
	public void handleCheckout(String orderId) {
		System.out.println("CheckoutListener received message: " + orderId);
		serv.processHolds(orderId);
	}

	public static void main(String[] args) {
		Inventory inv = new Inventory(new InventoryServiceImpl());
		inv.start();
	}

}
