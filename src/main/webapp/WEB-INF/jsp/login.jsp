<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Login Page</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css"
	rel="stylesheet" />
<link href="./css/signin.css" rel="stylesheet" /> 
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
<body onload='document.f.username.focus();'>




	<div class="container">


		<div class="omb_login">
			<h3 class="omb_authTitle">
				Login or <a href="#">Sign up</a>
			</h3>
			<form action="<c:url value="/signin/facebook" />" method="POST">
				<div class="row omb_row-sm-offset-3 omb_socialButtons">
					<div class="col-xs-4 col-sm-2">
						<button type="submit"
							class="btn btn-lg btn-block omb_btn-facebook">
							<i class="fa fa-facebook visible-xs"></i> <span class="hidden-xs">Facebook</span>
						</button>
						<input type="hidden" name="scope"
							value="email,user_friends,user_posts,user_about_me" />
					</div>
					<div class="col-xs-4 col-sm-2">
						<a href="#" class="btn btn-lg btn-block omb_btn-twitter"> <i
							class="fa fa-twitter visible-xs"></i> <span class="hidden-xs">Twitter</span>
						</a>
					</div>
					<div class="col-xs-4 col-sm-2">
						<a href="#" class="btn btn-lg btn-block omb_btn-google"> <i
							class="fa fa-google-plus visible-xs"></i> <span class="hidden-xs">Google+</span>
						</a>
					</div>
				</div>
			</form>

			<div class="row omb_row-sm-offset-3 omb_loginOr">
				<div class="col-xs-12 col-sm-6">
					<hr class="omb_hrOr">
					<span class="omb_spanOr">or</span>
				</div>
			</div>

			<div class="row omb_row-sm-offset-3">
				<div class="col-xs-12 col-sm-6">
					<form:form>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<form class="omb_loginForm" name='f' action='login'
							autocomplete="off" method="POST">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user"></i></span>
								<input type="text" class="form-control" name="username"
									placeholder="email address">
							</div>
							<span class="help-block"></span>

							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-lock"></i></span>
								<input type="password" class="form-control" name="password"
									placeholder="Password">
							</div>
							<span class="help-block"> <!-- Password error -->
							</span>

							<button class="btn btn-lg btn-primary btn-block" name="submit"
								type="submit" value="Login">Login</button>
						</form>
					</form:form>
				</div>
			</div>
			<div class="row omb_row-sm-offset-3">
				<div class="col-xs-12 col-sm-3">
					<label class="checkbox"> <input type="checkbox"
						value="remember-me">Remember Me
					</label>
				</div>
				<div class="col-xs-12 col-sm-3">
					<p class="omb_forgotPwd">
						<a href="#">Forgot password?</a>
					</p>
				</div>
			</div>
		</div>



	</div>
</body>
</html>