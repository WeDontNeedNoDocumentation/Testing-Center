<%
	if ((request.getParameter("action") != null) && (request.getParameter("action").trim().equals("logout"))) 
	{
		//session.putValue("login", "");
		session.setAttribute("login", "");
		response.sendRedirect("index.html");
		return;
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
            //query = "SELECT * FROM administrator WHERE administratorId = '" +
            //                idNumber + "' AND name = '" + password  + "'";
            // java.sql.ResultSet rs = DBConnection.ExecQuery(query);
	        //if (rs.next()) 
	        if(idNumber.equals("admin") && password.equals("admin"))
            {
                session.setAttribute("login", idNumber);
                response.sendRedirect("AdminHomepage.jsp");
            }

            else if (idNumber.equals("instructor") && password.equals("inst"))
            {
               	session.setAttribute("login", idNumber);
                response.sendRedirect("InstructorHomepage.jsp");
            }
            else
            {
               	session.setAttribute("login", idNumber);
                response.sendRedirect("StudentHomepage.jsp");
            }
        }
	}
%>