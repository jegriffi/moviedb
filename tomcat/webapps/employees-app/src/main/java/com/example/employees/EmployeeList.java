package com.example.employees;

import java.util.ArrayList;
import java.util.List;

public class EmployeeList {
	private static final List<Employee> employeeList = new ArrayList();

	private EmployeeList() {

	}

	static {
		employeeList.add(new Employee("jon", "smith", "1-1-12", "manager", "sales", "j.s@aol.com"));
		employeeList.add(new Employee("james", "smith", "1-1-12", "manager", "sales", "j.s@aol.com"));
		employeeList.add(new Employee("mirelle", "smith", "1-1-12", "manager", "sales", "j.s@aol.com"));
		employeeList.add(new Employee("bob", "smith", "1-1-12", "manager", "sales", "j.s@aol.com"));
		employeeList.add(new Employee("walt", "smith", "1-1-12", "manager", "sales", "j.s@aol.com"));
		employeeList.add(new Employee("kaitlyne", "smith", "1-1-12", "manager", "sales", "j.s@aol.com"));
		employeeList.add(new Employee("joann", "smith", "1-1-12", "manager", "sales", "j.s@aol.com"));
	}

	public static List<Employee> getInstance() {
		return employeeList;
	}
}
