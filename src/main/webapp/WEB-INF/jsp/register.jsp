
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Register</title>
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
<body onload='document.f.username.focus();'>
	<%-- <h3>Custom Login</h3>
	<form name='f' action='register' method='post'>
		<sec:csrfInput />
		<input type="hidden" name="${_csrf.parameterName}"
        value="${_csrf.token}" />
		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='username' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="register" type="submit"
					value="register" /></td>
			</tr>

		</table>
	</form> --%>

	<div class="container">


		<div class="omb_login">
			<h3 class="omb_authTitle">Register with</h3>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			

			<div class="row omb_row-sm-offset-3 omb_loginOr">
				<div class="col-xs-12 col-sm-6">
					<hr class="omb_hrOr">
					<span class="omb_spanOr">or</span>
				</div>
			</div>

			<div class="row omb_row-sm-offset-3">
				<div class="col-xs-12 col-sm-6">


					<form:form class="omb_loginForm" name='f'
						action="${pageContext.request.contextPath}/user/register"
						modelAttribute="social" 
						autocomplete="off" method="POST">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
							
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-user"></i></span>
							<input type="text" class="form-control" name="username"
								value="${social != null ? social.email : ''}"
								placeholder="email address">
						</div>
						<span class="help-block"></span>

						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock"></i></span>
							<input type="password" class="form-control" name="password"
								placeholder="Password">
						</div>
						<span class="help-block"> <c:if
								test="${param.error == 'true'}">
								<span><spring:message
										code="loginPage.authenticationFailure" /></span>
							</c:if> <!-- Password error -->
						</span>

						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock"></i></span>
							<input type="password" class="form-control"
								name="confirm_password" placeholder="confirm">
						</div>
						<span class="help-block"> <c:if
								test="${param.error == 'true'}">
								<span><spring:message
										code="loginPage.authenticationFailure" /></span>
							</c:if> <!-- Password error -->
						</span>

						<button class="btn btn-lg btn-primary btn-block" name="submit"
							type="submit" value="Login">Register</button>


						<c:if test="${SocialProfile.signInProvider != null}">
							<form:hidden path="signInProvider" />
						</c:if>



					</form:form>
				</div>
			</div>

		</div>



	</div>
</body>
</html>