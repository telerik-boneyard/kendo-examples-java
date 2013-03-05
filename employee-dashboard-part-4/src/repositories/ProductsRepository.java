package repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductsRepository extends RepositoryBase {

	public ProductsRepository(String path) {
		super(path);
	}
	
	public int getProductCount() throws SQLException {
		
		int total = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			String sql = "SELECT COUNT(*) AS Total FROM Products";
			
			stmt = super.connection.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				total = rs.getInt("Total");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			stmt.close();
			rs.close();
		}
		
		return total;
		
	}
	
	public List<models.Product> listProducts(int take, int skip) throws SQLException {
		
		List<models.Product> products = new ArrayList<models.Product>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			String sql = "SELECT p.ProductID, p.ProductName, p.SupplierID, s.CompanyName, " +
						 "p.CategoryID, c.CategoryName, p.UnitPrice, p.UnitsInStock, p.Discontinued " +
						 "FROM Products p " +
						 "JOIN Suppliers s ON p.SupplierID = s.SupplierID " +
						 "JOIN Categories c ON p.CategoryID = c.CategoryID " + 
						 "LIMIT ?,?";
			
			stmt = super.connection.prepareStatement(sql);
		
			stmt.setInt(1, skip);
			stmt.setInt(2, take);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				models.Product product = new models.Product();
				
				product.setProductID(rs.getInt("ProductID"));
				product.setProductName(rs.getString("ProductName"));
				product.setSupplier(new models.Supplier(rs.getInt("SupplierID"), rs.getString("CompanyName")));
				product.setCategory(new models.Category(rs.getInt("CategoryID"),rs.getString("CategoryName")));
				product.setUnitPrice(rs.getFloat("UnitPrice"));
				product.setUnitsInStock(rs.getInt("UnitsInStock"));
				product.setDiscontinued(rs.getBoolean("Discontinued"));
				
				products.add(product);				
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			rs.close();
			stmt.close();
		}
		
		return products;
	}

	// update products method
	public models.Product doUpdateProduct(models.Product product) throws Exception {

		// create a prepared statement object
		PreparedStatement stmt = null;

		try {

			// build the sql needed for the update
			String sql = "UPDATE Products SET SupplierID = ?, CategoryID = ?, ProductName = ?, " +
						 "UnitPrice = ?, UnitsInStock = ?, Discontinued = ? " +
						 "WHERE ProductID = ?";

			// prepare the statement for safe execution
			stmt = super.connection.prepareStatement(sql);

			// map the parameters into the query
			stmt.setInt(1, product.getSupplier().getSupplierID());
			stmt.setInt(2, product.getCategory().getCategoryID());
			stmt.setString(3, product.getProductName());
			stmt.setFloat(4, product.getUnitPrice());
			stmt.setInt(5, product.getUnitsInStock());
			stmt.setBoolean(6, product.getDiscontinued());
			stmt.setInt(7, product.getProductID());
			
			// execute the update
			stmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally {
			// close all necessary connection related instances
			stmt.close();
		}
		
		return product;
	}
	
	// product create method
	public int doCreateProduct(models.Product product) throws Exception {
		
		// set a default id value
		int id = 0;
		
		//create prepared statement and result set objects
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			// build the sql string
			String sql = "INSERT INTO Products (SupplierID, CategoryID, " +
						 "ProductName, UnitPrice, UnitsInStock, Discontinued ) " +
						 "VALUES (?, ?, ?, ?, ?, ?)";

			// prepare the statement for safe execution, specifying that we
			// want the auto-generated id from the database returned.
			stmt = super.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			// map the product object to the sql
			stmt.setInt(1, product.getSupplier().getSupplierID());
			stmt.setInt(2, product.getCategory().getCategoryID());
			stmt.setString(3, product.getProductName());
			stmt.setFloat(4, product.getUnitPrice());
			stmt.setInt(5, product.getUnitsInStock());
			stmt.setBoolean(6, product.getDiscontinued());

			// execute the create statement
			int rows = stmt.executeUpdate();
			// if no rows were returned, it failed
			if (rows == 0) {
				throw new SQLException("Unable to create product");
			}

			// get the generated key for this item
			rs = stmt.getGeneratedKeys();
			
			// pull the key off the result set
			if (rs.next()) {
				id = rs.getInt(1);
			}
			else {
				throw new SQLException("Unable to create product. No auto-genereated key obtained");
			}
		}
		finally {
			// close all connection related instances
			stmt.close();
			rs.close();
		}
		
		// return the id that was or wasn't created
		return id;
	}
	
	// the delete method
	public void doDeleteProduct(int productId) throws SQLException {
		
		// create a prepared statement object
		PreparedStatement stmt = null;
		
		try {
			
			// build the simple sql statement
			String sql = "DELETE FROM Products WHERE ProductID = ?";
			
			// prepare the statement for safe execution
			stmt = super.connection.prepareStatement(sql);
			
			// set the id of the product to delete
			stmt.setInt(1, productId);
			
			// execute the delete
			stmt.executeUpdate();
		}
		finally {
			// close all connection related instances
			stmt.close();
		}
	}
	
}
