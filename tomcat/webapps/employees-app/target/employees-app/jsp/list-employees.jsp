<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
       <link rel="stylesheet" href="../css/bootstrap.min.css"/>         
       <script src="../js/bootstrap.min.js"></script>       
    </head>

    <body>          
        <div class="container">
            <h2>Stars</h2>
            <!--Search Form -->
            <form action="/employee" method="get" id="seachEmployeeForm" role="form">
                <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
                <div class="form-group col-xs-5">
                    <input type="text" name="employeeName" id="employeeName" class="form-control" required="true" placeholder="Type the Name or Last Name of the employee"/>                    
                </div>
                <button type="submit" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span> Search
                </button>
                <br></br>
                <br></br>
            </form>
            
		<% 
		 /**
		 HEY JON LOOK HERE 

		 this is the part of the form that dynamically populates the tables according to the data its given 
		 c:when is the if-statement | c:otherwise is the else statement | "${}" is the variable access 
		  "${}" is the variable access from the EmployeeServlet class, inside you can get lists like "${douchbag.get(1)}" or whatever 
		  **/
		  %>
            <form action="/employee" method="post" id="employeeForm" role="form" >              
                <c:choose>
                    <c:when test="${not empty employeeList}">
                        <table  class="table table-striped">
                            <thead>
                                <tr>
                                    <td>#</td>
                                    <td>First Name</td>
                                    <td>Last Name</td>
                                    <td>Birth date</td>
                                </tr>
                            </thead>
                            <c:forEach var="star" items="${employeeList}">
                                <c:set var="classSucess" value="info"/>
                                <tr class="${classSucess}">
                                    <td>${star.id}</td>
                                    <td>${star.first}</td>
                                    <td>${star.last}</td>
                                    <td>${star.dob}</td>
                                </tr>
                            </c:forEach>               
                        </table>  
                    </c:when>                    
                    <c:otherwise>
                        <br>           
                        <div class="alert alert-info">
                            No people found matching your search criteria
                        </div>
                    </c:otherwise>
                </c:choose>                        
            </form>
           
        </div>
    </body>
</html>
