package com.nozama.products;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.nozama.inventory.InventoryItem;

public class ProductServiceClient implements Products {
	private URL url;
	private QName qname;
	private Service service;
	private Products products;
	
	public ProductServiceClient() throws MalformedURLException {
		url = new URL(Products.ENDPOINT_LOCATION + "?wsdl");
		qname = new QName(Products.NAMESPACE, Products.SERVICE);
		service = Service.create(url, qname);
		products = service.getPort(ProductService.class);
	}
	
	@Override
	public Product getProduct(long productId) {
		return products.getProduct(productId);
	}
	
	@Override
	public List<Product> getProducts() {
		return new ArrayList<Product>(products.getProducts());
	}
	
	@Override
	public void addProduct(Product product) {
		products.addProduct(product);
	}

	@Override
	public List<InventoryItem> getInventory() {
		return products.getInventory();
	}

}
