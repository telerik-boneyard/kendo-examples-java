package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Repository {

	public Connection conn;
	
	public Repository(String path) {
		
		// initialize the database driver
        try {
        	Class.forName("org.sqlite.JDBC");
        	
			// set the connection instance
			conn = DriverManager.getConnection("jdbc:sqlite:" + path);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
