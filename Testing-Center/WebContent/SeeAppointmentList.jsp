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

        <title>See Appointments - Student</title>

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
		<% 
            String email = session.getAttribute("email").toString();
      		String id = session.getAttribute("id").toString();
      		String name = session.getAttribute("name").toString();
      		
			String fname = session.getAttribute("fname").toString();
			String lname = session.getAttribute("lname").toString();
			int termId = Integer.parseInt(request.getParameter("termId").toString());
			System.out.println(termId);
			
			Student student = new Student(fname, lname, id, email,id);
      		
      		DateTimeUtil dtUtil = new DateTimeUtil();
      		List<Appointment> appts = new ArrayList<Appointment>();
      		
      		appts = student.viewAppointments(termId);
    	%>
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
                        <ul class="nav navbar-nav side-nav">
                    <li>
                        <a href="MakeAppointmentStudent.jsp"><span class="glyphicon glyphicon-calendar"></span></span></i> Make An Appointment</a>
                    </li>
                    <li class="active">
                        <a href="SeeAppointmentListInput.jsp"><span class="glyphicon glyphicon-ok"></span></span></i> See Appointments</a>
                    </li>
                    <li>
                        <a href="SetAppointmentReminder.jsp"><span class="glyphicon glyphicon-saved"></span></i> Set Appointment Reminder</a>
                    </li>
                </ul>
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
                            
                            <div class="table-responsive">
                            	<h3>Appointments</h3>
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <!-- Columns -->
                                        <tr class="active">
                                            <th>Appointment</th>
                                            <th>Time</th>
                                            <th>Seat Number</th>
                                            <th>Action</th>
                                        </tr>
                                    <!-- /Columns -->
                                    </thead>
                                    <tbody>
                                        <tr>
                                        <% 
                                        	for(Appointment a: appts)
                                        	{
                                        	
                                        %>
                                        <!-- row entries -->
                                            <td><%out.print(a.getAppointmentId()); %></td>
                                            <td><%out.print(dtUtil.getTime(a.getStartTime().getHourOfDay(), a.getStartTime().getMinuteOfHour())); %></td>
                                            <td><%out.print(a.getSeatNumber()); %></td>
                                            <td>
                                            	<button type="button" class="btn btn-sm btn-danger">Cancel</button>
                                            </td>
                                        <!-- /row entries -->    
                                        </tr>
                                        	<%}%>
                                    </tbody>
                                </table>
                            </div>
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
