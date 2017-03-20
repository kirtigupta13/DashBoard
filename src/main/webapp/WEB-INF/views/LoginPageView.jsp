<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<link href="resources/css/loginForm.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SCP DashBoard Web Application Login</title>
</head>
<body>
	<h1>SCP DashBoard Web Application</h1>
	<form:form name="submitForm" method="POST">
		<div class="imgcontainer">
			<img src="resources/images/cernerLogo.png" alt="Avatar"
				class="avatar">
		</div>
		<div class="container">
			<label>User Name</label> <input type="text" name="userName" /> <label>Password</label>
			<input type="password" name="password" /> <label>Domain Name</label>
			<input type="text" name="domainName" />
			<button type="submit">Login</button>
		</div>
	</form:form>
</body>
</html>