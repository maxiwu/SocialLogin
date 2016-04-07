<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<meta charset="utf-8">
<title>Bootstrap 3, from LayoutIt!</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.btn-circle.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet">

<link
	href="${pageContext.request.contextPath}/resources/font-awesome-4.3.0/css/font-awesome.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/font-awesome-4.3.0/css/custom.css"
	rel="stylesheet">

<link
	href="${pageContext.request.contextPath}/resources/bootstrap-toogle/css/bootstrap2-toggle.min.css"
	rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

<!-- Fav and touch icons -->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="${pageContext.request.contextPath}/resources/img/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="${pageContext.request.contextPath}/resources/img/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="${pageContext.request.contextPath}/resources/img/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="${pageContext.request.contextPath}/resources/img/apple-touch-icon-57-precomposed.png">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/img/favicon.png">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>


<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/bootstrap-toogle/js/bootstrap2-toggle.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$.ajax({
			url : "/UCloudPortal/device/list"
		}).then(function(data) {
			//will return two json object
			//http://localhost:8080/UCloudPortal/device/list       
		});
	});
</script>
</head>
<body>
	<div class="container">


		<!-- each row show 3 columns, each column for 1 profile -->
		<!-- let's try static layout first -->

		<a href="./logout">logout</a>

		<div class="row clearfix well">
			<div class="col-md-4 show-grid">

				<div class="row clearfix">
					<div class="col-md-6">
						Test Value <br class=""> <span class="badge badge-default">Hz</span>
						<span class="lead pull-right text-primary">324.13</span>

					</div>
					<div class="col-md-6">
						Test Value <br class=""> <span class="badge badge-default">Hz</span>
						<span class="lead pull-right text-primary">324.13</span>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-md-6">
						Test button <br class=""> <input type="checkbox" checked
							data-toggle="toggle" data-width="48" data-height="28">
					</div>

					<div class="col-md-6">
						Configuration <br />
						<textarea class="form-control" rows="2"></textarea>
						<button type="button" class="btn btn-default">Save</button>
					</div>
				</div>
				<div class="row clearfix">

					<div class="col-xs-12">
						<div class="row">
							<div class="col-xs-3 text-center">key1</div>
							<div class="col-xs-3 text-center">key2</div>
							<div class="col-xs-3 text-center">key3</div>
							<div class="col-xs-3 text-center">key4</div>
						</div>
						<div class="row" style="padding-top: 1em;">
							<div class="col-xs-3 text-center">
								<i class="fa fa-circle fa-circle--active"></i>
							</div>
							<div class="col-xs-3 text-center">
								<i class="fa fa-circle"></i>
							</div>
							<div class="col-xs-3 text-center">
								<i class="fa fa-circle"></i>
							</div>
							<div class="col-xs-3 text-center">
								<i class="fa fa-circle"></i>
							</div>
						</div>
					</div>
				</div>

			</div>

			<div class="col-md-3">
				<div class="row">
					<button type="button" class="btn btn-default btn-lg btn-circle">
						<span class="glyphicon glyphicon-off"></span>
					</button>
					<!-- <input type="checkbox" checked data-toggle="toggle" data-width="48"
						data-height="28"> -->
					<button type="button" class="btn btn-default">Save</button>
				</div>

			</div>

		</div>
	</div>
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
</body>
</html>
