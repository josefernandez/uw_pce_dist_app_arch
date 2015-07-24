package com.nozama.orders.webapp;

import javax.enterprise.context.ApplicationScoped;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.nozama.messaging.JMSInfo;
import com.nozama.messaging.holds.*;
import com.nozama.orders.Order;
import com.nozama.orders.OrderService;
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
		Order o = serv.get(orderId);

		if (o == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			if (serv.delete(orderId) == false) {
				return Response.status(Status.NOT_FOUND).build();
			} else {
				for (long itemId : o.getItems()) {
					cancelHold(orderId, itemId);
				}

				return Response.accepted().build();
			}
		}
	}

	/* PUT  /order/{orderId}/item/{itemId}
	 * 
	 * Adds a new line item to an existing order.
	 * 
	 * Returns 204 NO CONTENT and no body if the action was successful.
	 * Returns 404 NOT FOUND if the order does not exist.
	 * Returns 409 CONFLICT if the item is not available.
	 */
	@Path("/{orderId}/item/{itemId}")
	@PUT
	public Response addItemToOrder(@PathParam("orderId") String orderId, @PathParam("itemId") long itemId) {
		Order o = serv.get(orderId);

		if (o == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			placeHold(orderId, itemId);

			switch (getHoldResponse().getStatus()) {
			case CONFIRM:
				o.addItem(itemId);
				return Response.noContent().build();
			case DENY:
				return Response.status(Status.CONFLICT).build();
			default:
				throw new IllegalStateException("Message status unknown");
			}
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
				cancelHold(orderId, itemId);
				return Response.noContent().build();
			} else {
				return Response.status(Status.CONFLICT).build();
			}			
		}
	}

	/* PUT  /order/{orderId}/submit
	 * 
	 * Submits an order. For now, this means it deletes it from the repository.
	 * 
	 * Returns 204 NO CONTENT and no body if the action was successful.
	 * Returns 404 NOT FOUND if the order does not exist.
	 * Returns 409 CONFLICT if the order exists but has no line items.
	 */
	@Path("/{orderId}/submit")
	@PUT
	public Response submitOrder(@PathParam("orderId") String orderId) {
		Order o = serv.get(orderId);

		if (o == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else if (o.getItems().size() == 0) {
			return Response.status(Status.CONFLICT).build();
		} else {
			serv.submit(o);
			commitHold(o.getId());
			return Response.noContent().build();
		}
	}
	
	private void placeHold(String orderId, long itemId) {
		//TODO: place a hold on the hold request queue
	}

	private void cancelHold(String orderId, long itemId) {
		//TODO: cancel a hold using the hold request queue
	}

	private void commitHold(String orderId) {
		//TODO: commit a hold using the hold request queue
		
		//TODO: ALSO create a new checkout event in the checkout topic
	}

	private HoldResponse getHoldResponse() {
		HoldResponse hr = null;

		while (hr == null) {
			hr = //TODO: get the hold response from the hold response queue in a non-blocking fashion

			if (hr == null) {
				try {
					// we don't want to block waiting for a message
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		return hr;
	}

}
