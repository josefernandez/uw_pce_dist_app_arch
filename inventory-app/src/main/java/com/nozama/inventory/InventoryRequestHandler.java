package com.nozama.inventory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.nozama.messaging.JMSInfo;
import com.nozama.messaging.checkout.HoldRequestListener;
import com.nozama.messaging.holds.*;

//make this a message driven bean
public class InventoryRequestHandler extends HoldRequestListener {
    private ConnectionFactory holdRespConnFactory;
    private Queue holdRespQueue;
	private JMSProducer holdsResp;

	//FIXME:
	private InventoryService serv;

	public InventoryRequestHandler() {
		try {
			holdRespQueue = (Queue) InitialContext.doLookup(JMSInfo.HOLD_RESPONSE_QUEUE);
			holdRespConnFactory = (ConnectionFactory) InitialContext.doLookup(JMSInfo.HOLD_RESPONSE_CONNECTION_FACTORY);
			holdsResp = holdRespConnFactory.createContext().createProducer();
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void handleHoldRequest(HoldRequest h) {
		if (h instanceof PlaceHoldRequest) {
			PlaceHoldRequest phr = (PlaceHoldRequest) h;

			if (serv.placeHold(phr.getOrderId(), phr.getItemId())) {
				confirmHold(phr.getOrderId(), phr.getItemId());
			} else {
				denyHold(phr.getOrderId(), phr.getItemId());
			}
		} else if (h instanceof CancelHoldRequest) {
			CancelHoldRequest chr = (CancelHoldRequest) h;
			serv.clearHold(chr.getOrderId(), chr.getItemId());
		} else if (h instanceof CommitHoldRequest) {
			serv.processHolds(h.getOrderId());
		} else {
			throw new IllegalStateException("Message is unknown");
		}
	}

	private void confirmHold(String orderId, long itemId) {
		HoldResponse hold = new HoldResponse(orderId, itemId, HoldResponseStatus.CONFIRM);
		holdsResp.send(holdRespQueue, hold);
	}

	private void denyHold(String orderId, long itemId) {
		HoldResponse hold = new HoldResponse(orderId, itemId, HoldResponseStatus.DENY);
		holdsResp.send(holdRespQueue, hold);
	}

}
