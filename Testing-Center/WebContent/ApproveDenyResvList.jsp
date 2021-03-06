<%@page import="DBWorks.DBConnection"%>
<%@page import="Java.*" %>
<%@page import="org.joda.time.DateTime" %>
<%@page import="java.util.*" %>
<%@page import="java.lang.*" %>

<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Approve or Deny Reservation - Admin</title>

        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="css/sb-admin.css" rel="stylesheet">

        <!-- Custom Fonts -->
        <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

    </head>

    <body>
        <div id="wrapper">

            <!-- Navigation -->
            <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="scheduler.html">Testing Scheduler Center</a>
                </div>
                <!-- Top Menu Items -->
                <ul class="nav navbar-right top-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> <b class="caret"></b></a>
                        <ul class="dropdown-menu message-dropdown">
                            <li class="message-preview">
	                            <a href="#">
	                                <div class="media">
	                                    <span class="pull-left">
	                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
	                                    </span>
	                                    <div class="media-body">
	                                        <h5 class="media-heading"><strong>${sessionScope.name}</strong>
	                                        </h5>
	                                        <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
	                                        <p>Lorem ipsum dolor sit amet, consectetur...</p>
	                                    </div>
	                                </div>
	                            </a>
	                        </li>
	                        <li class="message-preview">
	                            <a href="#">
	                                <div class="media">
	                                    <span class="pull-left">
	                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
	                                    </span>
	                                    <div class="media-body">
	                                        <h5 class="media-heading"><strong>${sessionScope.name}</strong>
	                                        </h5>
	                                        <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
	                                        <p>Lorem ipsum dolor sit amet, consectetur...</p>
	                                    </div>
	                                </div>
	                            </a>
	                        </li>
	                        <li class="message-preview">
	                            <a href="#">
	                                <div class="media">
	                                    <span class="pull-left">
	                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
	                                    </span>
	                                    <div class="media-body">
	                                        <h5 class="media-heading"><strong>${sessionScope.name}</strong>
	                                        </h5>
	                                        <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
	                                        <p>Lorem ipsum dolor sit amet, consectetur...</p>
	                                    </div>
	                                </div>
	                            </a>
	                        </li>
                            <li class="message-footer">
                                <a href="#">Read All New Messages</a>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> <b class="caret"></b></a>
                        <ul class="dropdown-menu alert-dropdown">
                            <li>
                                <a href="#">Alert Name <span class="label label-default">Alert Badge</span></a>
                            </li>
                            <li>
                                <a href="#">Alert Name <span class="label label-primary">Alert Badge</span></a>
                            </li>
                            <li>
                                <a href="#">Alert Name <span class="label label-success">Alert Badge</span></a>
                            </li>
                            <li>
                                <a href="#">Alert Name <span class="label label-info">Alert Badge</span></a>
                            </li>
                            <li>
                                <a href="#">Alert Name <span class="label label-warning">Alert Badge</span></a>
                            </li>
                            <li>
                                <a href="#">Alert Name <span class="label label-danger">Alert Badge</span></a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">View All</a>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> ${sessionScope.name} <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="index.html"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
                <div class="collapse navbar-collapse navbar-ex1-collapse">
                    <ul class="nav navbar-nav side-nav">
	                   	<li>
		                    <a href="EditTestingCenter.jsp"><span class="glyphicon glyphicon-calendar"></span></span></i> Edit Testing Center</a>
		                </li>
		                <li>
		                    <a href="MakeAppointment.jsp"><i class="fa fa-fw fa-table"></i> Make Appointment</a>
		                </li>
		                <li class="active">
		                    <a href="ApproveDenyResv.jsp"><span class="glyphicon glyphicon-ok"></span></span></i> Approve/Reject RSV</a>
		                </li>
		                <li>
		                    <a href="StudentCheckinPage.jsp"><span class="glyphicon glyphicon-saved"></span></i> Check-in Student</a>
		                </li>
		                <li>
		                    <a href="ViewAppointments.jsp"><span class="glyphicon glyphicon-list"></span></i> View Appointments</a>
		                </li>
		                <li>
		                    <a href="CancelEditAppointment.jsp"><i class="fa fa-fw fa-wrench"></i> Cancel/Edit Appointments</a>
		                </li>
		                <li>
		                    <a href="javascript:;" data-toggle="collapse" data-target="#demo"><i class="fa fa-fw fa-arrows-v"></i> Import <i class="fa fa-fw fa-caret-down"></i></a>
		                    <ul id="demo" class="collapse">
		                        <li>
		                            <a href="ImportData.jsp">Import Users</a>
		                        </li>
		                        <li>
		                            <a href="ImportData.jsp">Import Class</a>
		                        </li>
		                        <li>
		                            <a href="ImportData.jsp">Import Roster</a>
		                        </li>
		                    </ul>
		                </li>
		                <li>
		                    <a href="index-rtl.html"><i class="fa fa-fw fa-dashboard"></i> Display Utilization Center</a>
		                </li>
		                <li>
		                    <a href="blank-page.html"><i class="fa fa-fw fa-file"></i> Generate Report</a>
		                </li>
                	</ul>
                </div>
                <!-- /.navbar-collapse -->
            </nav>

            <div id="page-wrapper">
				
                <div class="container-fluid">
                	<%
		                String email = session.getAttribute("email").toString();
						String name = session.getAttribute("name").toString();
						String termId = request.getParameter("termId");
					
						Administrator admin = new Administrator(name, email);
						DateTimeUtil dtUtil = new DateTimeUtil();
						
						List<Exam> pendingExams = new ArrayList<Exam>();
						pendingExams = admin.viewPendingExams(Integer.parseInt(termId));
               		%>
                
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Approve/Reject Reservations
                        </h1>
                    </div>
                </div>
				<h2>Reservations</h2>
                <div class="row">
	                <table class="table table-bordered table-hover">
	                    <thead>
	                    <!-- Columns course id section# term, duration, start date and time and end date and time -->
	                        <tr class="active">
	                            <th>Course ID</th>
	                            <th>Term ID</th>
	                            <th>Start Date</th>
	                            <th>Time</th>
	                            <th>End Date</th>
	                            <th>Time</th>
	                            <th>Duration</th>
	                            <th>Action</th>
	                        </tr>
	                    <!-- /Columns -->
	                    </thead>
	                    <tbody>
	                        <tr>
	                        <% 
                            	for(Exam e: pendingExams)
                            	{
            				  		int duration = e.getStart().getMinuteOfDay()-e.getEnd().getMinuteOfDay();
                            	
                            %>
                            <!-- row entries -->
                                <td><%out.print(e.getCourseId()); %></td>
                                <td><%out.print(termId); %></td>
                                <td><%out.print(e.getStart().getMonthOfYear()+"/"+e.getStart().getDayOfMonth()+"/"+e.getStart().getYear()); %></td>
                                <td><%out.print(e.getStart().getHourOfDay()+":"+e.getStart().getMinuteOfDay()); %></td>
                                <td><%out.print(e.getEnd().getMonthOfYear()+"/"+e.getEnd().getDayOfMonth()+"/"+e.getEnd().getYear()); %></td>
                                <td><%out.print(e.getEnd().getHourOfDay()+":"+e.getEnd().getMinuteOfDay()); %></td>
                                <td><%out.print(duration); %></td>
                                <td>
                                
                                	<form action="ShowApptAttendanceDetails.jsp" method="post">
		                            	<button type="submit" class="btn btn-sm btn-success" name="examId" value="<%out.print(e.getExamID());%>:A" formaction="AppDenConfirmation.jsp">Approve</button>
		                            	<button type="submit" class="btn btn-sm btn-danger" name="examId" value="<%out.print(e.getExamID());%>:D" formaction="AppDenConfirmation.jsp">Deny</button>
	                            	</form>
                                </td>
                            <!-- /row entries -->    
                            </tr>
                            	<%}%>
	                    </tbody>
	                </table>
                </div>
                </div>
            </div>
            <!-- /#page-wrapper -->

        </div>
        <!-- /#wrapper -->

        <!-- jQuery -->
        <script src="js/jquery.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>

    </body>

</html>