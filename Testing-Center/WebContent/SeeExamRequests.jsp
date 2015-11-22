<%@page import="DBWorks.DBConnection" %>

<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>See Exam Requests Page - Instructor</title>

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
                                        <h5 class="media-heading"><strong>John Smith</strong>
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
                                        <h5 class="media-heading"><strong>John Smith</strong>
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
                                        <h5 class="media-heading"><strong>John Smith</strong>
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
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li>
                        <a href="ScheduleExamRequest.jsp"><span class="glyphicon glyphicon-calendar"></span></span></i> Schedule Exam Request</a>
                    </li>
                    <li class="active">
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
                            See Exam Requests
                        </h1>
                    </div>
                </div>
                <jsp:useBean id="b" class="Bean.InstructorBean" scope="application" />
                
                <div class="row">
                <h3>Current Term</h3>
	                <table class="table table-bordered table-hover">
	                    <thead>
	                    <!-- Columns -->
	                        <tr class="active">
	                            <th>Course ID</th>
	                            <th>Duration</th>
	                            <th>Start Date</th>
	                            <th>End Date</th>
	                            <th>Status</th>
	                            <th>Action</th>
	                        </tr>
	                    <!-- /Columns -->
	                    </thead>
	                    <tbody>
	                    	
	                        <tr>
	                        <!--enter code here for table -->
	      					<%
	    
                            	String query = "SELECT examId, start, end, examStatus, numSeats, examLength, boolCourseExam, courseexam.courseIdCE "
                        				+ "FROM exam "
                        				+ "LEFT JOIN courseexam "
                        				+ "ON exam.examId=courseexam.examIdCE "
                        				+ "WHERE exam.instructorId = 'sstoller'";
	                        		
                               	java.sql.ResultSet rs = DBConnection.ExecQuery(query);
                               	while(rs.next())
                               	{
                                    String courseId = rs.getString(8);
                                    String duration = rs.getString(6);
                                    String sDate = rs.getString(2);
                                    String numSeats = rs.getString(5);
                                    //String sTime = rs.getString();
                                    String eDate = rs.getString(3);
                                    //String eTime = rs.getString();
                                    String status = rs.getString(4);
                            %> 
	                        <!-- row entries -->
	                            <td><%out.print(courseId);%></td>
	                            <td><%out.print(duration);%></td>
	                            <td><%out.print(sDate);%></td>
	                            <td><%out.print(eDate);%></td>
	                            <td>
	                            	<%if(status.equals("P"))
	                            	{%>
		                            	<button type="button" class="btn btn-sm btn-info">Pending</button>
		                           	<%}else if(status.equals("A"))
		                           	{%>
		                           		<button type="button" class="btn btn-sm btn-success">Approved</button>
		                           	<%}else
		                           	{%>
		                           		<button type="button" class="btn btn-sm btn-danger">Denied</button>
		                           	<%}
		                           	%>
				                </td>
	                            <td>
		                            <button type="button" class="btn btn-sm btn-danger">Cancel</button>
	                    		</td>
	                        <!-- /row entries -->    
	                        </tr>
	                        <%} %>
	                    </tbody>
	                </table>
	                <h3>Future Term</h3>
	                <table class="table table-bordered table-hover">
	                    <thead>
	                    <!-- Columns -->
	                        <tr class="active">
	                            <th>Course ID</th>
	                            <th>Section</th>
	                            <th>Term</th>
	                            <th>Duration</th>
	                            <th>Start Date</th>
	                            <th>Time</th>
	                            <th>End Date</th>
	                            <th>Time</th>
	                            <th>Status</th>
	                            <th>Action</th>
	                        </tr>
	                    <!-- /Columns -->
	                    </thead>
	                    <tbody>
	                        <tr>
	                        <!--enter code here for table -->
	                        <%  %>
	                        <!-- row entries -->
	                            <td></td>
	                            <td></td>
	                            <td></td>
	                            <td></td>
	                            <td></td>
	                            <td></td>
	                            <td></td>
	                            <td></td>
	                            <td>
		                            
				                </td>
	                            <td>
		                            
	                    		</td>
	                        <!-- /row entries -->    
	                        </tr>
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
