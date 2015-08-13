package com.nozama.search.webapp;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.nozama.products.impl.ProductRepoImpl;
import com.nozama.search.Searcher;
import com.nozama.search.impl.SearcherBuilder;

@ApplicationScoped
@Path("/search")
public class SearchResource {
	private Searcher s;
	
	public SearchResource() {
		try {

			s = new SearcherBuilder()

			//	Configure searcher through SearcherBuilder here!

					.build();
			
			s.index(new ProductRepoImpl().getProducts());
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
	/**
	 * GET /search?query=<query>
	 * 
	 * Performs a search based on the provided query.
	 * 
	 * Returns:
	 * 	400 BAD REQUEST if the query parameter wasn't set correctly.
	 *  200 OK if the query was successful and the results in the response body.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response doSearch(@QueryParam(value = "query") String query) throws IOException {
		if (query == null) {
			return Response.status(Status.BAD_REQUEST).build();
		} else {
			return Response.ok(s.doQuery(query)).build();
		}
	}

}
