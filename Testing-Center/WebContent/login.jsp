<%@page import="DBWorks.DBConnection"%>
<%@page import="Java.*" %>
<%@ page import="java.util.*" %>

<%
	if ((request.getParameter("action") != null) && (request.getParameter("action").trim().equals("logout"))) 
	{
		session.setAttribute("login", "");
		response.sendRedirect("index.html");
	}

	String email = request.getParameter("email");
	String password = request.getParameter("password");
	String name;
	String id;
	
    String query = null;
    String queryTwo = null;
	session.setAttribute("login", "");
	
	TestingCenter tc = TestingCenter.getTestingCenter();
	session.setAttribute("TC", tc);
	
	//MyClass mc = new MyClass("blah");
	
	//session.setAttribute("MySessionVariable", param);
	
	if ((email != null) && (password != null))
    {
        if (email.trim().equals("") || password.trim().equals("")) 
        {
		      response.sendRedirect("index.html");
        } 
        else 
        {
            query = "SELECT * FROM administrator WHERE email = '" +
                            email + "' AND administratorId = '" + password  + "'";
             java.sql.ResultSet rs = DBConnection.ExecQuery(query);
	        if (rs.next())
	        {
	        	
	        	session.setAttribute("login", email);
                response.sendRedirect("AdminHomepage.jsp");
	        }
	        else
	        {
	        	query = "SELECT * FROM instructor WHERE email = '" +
                        email + "' AND instructorId = '" + password  + "'";
         		rs = DBConnection.ExecQuery(query);
	        	if(rs.next())
	        	{
	        		id = rs.getString(5);
	        		email = rs.getString(4);
	        		name = rs.getString(2);
	        		
	        		session.setAttribute("name", name);
	        		session.setAttribute("login", email);
	        		session.setAttribute("id", password);
	        		session.setAttribute("email", email);
	        		
	        		Instructor instr = new Instructor(name, email, id);
	        		
	        		session.setAttribute("Instructor", instr);
	        		
	                response.sendRedirect("InstructorHomepage.jsp");
	        	}
	        	else
	        	{
	        		query = "SELECT * FROM student WHERE email = '" +
	                        email + "' AND studentId = '" + password  + "'";
	         		rs = DBConnection.ExecQuery(query);
	         		if(rs.next())
	         		{
	         			session.setAttribute("login", email);
	                    response.sendRedirect("StudentHomepage.jsp");
	         		}
	         		else
	         		{
	         			out.print("Email and Password combination is incorrect ");
	                    %>
	                    
						
	                    <br/><% %>
	                    <a href="index.html"> Back to login page </a>
	                    <%
	         		}
	        	}
	        }
        }
	}
%>