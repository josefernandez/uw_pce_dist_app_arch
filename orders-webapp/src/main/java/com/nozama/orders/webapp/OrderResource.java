package com.nozama.orders.webapp;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.nozama.orders.*;
import com.nozama.orders.impl.OrderServiceImpl;

@ApplicationScoped
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
	@POST
	public Response createOrder() {
		return Response.status(Status.CREATED).entity(serv.create().getId()).build();
	}
	
	/* GET  /order/{orderId}
	 * 
	 * Returns an existing order.
	 * 
	 * Returns 200 OK and order as JSON body if the order exists.
	 * Returns 404 NOT FOUND if the order does not exist.
	 */
	@Path("/{orderId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrder(@PathParam("orderId") String orderId) {
		Order o = serv.get(orderId);
		if (o == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok().entity(o).build();
		}
	}
	
	/* DELETE  /order/{orderId}
	 * 
	 * Deletes an existing order. Orders may take a while to delete.
	 * 
	 * Returns 404 NOT FOUND if the order does not exist.
	 * Returns 202 ACCEPTED if the order exists and it was deleted.
	 */
	@Path("/{orderId}")
	@DELETE
	public Response deleteOrder(@PathParam("orderId") String orderId) {
		if (serv.delete(orderId) == false) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.accepted().build();
		}
	}

	/* PUT  /order/{orderId}/item/{itemId}
	 * 
	 * Adds a new line item to an existing order.
	 * 
	 * Returns 204 NO CONTENT and no body if the action was successful.
	 * Returns 404 NOT FOUND if the order does not exist.
	 */
	@Path("/{orderId}/item/{itemId}")
	@PUT
	public Response addItemToOrder(@PathParam("orderId") String orderId, @PathParam("itemId") long itemId) {
		Order o = serv.get(orderId);
		if (o == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			o.addItem(itemId);
			return Response.noContent().build();
		}
	}

	/* DELETE  /order/{orderId}/item/{itemId}
	 * 
	 * Deletes a new line item from an existing order.
	 * 
	 * Returns 204 NO CONTENT and no body if the action was successful.
	 * Returns 404 NOT FOUND if the order does not exist.
	 * Returns 409 CONFLICT if the line item does not exist.
	 */
	@Path("/{orderId}/item/{itemId}")
	@DELETE
	public Response removeItemFromOrder(@PathParam("orderId") String orderId, @PathParam("itemId") long itemId) {
		Order o = serv.get(orderId);
		if (o == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			if (o.removeItem(itemId)) {
				return Response.noContent().build();
			} else {
				return Response.status(Status.CONFLICT).build();
			}			
		}
	}

}
