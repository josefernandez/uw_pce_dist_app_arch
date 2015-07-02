package com.nozama.orders;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
	@JsonProperty private String id;
	@JsonProperty private List<Long> items = new ArrayList<Long>();
	
	public Order(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public List<Long> getItems() {
		return items;
	}
	
	public void addItem(long itemId) {
		items.add(itemId);
	}
	
	public boolean removeItem(long id) {
		Long i = null;
		
		for (Long item : items) {
			if (item == id) {
				i = item;
			}
		}
		
		if (i == null) {
			return false;
		} else {
			items.remove(i);
			return true;
		}
	}
	
}
