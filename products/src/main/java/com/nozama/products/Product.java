package com.nozama.products;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "product")
public class Product {
	@XmlElement(name = "id", required = true)
	private long id;
	
	@XmlElement(name = "name", required = true)
	private String name;
	
	public Product() {
		// do nothing
	}
	
	public Product(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
