package com.nozama.orders;

import java.util.Collection;

public interface OrderService {
	Order get(String id);
	Collection<Order> getAll();
	Order create();
	void submit(Order order);
	boolean delete(String id);
}
