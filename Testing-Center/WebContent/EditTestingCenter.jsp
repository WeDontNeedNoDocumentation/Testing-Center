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

    <title>Edit Testing Center Information Page - Admin</title>

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
    <link href="css/addboxes.css" rel="stylesheet">

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
                   	<li class="active">
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

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Edit Testing Center Information
                        </h1>
                    </div>
                </div>
                <div class="row">
                	<form action="EditTestingCenterFields.jsp" method="post">
                		<div class="col-sm-6re">
               
		               <%
							String email = session.getAttribute("email").toString();
							String name = session.getAttribute("name").toString();
						
							Administrator admin = new Administrator(name, email);
							DateTimeUtil dtUtil = new DateTimeUtil();
						%>
                		
                		
                			<div class="form-group input-group">
		                        <span class="input-group-addon">Edit Number of Seats:</span>
		                        <input name="numSeats" type="text" class="form-control" placeholder="<%out.print(admin.viewNumberSeats()); %>">
		                        <span class="input-group-addon">Seats</span>
		                    </div>
		
		                    <div class="form-group input-group">
		                        <span class="input-group-addon">Edit Number of Set-Aside Seats:</span>
		                        <input name="numSASeats" type="text" class="form-control" placeholder="<%out.print(admin.viewSetAside()); %>">
		                        <span class="input-group-addon">Seats</span>
		                    </div>
		
		                    <!-- <div class="form-group input-group">
		                    	<span class="input-group-addon">Edit Testing Hours</span>
		                        <input name="testingHours" type="text" class="form-control" placeholder="10:00am">
		                    </div>
		
		                    <div class="form-group input-group">
		                        <span class="input-group-addon">Closed Days</span>
		                        <input name="eDate" type="text" class="form-control" placeholder="mm/dd/yyyy">
		                    </div>
		                    
		                    <div class="form-group input-group">
		                    	<span class="input-group-addon">Reserved Periods</span>
		                        <input name="reserved" type="text" class="form-control" placeholder="12:00pm">
		                    </div> -->
		                    
		                    <div class="form-group input-group">
		                        <span class="input-group-addon">Edit Gap Time:</span>
		                        <input name="gapTime" type="text" class="form-control" placeholder="<%out.print(admin.viewGapTime()); %>">
		                        <span class="input-group-addon">Minutes</span>
		                    </div>
		                    
		                    <div class="form-group input-group">
		                        <span class="input-group-addon">Reminder Interval:</span>
		                        <input name="reminderInt" type="text" class="form-control" placeholder="<%out.print(admin.viewReminder()); %>">
		                        <span class="input-group-addon">Minutes</span>
		                    </div>
		                    
		                    <button type="submit" value="submit">Submit</button>
			                
			                <div class="container">
			                
						</div>
					</form>
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
