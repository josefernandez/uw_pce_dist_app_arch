package com.nozama.orders.webapp;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@ApplicationPath("/rest")
public class OrderApplication extends Application {
	
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(OrderResource.class);
        resources.add(OrdersResource.class);
        return resources;
    }
     
    @Override
    public Set<Object> getSingletons() {
        final Set<Object> instances = new HashSet<Object>();

        instances.add(new JacksonJsonProvider());
        return instances;
    }

}
