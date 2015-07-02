package com.nozama.assignment2;

import java.util.HashMap;
import java.util.Map;

import com.nozama.orders.IdGenerator;
import com.nozama.orders.Order;
import com.nozama.orders.OrderDoesNotExistException;
import com.nozama.orders.OrderService;

public class OrderServiceImpl implements OrderService {
	
	public static final OrderService INSTANCE = new OrderServiceImpl(new IdGeneratorImpl());
	
	private IdGenerator idGen;
	private Map<String, Order> orders = new HashMap<String, Order>();
	
	public OrderServiceImpl(IdGenerator idGen) {
		this.idGen = idGen;
	}

	@Override
	public Order create() {
		String id = idGen.next();
		Order order = new Order(id);
		orders.put(id, order);
		return order;
	}

	@Override
	public void submit(Order order) {
		if (orders.get(order.getId()) == null) {
			throw new OrderDoesNotExistException(order.getId());
		} else {
			orders.remove(order.getId());
		}
	}

	@Override
	public Order get(String id) {
		return orders.get(id);
	}

	@Override
	public boolean delete(String id) {
		if (orders.remove(id) == null) {
			return false;
		} else {
			return true;
		}
	}

}
