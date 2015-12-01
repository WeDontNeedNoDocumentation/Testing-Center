<%@page import="Java.*" %>
<%@page import="org.joda.time.LocalDate" %>
<%@page import="java.util.*" %>

<table border="1">
                                    <thead>
                                    <!-- Columns -->
                                        <tr class="active">
                                            <th>Date</th>
                                            <th>Number of Appointments</th>
                                            <th>Courses</th>
                                        </tr>
                                    <!-- /Columns -->
                                    </thead>
                                    <tbody>

<%
String email = session.getAttribute("email").toString();
String name = session.getAttribute("name").toString();
int termId = Integer.parseInt(request.getParameter("termId"));

Administrator admin = new Administrator(name, email);
DateTimeUtil dtUtil = new DateTimeUtil();

Map<LocalDate, Integer> appts = admin.appointmentsPerWeek(termId);
Map<LocalDate, Set<String>> courses = admin.coursesPerWeek(termId);

for (LocalDate date : appts.keySet()) {
	int numAppts = appts.get(date);
	Set<String> courseSet = courses.get(date);
	%><tr>
	<td><% out.print(date.toString()); %></td>
	<td><% out.print(numAppts); %></td>
	<td><% for (String courseId : courseSet)
			out.print(courseId + "\n");%></td>
	</tr><%
}%>
</tbody>
</table>