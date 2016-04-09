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



//INSTEAD OF USING WEB.XML.....
//this annotation basically messes with the url to show where exactly to look in the project structure
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
                    searchEmployeeById(req, resp); //not used...
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
   
    //gathers the list of Results (Stars in our case) and sends it to ForwardListEmployees();
    //** for every list we query, we're going to need to create one of these functions to get the data **
    private void searchEmployeeByName(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        //String employeeName = req.getParameter("employeeName");
        //List<Employee> result = employeeService.searchEmployeesByName(employeeName);

	List<Star> result = JavaToSQL.selectStars();

        forwardListEmployees(req, resp, result);
    }

    //this function sends the attributes to the jsp file (nextJSP) where it will attach the lists needed
    //unlike the searchEmployeeByName, i think there will only be ONE of these functions to send all the data to the .jsp file
    private void forwardListEmployees(HttpServletRequest req, HttpServletResponse resp, List employeeList)
            throws ServletException, IOException {
        String nextJSP = "/jsp/list-employees.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("employeeList", employeeList);
        dispatcher.forward(req, resp);
    }
}       
