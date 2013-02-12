package repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Employee;

public class EmployeeRepository extends Repository {
	
	public EmployeeRepository(String path) {
		super(path);
	}
	
	public List<Employee> listEmployees (int managerId){
		
		// create a new empty list of employees to return as a result
		List<Employee> employees = new ArrayList<Employee>();
		
		try {
		
			
			// use prepared statements to prevent sql injection attacks
			PreparedStatement stmt = null;
			
			// the query to send to the database
			String query = "SELECT e.EmployeeID, e.FirstName, e.LastName, e.ReportsTo AS ManagerID, "+
					   	   "(SELECT COUNT(*) FROM Employees WHERE ReportsTo = e.EmployeeID) AS DirectReports " +
					   	   "From Employees e ";			
			
			// add a where clause to the query
			// if the employee doesn't have a manager
			if (managerId == 0) {
				// select where employees reportsto is null
				query += "WHERE e.ReportsTo IS NULL";
				stmt = super.conn.prepareStatement(query);
			// otherwise
			} else {
				// select where the reportsto is equal to the employeeId parameter
				query +=  "WHERE e.ReportsTo = ?";
				stmt = super.conn.prepareStatement(query);
				stmt.setInt(1, managerId);
			}
			
			System.out.println(query);
			
			// execute the query into a result set
			ResultSet rs = stmt.executeQuery();
			
			// iterate through the result set
			while(rs.next()) {
				
				// create a new employee model object
				Employee employee = new Employee();
				
				// select fields out of the database and set them on the class
				employee.setEmployeeID(rs.getInt("EmployeeID"));
				employee.setFirstName(rs.getString("FirstName"));
				employee.setLastName(rs.getString("LastName"));
				employee.setManagerID(rs.getInt("ManagerID"));
				employee.setHasChildren(rs.getInt("DirectReports") > 0);
				employee.setFullName();
				
				// add the class to the list
				employees.add(employee);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		// return the result list
		return employees;
		
	}
	
}
