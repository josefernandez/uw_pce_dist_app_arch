package com.nozama.orders;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")
public class Order {
	@XmlElement(name="id") private String id;
	@XmlElement(name="items") private List<Long> items = new ArrayList<Long>();
	
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
