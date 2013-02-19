package repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductsRepository extends Repository {
	
	public ProductsRepository(String path) {
		super(path);
	}

	public int getCount() throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int total = 0;
		
		try {
			
			// create a prepared statement
			stmt = super.conn.prepareStatement("SELECT COUNT(*) AS Total FROM Products");
			
			// execute the statment into the result set
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				total = rs.getInt("Total");
			}
		}
		finally {
			rs.close();
			stmt.close();
		}
		
		return total;
	}
	
	public List<models.Product> listProducts(int skip, int take) throws SQLException {
		
		List<models.Product> products = new ArrayList<models.Product>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			// the sql string to send to the database
			String sql = "SELECT p.ProductID, p.ProductName, p.SupplierID, s.CompanyName, " +
						 "p.CategoryID, c.CategoryName, p.UnitPrice, p.UnitsInStock, p.Discontinued " +
						 "FROM Products p " +
						 "JOIN Suppliers s ON p.SupplierID = s.SupplierID " +
						 "JOIN Categories c ON p.CategoryID = c.CategoryID " +
						 "LIMIT ?,?";
			
			// prepare the statement with the sql string
			stmt = super.conn.prepareStatement(sql);
			
			// add in the take and skip parameters
			stmt.setInt(1, skip);
			stmt.setInt(2, take);
			
			// execute the query into the result set
			rs = stmt.executeQuery();
			
			// loop through the result set and map each row to a product object
			while(rs.next()) {
				
				models.Product product = new models.Product();
				
				product.setProductID(rs.getInt("ProductID"));
				product.setProductName(rs.getString("ProductName"));
				product.setSupplier(new models.Supplier(rs.getInt("SupplierID"), rs.getString("CompanyName")));
				product.setCategory(new models.Category(rs.getInt("CategoryID"), rs.getString("CategoryName")));
				product.setUnitPrice(rs.getFloat("UnitPrice"));
				product.setUnitsInStock(rs.getInt("UnitsInStock"));
				product.setDiscontinued(rs.getBoolean("Discontinued"));
				
				products.add(product);				
			}
		} 
		finally {
			rs.close();
			stmt.close();
		}
		
		return products;
	}
}