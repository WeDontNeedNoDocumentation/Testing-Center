<%@page import="Java.*" %>
<%@page import="org.joda.time.LocalDate" %>
<%@page import="java.util.*" %>

<table border="1">
                                    <thead>
                                    <!-- Columns -->
                                        <tr class="active">
                                            <th>Date</th>
                                            <th>Appointments</th>
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

Map<LocalDate, Integer> res = admin.appointmentsPerDay(termId);

for (LocalDate date : res.keySet()) {
	%><tr>
	<td><% out.print(date.toString()); %></td>
	<td><% out.print(res.get(date)); %></td>
	</tr><%
}%>
</tbody>
</table>