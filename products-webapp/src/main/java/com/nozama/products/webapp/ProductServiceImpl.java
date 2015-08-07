package com.nozama.products.webapp;

import java.util.Collection;
import java.util.List;

import javax.jws.WebService;

import com.nozama.inventory.InventoryItem;
import com.nozama.inventory.InventoryService;
import com.nozama.products.*;

@WebService(
	endpointInterface = "com.nozama.products.ProductService",
	serviceName = Products.SERVICE,
	targetNamespace = Products.NAMESPACE,
	portName = "ProductsPort")
public class ProductServiceImpl implements ProductService {
	
	//FIXME:
	private ProductRepo repo;

	//FIXME:
	private InventoryService inv;

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

	@Override
	public List<InventoryItem> getInventory() {
		return inv.getInventory();
	}

}
