package com.nozama.orders.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.nozama.core.IdGenerator;
import com.nozama.core.impl.IdGeneratorImpl;
import com.nozama.orders.*;

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

	// Please ignore this method for now!
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
	public Collection<Order> getAll() {
		return orders.values();
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
