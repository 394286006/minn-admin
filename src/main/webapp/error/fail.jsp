<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title>登陆失败</title>
</head>

<body>
	<h2>重新登陆.</h2>
	<p><a href="http://127.0.0.1:8084/cas/login?service=http://127.0.0.1:8080/admin/login">登陆</a></p>
</body>
</html>