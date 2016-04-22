<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Welcome</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/signin.css"
	rel="stylesheet" />
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
	crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
</head>
<body>
	<div class="container">
		<c:if test="${connection!=null }">
			<!-- each row show 3 columns, each column for 1 profile -->
			<!-- let's try static layout first -->
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-md-6">
					<div class="well well-sm">
						<div class="row">
							<div class="col-sm-6 col-md-4">
								<img src="${connection.imageUrl}?type=large" alt=""
									class="img-rounded img-responsive" />
							</div>
							<div class="col-sm-6 col-md-8">
								<h4>${connection.displayName}</h4>
								<small><cite title="San Francisco, USA">San
										Francisco, USA <i class="glyphicon glyphicon-map-marker">
									</i>
								</cite></small>
								<p>
									<i class="glyphicon glyphicon-envelope"></i><br /> <i
										class="glyphicon glyphicon-globe"></i><a
										href="http://www.jquery2dotnet.com">www.jquery2dotnet.com</a>
									<br /> <i class="glyphicon glyphicon-gift"></i>June 02, 1988
								</p>

								<!-- Split button -->
								<!-- <div class="btn-group">
								<button type="button" class="btn btn-primary">Social</button>
								<button type="button" class="btn btn-primary dropdown-toggle"
									data-toggle="dropdown">
									<span class="caret"></span><span class="sr-only">Social</span>
								</button>
								<ul class="dropdown-menu" role="menu">
									<li><a href="#">Twitter</a></li>
									<li><a href="https://plus.google.com/+Jquery2dotnet/posts">Google
											+</a></li>
									<li><a href="https://www.facebook.com/jquery2dotnet">Facebook</a></li>
									<li class="divider"></li>
									<li><a href="#">Github</a></li>
								</ul>
							</div> -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</div>

	<c:if test="${connection == null }">
		<span>This Account do not have Social profile</span>
	</c:if>
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />

	<form action="${pageContext.request.contextPath}/logout">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<button class="btn btn-lg btn-primary btn-block" name="submit"
			type="submit" value="logout">logout</button>
	</form>
</body>
</html>
