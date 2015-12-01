<%@page import="Java.*" %>
<%@page import="org.joda.time.LocalDate" %>
<%@page import="java.util.*" %>

<table border="1">
                                    <thead>
                                    <!-- Columns -->
                                        <tr class="active">
                                            <th>Term ID</th>
                                            <th>Number of Appointments</th>
                                        </tr>
                                    <!-- /Columns -->
                                    </thead>
                                    <tbody>

<%
String email = session.getAttribute("email").toString();
String name = session.getAttribute("name").toString();
int startTermId = Integer.parseInt(request.getParameter("startTermId"));
int endTermId = Integer.parseInt(request.getParameter("endTermId"));

Administrator admin = new Administrator(name, email);
DateTimeUtil dtUtil = new DateTimeUtil();

Map<Integer, Integer> appts = admin.appointmentsPerTerm(startTermId, endTermId);

for (Integer termId : appts.keySet()) {
	%><tr>
	<td><% out.print(termId); %></td>
	<td><% out.print(appts.get(termId)); %></td>
	</tr><%
}%>
</tbody>
</table>