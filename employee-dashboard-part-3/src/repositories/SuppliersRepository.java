package repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppliersRepository extends Repository {

  public SuppliersRepository(String path) {
    super(path);
  }
  
  public List<models.Supplier> listSuppliers() throws SQLException {
    
    PreparedStatement stmt = null;
    ResultSet rs = null;
 
    // prepare a list of suppliers to populate as a return value
    List<models.Supplier> suppliers = new ArrayList<models.Supplier>();
    
    try {
      
      // set sql statement
      String sql = "SELECT SupplierID, CompanyName AS SupplierName FROM Suppliers";
      
      // prepare the string for safe execution
      stmt = super.conn.prepareStatement(sql);
      
      // execute the statement into a ResultSet
      rs = stmt.executeQuery();
      
      // loop through the results
      while(rs.next()) {
        
        // create a new supplier object
        models.Supplier supplier = new models.Supplier();
        
        // populate it with the values from the database
        supplier.setSupplierID(rs.getInt("SupplierID"));
        supplier.setSupplierName(rs.getString("SupplierName"));
        
        // add the supplier to the return list
        suppliers.add(supplier);
      }
    }
    finally {
      // close out all connection related instances
      stmt.close();
      rs.close();
    }
    
    // return the list of suppliers
    return suppliers;
  }
}
