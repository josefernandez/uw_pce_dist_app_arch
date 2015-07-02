package com.nozama.orders;

public interface OrderService {
	Order get(String id);
	Order create();
	void submit(Order order);
	boolean delete(String id);
}
