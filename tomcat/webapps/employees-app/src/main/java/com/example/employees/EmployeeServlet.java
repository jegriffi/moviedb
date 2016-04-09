package com.example.employees;


/*ADD JAVAX.SERVLET TO THE CLASSPATH THROUGH COMMANDLINE:
 * javac -cp .;/path/to/tomcat/lib/servlet-api.jar com/exmaple/Myservletclass.java
*/
import java.util.List;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.Star;
import javatosql.JavaToSQL;

@WebServlet(
    name="EmployeeServlet",
    urlPatterns={"/employee"}
)
public class EmployeeServlet extends HttpServlet {
    EmployeeService employeeService = new EmployeeService();
    
    
    /* used to process the HTTP GET request. 
        ***forwardListEmployees method is a helper method to dispatch the employeeList
        *** to the list-employees.jsp file.
    */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("searchAction");
        if (action != null) {
            switch(action) {
                case "searchById":
                    searchEmployeeById(req, resp);
                    break;
                case "searchByName":
                    searchEmployeeByName(req, resp);
                    break;
            }
        } else {
            //List <Employee> result = employeeService.getAllEmployees();
            //forwardListEmployees(req, resp, result);
	    searchEmployeeByName(req, resp);
        }
    }
    
    private void searchEmployeeById(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        long idEmployee = Integer.valueOf(req.getParameter("idEmployee"));
        Employee employee = null;
        try {
            employee = employeeService.getEmployee(idEmployee);
        } catch (Exception e) {
            Logger.getLogger(EmployeeServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        
        req.setAttribute("employee", employee);
        req.setAttribute("action", "edit");
        
        String nextJSP = "/jsp/new-employee.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
    }
    
    private void searchEmployeeByName(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        //String employeeName = req.getParameter("employeeName");
        //List<Employee> result = employeeService.searchEmployeesByName(employeeName);

	List<Star> result = JavaToSQL.selectStars();

        forwardListEmployees(req, resp, result);
    }
    
    private void forwardListEmployees(HttpServletRequest req, HttpServletResponse resp, List employeeList)
            throws ServletException, IOException {
        String nextJSP = "/jsp/list-employees.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("employeeList", employeeList);
        dispatcher.forward(req, resp);
    }
}       
