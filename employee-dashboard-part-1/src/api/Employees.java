package api;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import repositories.EmployeeRepository;


/**
 * Servlet implementation class Employees
 */
@WebServlet(description = "A servlet to return data about employees from the database", urlPatterns = { "/api/employees" })
public class Employees extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// employee repository class
	private EmployeeRepository _repository = null;
	private Gson _gson = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Employees() {
        super();
        
        // initialize the Gson library
        _gson = new Gson();
    }
    
    public void init() throws ServletException {
    	super.init();
    
    	// create a new instance of the repository class. pass in the path to the data/sample.db
    	// file which we can get by getting the servlet context, then calling 'getRealPath'
    	_repository = new EmployeeRepository(this.getServletContext().getRealPath("data/sample.db"));
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			System.out.println(request.getParameter(""));
			
			// get the managerId off of the request
			int managerId = request.getParameter("EmployeeID") == null ? 0 : Integer.parseInt(request.getParameter("EmployeeID"));
			
			System.out.println(managerId);
			
			// get the employees from the database
			List<models.Employee> employees = _repository.listEmployees(managerId);
			
			// set the content type we are sending back as JSON
			response.setContentType("application/json"); 
			
			// convert the list to json and write it to the response
			response.getWriter().print(_gson.toJson(employees));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
