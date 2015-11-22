<%@page import="DBWorks.DBConnection"%>
<jsp:useBean id="b" class="Bean.InstructorBean" scope="application" />
<%
	if ((request.getParameter("action") != null) && (request.getParameter("action").trim().equals("logout"))) 
	{
		session.setAttribute("login", "");
		response.sendRedirect("index.html");
	}

	String idNumber = request.getParameter("idNumber");
	String password = request.getParameter("password");
    String query = null;
	session.setAttribute("login", "");
	
	if ((idNumber != null) && (password != null))
    {
        if (idNumber.trim().equals("") || password.trim().equals("")) 
        {
		      response.sendRedirect("index.html");
        } 
        else 
        {
            query = "SELECT * FROM administrator WHERE email = '" +
                            idNumber + "' AND administratorId = '" + password  + "'";
             java.sql.ResultSet rs = DBConnection.ExecQuery(query);
	        if (rs.next())
	        {
	        	
	        	session.setAttribute("login", idNumber);
                response.sendRedirect("AdminHomepage.jsp");
	        }
	        else
	        {
	        	query = "SELECT * FROM instructor WHERE email = '" +
                        idNumber + "' AND instructorId = '" + password  + "'";
         		rs = DBConnection.ExecQuery(query);
	        	if(rs.next())
	        	{
	        		session.setAttribute("login", idNumber);
	                response.sendRedirect("InstructorHomepage.jsp");
	        	}
	        	else
	        	{
	        		query = "SELECT * FROM student WHERE email = '" +
	                        idNumber + "' AND studentId = '" + password  + "'";
	         		rs = DBConnection.ExecQuery(query);
	         		if(rs.next())
	         		{
	         			session.setAttribute("login", idNumber);
	                    response.sendRedirect("StudentHomepage.jsp");
	         		}
	         		else
	         		{
	         			out.print("Email and Password combination is incorrect " + request.getAttribute("stringg"));
	         			
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