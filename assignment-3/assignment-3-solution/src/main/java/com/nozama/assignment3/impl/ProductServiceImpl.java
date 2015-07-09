package com.nozama.assignment3.impl;

import java.util.Collection;

import javax.xml.ws.Endpoint;

import com.nozama.products.Product;
import com.nozama.products.ProductRepo;
import com.nozama.products.Products;
import com.nozama.products.impl.ProductRepoImpl;

public class ProductServiceImpl implements ProductService {
	private ProductRepo repo;
	
	public ProductServiceImpl() {
		this(new ProductRepoImpl());
	}
	
	public ProductServiceImpl(ProductRepoImpl repo) {
		this.repo = repo;
	}
	
	@Override
	public Collection<Product> getProducts() {
		return repo.getProducts();
	}

	@Override
	public Product getProduct(long productId) {
		return repo.getProduct(productId);
	}

	@Override
	public void addProduct(Product product) {
		repo.put(product);
	}

	public static void main(String[] args) {
		Endpoint ep = Endpoint.publish(Products.ENDPOINT_LOCATION, new ProductServiceImpl());
		// put a breakpoint on this next line and debug for manual testing
		if (ep != null) ep.stop();
	}
}
