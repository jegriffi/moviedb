package javatosql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.*;

public class SQL {

	public static void main(String[] args) throws Exception {
		query("moviedb", "root", "pass");

	}
	
	public static void query(String db, String user, String pwd) throws Exception{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection("jdbc:mysql:///"+db,user, pwd);
        Statement select = connection.createStatement();
		
		Scanner in = new Scanner(System.in);
		
		String input = Helper.prompt("query", in);
		String command = input.split("\\s+",2)[0].toLowerCase();
		
		if(command.equals("select")){
			select(input, select);
		} else {
			update(input, select);
		}
		select.close();
		connection.close();
		
	}
	
	private static void select(String query, Statement select) throws Exception{
		if(select.execute(query)){
			ResultSet rs = select.executeQuery(query);
			ResultSetMetaData metadata = rs.getMetaData();
			List<String> columnNames = new ArrayList<String>();
			int i;
			for(i = 1; i <= metadata.getColumnCount(); ++i){
				columnNames.add(metadata.getColumnName(i));
			}
			
			i = 0;
			while(i++ < 25 && rs.next()){
				int size = metadata.getColumnCount();
				for(int k = 1; k <= size; ++k){
					System.out.println(columnNames.get(k-1) + ": " + rs.getString(k));
				}
				System.out.println();
			}
		}
	}
	
	private static void update(String query, Statement select) throws Exception{
		int count = select.executeUpdate(query);
		System.out.println(count + " rows affected");
	}

}








