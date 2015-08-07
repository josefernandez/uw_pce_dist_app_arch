package com.nozama.inventory;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "holds")
public class Hold implements Serializable {
	private static final long serialVersionUID = 3253396228550682484L;

	@XmlElement(name="order")
	private String order;

	@XmlElement(name="items")
	private ArrayList<Long> items;
	
	public Hold() {
		// do nothing
	}
	
	public Hold(String order, ArrayList<Long> items) {
		this.order = order;
		this.items = items;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrder() {
		return order;
	}

	public void setItems(ArrayList<Long> items) {
		this.items = items;
	}

	public ArrayList<Long> getItems() {
		return items;
	}

}
