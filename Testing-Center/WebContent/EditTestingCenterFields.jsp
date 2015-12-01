<%@page import="DBWorks.DBConnection"%>
<%@page import="Java.*" %>
<%@ page import="java.util.*" %>

<%
	String email = session.getAttribute("email").toString();
	String name = session.getAttribute("name").toString();
	
	Administrator admin = new Administrator(name, email);
	
	String numSeats = request.getParameter("numSeats");
	String numSASeats = request.getParameter("numSASeats");
	String gapTime = request.getParameter("gapTime");
	String reminderInt = request.getParameter("reminderInt");
	
	if(!(numSeats.equals("")))
		admin.editNumberSeats(Integer.parseInt(numSeats));
	if(!(numSASeats.equals("")))
		admin.editSetAside(Integer.parseInt(numSASeats));
	if(!(gapTime.equals("")))
		admin.editGapTime(Integer.parseInt(gapTime),0);
	if(!(reminderInt.equals("")))
		admin.editReminder(Integer.parseInt(reminderInt));
	
	response.sendRedirect("EditTestingCenter.jsp");
%>