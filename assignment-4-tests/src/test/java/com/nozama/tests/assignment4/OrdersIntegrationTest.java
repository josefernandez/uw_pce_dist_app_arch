package com.nozama.tests.assignment4;

import static com.jayway.restassured.RestAssured.*;
import static org.junit.Assert.*;

import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import com.jayway.restassured.response.Response;

public class OrdersIntegrationTest {
	
	@Test
	public void testUnauthenticatedUsersCanStillAccesOriginalOrderResource() {
		expect().
			statusCode(404).
		when().
			get("orders/rest/order/fake-id");
	}
	
	@Test
	public void testUnauthenticatedUsersCannotGetOrders() {
		expect().
			statusCode(Status.UNAUTHORIZED.getStatusCode()).
		when().
			get("orders/rest/orders");
	}
	
	@Test
	public void testOrdersCannotBeRetrievedIfNotAuthenticated() {
		given().
			auth().basic("badUser", "password").
		expect().
			statusCode(Status.FORBIDDEN.getStatusCode()).
		when().
			get("orders/rest/orders");
	}
	
	@Test
	public void testOrdersCanBeRetrievedIfAuthenticatedAndAuthorized() {
		given().
			auth().basic("goodUser", "password").
		expect().
			statusCode(Status.OK.getStatusCode()).
		when().
			get("orders/rest/orders");
	}

	@Test
	public void testUnauthenticatedUsersCannotDeleteOrders() {
		expect().
			statusCode(Status.UNAUTHORIZED.getStatusCode()).
		when().
			delete("orders/rest/orders/badOrderId");
	}
	
	@Test
	public void testOrdersCannotBeDeletedIfNotAuthenticated() {
		given().
			auth().basic("badUser", "password").
		expect().
			statusCode(666).
		when().
			delete("orders/rest/orders/badOrderId");
	}
	
	@Test
	public void testAuthorizedUsersReceive404WhenOrderIsDeletedIfOrderDoesNotExist() {
		given().
			auth().basic("goodUser", "password").
		expect().
			statusCode(Status.NOT_FOUND.getStatusCode()).
		when().
			delete("orders/rest/orders/badOrderId");
	}
	
	@Test
	public void testAuthorizedUsersReceive204WhenOrderIsDeletedIfOrderExists() {
		Response r = post("orders/rest/order");
		
		assertEquals(Status.CREATED.getStatusCode(), r.getStatusCode());
		
		String id = r.getBody().asString();
		
		given().
			auth().basic("goodUser", "password").
		expect().
			statusCode(Status.NO_CONTENT.getStatusCode()).
		when().
			delete("orders/rest/orders/" + id);
	}

}
