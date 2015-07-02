package com.nozama.assignment2.impl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.nozama.assignment2.OrderServiceImpl;
import com.nozama.orders.Order;
import com.nozama.orders.OrderService;

@Path("/order")
public class OrderResource {
	private OrderService serv;
	
	public OrderResource() {
		this(OrderServiceImpl.INSTANCE);
	}
	
	public OrderResource(OrderService serv) {
		this.serv = serv;
	}

	/* POST  /order/
	 * 
	 * Creates a new order. The order ID is automatically generated.
	 * 
	 * Returns 201 CREATED and the order ID in plain text.
	 */
	public createOrder() {
		
	}
	
	/* GET  /order/{orderId}
	 * 
	 * Returns an existing order.
	 * 
	 * Returns 200 OK and order as JSON body if the order exists.
	 * Returns 404 NOT FOUND if the order does not exist.
	 */
	public getOrder() {
		
	}
	
	/* DELETE  /order/{orderId}
	 * 
	 * Deletes an existing order. Orders may take a while to delete.
	 * 
	 * Returns 404 NOT FOUND if the order does not exist.
	 * Returns 202 ACCEPTED if the order exists and it was deleted.
	 */
	public deleteOrder() {
		
	}

	/* PUT  /order/{orderId}/item/{itemId}
	 * 
	 * Adds a new line item to an existing order.
	 * 
	 * Returns 204 NO CONTENT and no body if the action was successful.
	 * Returns 404 NOT FOUND if the order does not exist.
	 */
	public addItemToOrder() {
		
	}

	/* DELETE  /order/{orderId}/item/{itemId}
	 * 
	 * Deletes a new line item from an existing order.
	 * 
	 * Returns 204 NO CONTENT and no body if the action was successful.
	 * Returns 404 NOT FOUND if the order does not exist.
	 * Returns 409 CONFLICT if the line item does not exist.
	 */
	public removeItemFromOrder() {
		
	}

}
