package objects;

public class Customer {
	int id = 0;
	String first = "";
	String last = "";
	String cc = "";
	String address = "";
	String email = "";
	String password = "";

	public Customer(int id, String first, String last, String cc, String address, String email, String password) {
		this.id = id;
		this.first = first;
		this.last = last;
		this.cc = cc;
		this.address = address;
		this.email = email;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}

	public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	public String getCc() {
		return cc;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}	
}
