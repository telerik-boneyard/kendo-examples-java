package repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesRepository extends Repository {

	public CategoriesRepository(String path) {
		super(path);
	}
	
	public List<models.Category> listCategories() throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// create a list of categories to return
		List<models.Category> categories = new ArrayList<models.Category>();
		
		try {
			
			// create the sql string
			String sql = "SELECT CategoryID, CategoryName FROM Categories";
			
			// prepare the string for safe execution
			stmt = super.conn.prepareStatement(sql);
			
			// execute the sql and return the results to the ResultSet
			rs = stmt.executeQuery();
			
			// iterate through the result set
			while(rs.next()) {
				
				// create a new category object
				models.Category category = new models.Category();
				
				// populate it's values from the database
				category.setCategoryID(rs.getInt("CategoryID"));
				category.setCategoryName(rs.getString("CategoryName"));
				
				// add it to the list of categories
				categories.add(category);
			}
		}
		finally {
			// close out all connection related instances
			stmt.close();
			rs.close();
		}
		
		// return the list of categories
		return categories;
		
	}
}
