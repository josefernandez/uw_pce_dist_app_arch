package com.nozama.search;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.nozama.products.Product;

public interface Searcher {
	void index(Collection<Product> products) throws IOException;
	List<Product> doQuery(String query) throws IOException ;
}
