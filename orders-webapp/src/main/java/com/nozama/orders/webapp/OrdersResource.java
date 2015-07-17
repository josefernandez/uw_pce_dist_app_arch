package com.nozama.orders.webapp;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.nozama.orders.OrderService;
import com.nozama.orders.impl.OrderServiceImpl;

@ApplicationScoped
@Path("/orders")
public class OrdersResource {
	private OrderService serv;

	public OrdersResource() {
		this(OrderServiceImpl.INSTANCE);
	}
	
	public OrdersResource(OrderService serv) {
		this.serv = serv;
	}

	/* GET  /orders
	 * 
	 * Returns all existing orders.
	 * 
	 * Returns 200 OK and a list of orders in the body if the user is in the "order-manager" role.
	 * Returns 401 UNAUTHORIZED if the useris not logged in.
	 * Returns 403 FORBIDDEN otherwise.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOrders() {
		
		// use this to get the list of orders:
		serv.getAll();
	}

	/* DELETE  /orders/{orderId}
	 *
	 * NOTE: Does not actually delete an order!!
	 * 
	 * Allows someone in the 'order-manager' role to delete an order for a customer.
	 * 
	 * Returns 204 NO CONTENT if the user is logged in and in the "order-manager" role.
	 * Returns 401 UNAUTHORIZED if the useris not logged in.
	 * Returns 404 NOT FOUND if the user is in the "order-manager" role but the order does not exist.
	 * Returns 666 if the user is logged in but is not an order-manager.
	 */
	@Path("/{orderId}")
	@DELETE
	public Response deleteCustomerOrder(@PathParam("orderId") String orderId) {
		// check security here, but don't try to delete an order!
	}

}
