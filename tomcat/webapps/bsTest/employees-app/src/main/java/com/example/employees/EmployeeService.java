package com.example.employees;

import java.util.*;

public class EmployeeService {
	
	List<Employee> employeeList = EmployeeList.getInstance();

	public List<Employee> getAllEmployees() {
		return employeeList;
	}

	public List<Employee> searchEmployeesByName(String name) {
            List<Employee> result = new ArrayList();
            for (Employee e: employeeList) {
                if (e.getName().equals(name) || e.getLastName().equals(name)) {
                    result.add(e);
                }
            }
            return result;
	}

	public Employee getEmployee(long id) throws Exception {
	    boolean isFound = false;
	    Employee match = new Employee();
            for (Employee e : employeeList) {
                if (e.getId() == id) {
                    match = e;
		    isFound = true;
                    break;
                }
            }
            
            if (isFound) {
                    return match;
            } else {
                    throw new Exception("The Employee id " + id + " was not found");
            }
	}
}
