package com.nozama.orders.webapp;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.nozama.inventory.InventoryService;
import com.nozama.orders.OrderService;

@ApplicationScoped
@Path("/orders")
public class OrdersResource {

	//FIXME:
	private OrderService serv;

	//FIXME:
	private InventoryService inv;

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
		return Response.ok(serv.getAll()).build();
	}

	/* DELETE  /orders/{orderId}
	 * 
	 * Allows someone in the 'order-manager' role to delete an order for a customer.
	 * 
	 * Returns 204 NO CONTENT if the user is logged in and in the "order-manager" role.
	 * Returns 401 UNAUTHORIZED if the useris not logged in.
	 * Returns 404 NOT FOUND if the user is in the "order-manager" role but the order does not exist.
	 * Returns 666 if the user is logged in but not an order-manager.
	 */
	@Path("/{orderId}")
	@DELETE
	public Response deleteCustomerOrder(
			@PathParam("orderId") String orderId,
			@Context SecurityContext security) {

		if (!security.isUserInRole("order-manager")) {
			return Response.status(666).build();
		} else if (serv.delete(orderId)) {
			return Response.noContent().build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	/* DELETE  /orders/nuke
	 * 
	 * Nukes all holds and resets all inventory stock.
	 * 
	 * Returns 204 NO CONTENT
	 */
	@Path("/nuke")
	@DELETE
	public Response nuke() {
		inv.nuke();
		return Response.noContent().build();
	}

	/* GET  /orders/holds
	 * 
	 * Returns all holds on all orders
	 * 
	 * Returns 204 NO CONTENT
	 */
	@Path("/holds")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHolds() {
		return Response.ok(inv.getHolds()).build();
	}
	
}
