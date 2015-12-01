<%@page import="Java.*" %>
<%@page import="org.joda.time.LocalDate" %>
<%@page import="java.util.*" %>

<table border="1">
                                    <thead>
                                    <!-- Columns -->
                                        <tr class="active">
                                            <th>Course ID</th>
                                            <th>Instructor</th>
                                            <th>Subject</th>
                                            <th>Catalog Number</th>
                                            <th>Section Number</th>
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

List<Course> courses = admin.coursesUsed(termId);

for (Course course : courses) {
	%><tr>
	<td><% out.print(course.getCourseTerm()); %></td>
	<td><% out.print(course.getInstructor()); %></td>
	<td><% out.print(course.getSubject()); %></td>
	<td><% out.print(course.getCatalogNumber()); %></td>
	<td><% out.print(course.getSection()); %></td>
	</tr><%
}%>
</tbody>
</table>