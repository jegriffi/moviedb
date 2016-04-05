package javatosql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InsertCustomer {
	private static Pattern pattern;
	private static Matcher matcher;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static void main(String[] args) throws Exception{
		String db = "moviedb";
		String username = "root";
		String pwd = "pass";
		insertCustomer(db,username,pwd);

	}
	
	public static void insertCustomer(String db, String username, String password) throws Exception{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection("jdbc:mysql:///"+db,username, password);
        Statement select = connection.createStatement();
        
        List<String> info = customerInfo();
        insertCC(info, connection, select);
        insertCustomer(info, connection, select);
        
        select.close();
        connection.close();
	}
	
	private static List<String> customerInfo(){
		List<String> info = new ArrayList<String>();
		Scanner in = new Scanner(System.in);

    	String first = "";
    	while(first == null || first.length() <= 0){
    		first = Helper.prompt("first name: ", in);
    	}
    	
    	String last = Helper.prompt("last name", in);
		
		String address = "";
		while (address.length() <= 0){
			address = Helper.prompt("address", in);
		}
		
		String email = "";
		pattern = Pattern.compile(EMAIL_PATTERN);
		while(!validate(email)){
			email = Helper.prompt("email", in);
		}
		
		String password = "";
		while(password.length() <= 0){
			password = Helper.prompt("password", in);
		}
		
		String cc = "";
		while (cc.length() < 16){
			cc = Helper.prompt("credit card", in).replaceAll("-", "");
		}
		
		System.out.println("Enter credit card expiration date: ");
		String expiration = Helper.getDate(in);
		
		info.add(first);
		info.add(last);
		info.add(address);
		info.add(email);
		info.add(password);
		info.add(cc);
		info.add(expiration);
		
		System.out.println(info);
		
		return info;
	}
	
	private static void insertCC(List<String> info, Connection connection, Statement select) throws Exception{
		String first = info.get(0);
		String last = info.get(1);
		String cc = info.get(5);
		String exp = info.get(6);
		String query = "INSERT INTO creditcards (id, first, last, expiration) " +
					   "VALUES ('" + cc + "', '" + first + "', '" + last + "', '" + exp +"')";
		System.err.println(query);
		
		int count = select.executeUpdate(query);
		System.out.println("\nInserted " + count + " rows");
	}
	
	private static void insertCustomer(List<String> info, Connection connection, Statement select) throws Exception{
		String first = info.get(0);
		String last = info.get(1);
		String address = info.get(2);
		String email = info.get(3);
		String password = info.get(4);
		String cc = info.get(5);
		
		String query = "INSERT INTO customers (first, last, address, email, password, cc) " +
					   "VALUES ('" + 
				       first + "', '" + 
					   last + "', '" + 
				       address + "', '" + 
					   email + "', '" + 
				       password + "', '" + 
					   cc + "')";
		System.err.println(query);
		int count = select.executeUpdate(query);
		System.out.println("\nInserted " + count + " rows");
	}
	
	private static boolean validate(final String hex) {
		matcher = pattern.matcher(hex);
		return matcher.matches();

	}

}

















