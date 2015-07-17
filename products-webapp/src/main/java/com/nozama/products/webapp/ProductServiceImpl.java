package com.nozama.products.webapp;

import java.util.Collection;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import com.nozama.products.*;
import com.nozama.products.impl.ProductRepoImpl;

@WebService(
	endpointInterface = "com.nozama.products.ProductService",
	serviceName = Products.SERVICE,
	targetNamespace = Products.NAMESPACE,
	portName = "ProductsPort")
public class ProductServiceImpl implements ProductService {
	private ProductRepo repo;

	// load this from resources
	// leave the default 100
	private Integer maxProducts = 100;
	
	public ProductServiceImpl() {
		this(new ProductRepoImpl());
	}
	
	public ProductServiceImpl(ProductRepoImpl repo) {
		this.repo = repo;
	}
	
	@Override
	public Collection<Product> getSomeProducts() {
		return repo.getSomeProducts(maxProducts);
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
		if (ep != null) ep.stop();
	}
}
