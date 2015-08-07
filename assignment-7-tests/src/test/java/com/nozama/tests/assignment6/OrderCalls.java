package com.nozama.tests.assignment6;

import static com.jayway.restassured.RestAssured.*;
import static org.junit.Assert.*;

import javax.ws.rs.core.Response.Status;

import com.jayway.restassured.response.Response;

public abstract class OrderCalls {

	public String createOrder() {
		Response r = post("orders/rest/order");

		assertEquals(Status.CREATED.getStatusCode(), r.getStatusCode());
		
		System.out.println("Created order : " + r.getBody().asString());
		
		return r.getBody().asString();
	}
	
	public void addItemToOrder(String orderId, long itemId, Status status) {
		System.out.println("Adding item " + itemId + " to order : " + orderId);
		
		expect().
			statusCode(status.getStatusCode()).
		when().
			put("orders/rest/order/" + orderId + "/item/" + itemId);
	}
	
	public void removeItemFromOrder(String orderId, long itemId, Status status) {
		System.out.println("Removing item " + itemId + " from order : " + orderId);

		expect().
			statusCode(status.getStatusCode()).
		when().
			delete("orders/rest/order/" + orderId + "/item/" + itemId);
	}
	
	public void checkout(String orderId) {
		System.out.println("Checkout for order : " + orderId);

		expect().
			statusCode(Status.NO_CONTENT.getStatusCode()).
		when().
			put("orders/rest/order/" + orderId + "/submit");
	}

	public void nuke() {
		given().
			auth().basic("goodUser", "password").
		expect().
			statusCode(Status.NO_CONTENT.getStatusCode()).
		when().
			delete("orders/rest/orders/nuke");
	}

}
