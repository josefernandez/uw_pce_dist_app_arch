package com.nozama.tests.assignment4;

import static com.jayway.restassured.RestAssured.*;
import static org.junit.Assert.*;

import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import com.jayway.restassured.response.Response;

public class OrdersIntegrationTest {
	private static long itemId = 20L;
	
	private String createOrder() {
		Response r = post("orders/rest/order");
		assertEquals(Status.CREATED.getStatusCode(), r.getStatusCode());
		return r.getBody().asString();
	}
	
	private void addItemToOrder(String orderId, long itemId, Status status) {
		expect().
			statusCode(status.getStatusCode()).
		when().
			put("orders/rest/order/" + orderId + "/item/" + itemId);
	}
	
	private void removeItemFromOrder(String orderId, long itemId, Status status) {
		expect().
			statusCode(status.getStatusCode()).
		when().
			delete("orders/rest/order/" + orderId + "/item/" + itemId);
	}
	
	private void checkout(String orderId) {
		expect().
			statusCode(Status.NO_CONTENT.getStatusCode()).
		when().
			put("orders/rest/order/" + orderId + "/submit");
			
	}
	
	@Test
	public void testCannotOrderTwoOfTheSameItem() {
		final long item = itemId++;
		String orderId = createOrder();
		addItemToOrder(orderId, item, Status.NO_CONTENT);
		addItemToOrder(orderId, item, Status.CONFLICT);
	}
	
	@Test
	public void testCanCommitHoldsAfterCheckout() {
		final long item1 = itemId++;
		final long item2 = itemId++;
		
		String order1 = createOrder();
		addItemToOrder(order1, item1, Status.NO_CONTENT);
		addItemToOrder(order1, item2, Status.NO_CONTENT);
		checkout(order1);
		
		String order2 = createOrder();
		addItemToOrder(order2, item1, Status.CONFLICT);
	}
	
	@Test
	public void testCanCancelHolds() {
		final long item = itemId++;

		String order1 = createOrder();
		addItemToOrder(order1, item, Status.NO_CONTENT);
		removeItemFromOrder(order1, item, Status.NO_CONTENT);
		addItemToOrder(order1, item, Status.NO_CONTENT);
		removeItemFromOrder(order1, item, Status.NO_CONTENT);

		String order2 = createOrder();
		addItemToOrder(order2, item, Status.NO_CONTENT);
	}

}
