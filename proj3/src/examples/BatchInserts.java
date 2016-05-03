package examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchInserts {
	String user;
	String pwd;
	
	
	private void func() throws SQLException{
		List<Employee> employees = new ArrayList<Employee>();
		String sql = "insert into employee (name, city, phone) values (?, ?, ?)";
		Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb",user, pwd);
		PreparedStatement ps = connection.prepareStatement(sql);
		 
		final int batchSize = 1000;
		int count = 0;
		 
		for (Employee employee: employees) {
		 
		    ps.setString(1, employee.getName());
		    //ps.setString(2, employee.getCity());
		    //ps.setString(3, employee.getPhone());
		    ps.addBatch();
		     
		    if(++count % batchSize == 0) {
		        ps.executeBatch();
		    }
		}
		ps.executeBatch(); // insert remaining records
		ps.close();
		connection.close();
	}
}
