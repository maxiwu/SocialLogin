<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Register Page</title>
</head>
<body onload='document.f.username.focus();'>
	<h3>Custom Login</h3>
	<form name='f' action='register' method='post'>
		<%-- <sec:csrfInput /> --%>
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
	</form>
</body>
</html>