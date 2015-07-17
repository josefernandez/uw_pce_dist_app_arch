package com.nozama.products;

import java.util.Collection;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.bind.annotation.XmlElement;

@WebService(targetNamespace = Products.NAMESPACE)
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public interface ProductService extends Products {

	@WebMethod
	Collection<Product> getProducts();

	@WebMethod
	Collection<Product> getSomeProducts();

	@WebMethod
	Product getProduct(@XmlElement(required=true) @WebParam(name = "id") long productId);
	
	@WebMethod
	@Oneway
	void addProduct(@XmlElement(required=true) @WebParam(name = "product") Product product);

}
