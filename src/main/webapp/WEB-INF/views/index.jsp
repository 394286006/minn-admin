<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page language="java" import="p.minn.workflow.Constant" %>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv='X-Frame-Options' content='SAMEORIGIN' />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><sp:message code="main_title"/></title>

</head>
<body>
<jsp:include page="../common/common.jsp"></jsp:include>

<%
   Object user=  session.getAttribute(Constant.LOGINUSER);
if(user==null){
%>
<jsp:include page="../login/login.jsp" flush="true"/>
 <%
}else{
 %>
 <jsp:include page="../layouts/layout.jsp" flush="true"/>
 <%
 }
 %>
</body>
</html>