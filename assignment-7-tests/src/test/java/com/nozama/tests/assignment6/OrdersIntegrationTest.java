package com.nozama.tests.assignment6;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import com.nozama.inventory.Hold;

public class OrdersIntegrationTest extends OrderCalls {
	
	@Before
	public void setUp() {
		nuke();
	}
	
	@Test
	public void testCannotOrderTwoOfTheSameItem() {
		System.out.println("-------------------------------------");
		final long item = 1L;
		String orderId = createOrder();
		addItemToOrder(orderId, item, Status.NO_CONTENT);
		addItemToOrder(orderId, item, Status.CONFLICT);
	}
	
	@Test
	public void testCanCommitHoldsAfterCheckout() {
		System.out.println("-------------------------------------");
		final long item1 = 1L;
		final long item2 = 2L;
		
		String order1 = createOrder();
		addItemToOrder(order1, item1, Status.NO_CONTENT);
		addItemToOrder(order1, item2, Status.NO_CONTENT);
		checkout(order1);
		
		String order2 = createOrder();
		addItemToOrder(order2, item1, Status.CONFLICT);
	}
	
	@Test
	public void testCanCancelHolds() {
		System.out.println("-------------------------------------");
		final long item = 1L;

		String order1 = createOrder();
		addItemToOrder(order1, item, Status.NO_CONTENT);
		removeItemFromOrder(order1, item, Status.NO_CONTENT);
		addItemToOrder(order1, item, Status.NO_CONTENT);
		removeItemFromOrder(order1, item, Status.NO_CONTENT);

		String order2 = createOrder();
		addItemToOrder(order2, item, Status.NO_CONTENT);
	}
	
	@Test
	public void testOrdersDeleteWorksNow() {
		System.out.println("-------------------------------------");
		String order = createOrder();
		
		// confirm order creation
		expect().
			statusCode(Status.OK.getStatusCode()).
		when().
			get("orders/rest/order/" + order);
		
		// use admin order deletion
		given().
			auth().basic("goodUser", "password").
		expect().
			statusCode(Status.NO_CONTENT.getStatusCode()).
		when().
			delete("orders/rest/orders/" + order);
		
		// confirm order is gone
		expect().
			statusCode(Status.NOT_FOUND.getStatusCode()).
		when().
			get("orders/rest/order/" + order);
	}
	
	@Test
	public void testHoldsCanBeRetrieved() throws MalformedURLException {
		System.out.println("-------------------------------------");
		long order1item1 = 1L;
		long order1item2 = 2L;
		long order2item1 = 3L;
		
		String order1 = createOrder();
		addItemToOrder(order1, order1item1, Status.NO_CONTENT);
		addItemToOrder(order1, order1item2, Status.NO_CONTENT);

		String order2 = createOrder();
		addItemToOrder(order2, order2item1, Status.NO_CONTENT);

		// check holds
		Hold[] holds = get("orders/rest/orders/holds").as(Hold[].class);
		
		List<Long> holds1 = holds[0].getOrder().equals(order1) ? holds[0].getItems() : holds[1].getItems();
		List<Long> holds2 = holds[0].getOrder().equals(order2) ? holds[0].getItems() : holds[1].getItems();
		
		assertEquals(2, holds1.size());
		assertEquals(1, holds2.size());
		assertTrue(holds1.contains(order1item1));
		assertTrue(holds1.contains(order1item2));
		assertTrue(holds2.contains(order2item1));
	}
}
