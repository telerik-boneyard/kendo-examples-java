package api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repositories.ProductsRepository;

import com.google.gson.Gson;

/**
 * Servlet implementation class Products
 */
@WebServlet("/api/products")
public class Products extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// employee repository class
	private repositories.ProductsRepository _repository;
	private Gson _gson;
		
    public Products() {
        super();
        
        // initialize the Gson library
        _gson = new Gson();
    }
	    
    public void init() throws ServletException {
    	super.init();
    
    	// create a new instance of the repository class. pass in the path to the data/sample.db
    	// file which we can get by getting the servlet context, then calling 'getRealPath'
    	_repository = new ProductsRepository(this.getServletContext().getRealPath("data/sample.db"));
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// set the content type we are sending back as JSON
		response.setContentType("application/json");
		
		// get the take and skip parameters
		int skip = request.getParameter("skip") == null ? 0 : Integer.parseInt(request.getParameter("skip"));
		int take = request.getParameter("take") == null ? 20 : Integer.parseInt(request.getParameter("take"));
		
		try {
			
			// create a new DataSourceResult to send back
			models.DataSourceResult result = new models.DataSourceResult();
			
			// set the total property
			result.setTotal(_repository.getCount());
			
			// set the data
			result.setData(_repository.listProducts(skip, take)); 
						
			// convert the DataSourceReslt to JSON and write it to the response
			response.getWriter().print(_gson.toJson(result));
		}
		catch (Exception e) {
			response.sendError(500);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
