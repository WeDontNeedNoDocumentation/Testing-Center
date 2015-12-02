<%@page import="DBWorks.DBConnection"%>
<%@page import="Java.*"%>
<%@page import="java.util.*"%>
<%@page import="org.joda.time.DateTime" %>

<!DOCTYPE html>
<html lang="en">
 
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>View Appointments - Admin</title>

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
                        <li>
                            <a href="ApproveDenyResv.jsp"><span class="glyphicon glyphicon-ok"></span></span></i> Approve/Reject RSV</a>
                        </li>
                        <li>
                            <a href="StudentCheckinPage.jsp"><span class="glyphicon glyphicon-saved"></span></i> Check-in Student</a>
                        </li>
                        <li class="active">
                            <a href="ViewAppointments.jsp"><span class="glyphicon glyphicon-list"></span></i> View Appointments</a>
                        </li>
                        <li>
                            <a href="CancelEditAppointment.jsp"><i class="fa fa-fw fa-wrench"></i> Cancel/Edit Appointments</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-toggle="collapse" data-target="#demo"><i class="fa fa-fw fa-arrows-v"></i> Import <i class="fa fa-fw fa-caret-down"></i></a>
                            <ul id="demo" class="collapse">
                                <li>
                                    <a href="#">Import Users</a>
                                </li>
                                <li>
                                    <a href="#">Import Class</a>
                                </li>
                                <li>
                                    <a href="#">Import Roster</a>
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
                	<img src="img/viewappointmentsimg.png" alt="missing">
                    <div class="row">
                        <div class="col-lg-6">
                        <!-- View appointments. The system displays all appointments and the number of available seats at the current time or a specified other time. -->
                            <h2>Here are all the appointments currently active.</h2>
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <!-- Columns -->
                                        <tr class="active">
                                            <th>Appointment ID</th>
                                            <th>Net ID</th>
                                            <th>Seat Number</th>
                                        </tr>
                                    <!-- /Columns -->
                                    </thead>
                                    <tbody>
                                        <tr>
                                        <!-- HAVE NOT DONE TIME ENTERED AND AVAILAIBLE SEATS YET -->
                                        <%
	                                    
		                                    String email = session.getAttribute("email").toString();
		                                    String name = session.getAttribute("name").toString();
	
		                                    Administrator admin = new Administrator(name, email);
		                                    DateTimeUtil dtUtil = new DateTimeUtil();
		                                    
		                                    DateTime time = request.getParameter("time") == null ? DateTime.now() : new DateTime(Integer.parseInt(request.getParameter("time")));
		                                    
			                            	List<Appointment> appts = admin.viewAppointments(time);
			                            	
			                            	for (Appointment appt : appts) {
		                              	%>
                                        <!-- row entries -->
                                            <td><%out.print(appt.getAppointmentId());%></td>
                                            <td><%out.print(appt.getNetId());%></td>
                                            <td><%out.print(appt.getSeatNumber());%></td>
                                        <!-- /row entries -->    
                                        </tr>
                                        <%} %>
                                        
                                    </tbody>
                                </table>
                            </div>
                            
                        <span>There are <% out.print(TestingCenter.getTestingCenter().getNumSeats() - appts.size());%> seats open.</span>
                    <!-- /.row -->
                        </div>
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
