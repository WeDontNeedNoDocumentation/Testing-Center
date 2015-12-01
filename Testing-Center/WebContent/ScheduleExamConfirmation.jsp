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

    <title>Schedule Course Exam Confirmation - Instructor</title>

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
	
		Instructor instr = new Instructor(name, email, id);
		
		String examId = request.getParameter("examId");
		
		String sDate = request.getParameter("sDate");
		int month = Integer.parseInt(sDate.substring(0,2));
		int day = Integer.parseInt(sDate.substring(3,5));
		int year = Integer.parseInt(sDate.substring(6,10));
		
		String sTime = request.getParameter("sTime");
		int hour = Integer.parseInt(sTime.substring(0,2));
		int minute = Integer.parseInt(sTime.substring(3,5));
		String amPm = sTime.substring(5,7);
		
		if(amPm.equals("PM")||amPm.equals("pm")||amPm.equals("Pm"))
			hour+= 12;
		
		DateTime stDate = new DateTime(year, month, day, hour, minute, 0, 0);
		
		String eDate = request.getParameter("eDate");
		month = Integer.parseInt(eDate.substring(0,2));
		day = Integer.parseInt(eDate.substring(3,5));
		year = Integer.parseInt(eDate.substring(6,10));
		
		String eTime = request.getParameter("eTime");
		hour = Integer.parseInt(eTime.substring(0,2));
		minute = Integer.parseInt(eTime.substring(3,5));
		amPm = eTime.substring(5,7);
		
		if(amPm.equals("PM")||amPm.equals("pm")||amPm.equals("Pm"))
			hour+=12;
		
		DateTime enDate = new DateTime(year, month, day, hour, minute, 0, 0);
		
		String instrId = request.getParameter("instrId");
		String courseId = request.getParameter("courseId");
		String seats = request.getParameter("seats");
		//int intSeats = Integer.parseInt(seats);
		String duration = request.getParameter("duration");
		//int intDuration = Integer.parseInt(duration);
		
		Exam e = new Exam(examId, stDate, enDate, instrId, courseId, Integer.parseInt(request.getParameter("seats")), Integer.parseInt(request.getParameter("duration")), false);
		instr.makeExam(e, stDate, enDate, true, instrId);
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
                            <a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li class="active">
                        <a href="ScheduleExamRequest.jsp"><span class="glyphicon glyphicon-calendar"></span></span></i> Schedule Exam Request</a>
                    </li>
                    <li>
                        <a href="SeeExamRequests.jsp"><i class="fa fa-fw fa-table"></i> See Exam Requests</a>
                    </li>
                    <li>
                        <a href="ApptAttendanceDetails.jsp"><span class="glyphicon glyphicon-ok"></span></span></i> Appt/Attendance Details</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-fw fa-dashboard"></i> Display Utilization Center</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>

        <div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Schedule Exam Confirmed
                        </h1>
                    </div>
                </div>
                <div class="row">
                	
                </div>
            </div>
            <!-- /.container-fluid -->

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