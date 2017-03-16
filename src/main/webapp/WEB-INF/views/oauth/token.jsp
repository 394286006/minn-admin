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
<jsp:include page="../../common/common.jsp"></jsp:include>

<center>
<% String lang= response.getLocale().getLanguage() ;%>
   <div class="container">

  <table>
   <tr><td><sp:message code="login_language"/>:</td><td>
   
    <select id="lang_name" name="lang_name" onchange="changelanguage()">
  <option value="zh" <%
     if(lang.equals("zh")){
  %>
  <%= "selected=selected" %>
  <%} %> ><sp:message code="login_language_zh"/></option>
  <option value="en" <%
     if(lang.equals("en")){
  %>
  <%= "selected=selected" %>
  <%} %>><sp:message code="login_language_en"/></option>
</select>
   </td></tr>
 </table>
<form  id="submit_form" name="submit_form" class="form-signin" role="form" onsubmit="return false;">
 <h2 class="form-signin-heading"><sp:message code="login_title"></sp:message></h2>
<table>
 <tr>
  <td><input type="hidden" name="method" value="login" /></td>
 <tr>
  <td><sp:message code="login_name"></sp:message>: </td><td><input class="form-control" type="text" id="username" name="username" value="admin"/></td></tr>
  <tr> <td><sp:message code="login_pwd"></sp:message>:</td><td> <input class="form-control" type="password" id="password" name="password" value="admin" /></td></tr>
 <tr> <td colspan="2" > <input type="submit" onClick="getToken();" class="btn btn-lg btn-primary btn-block" value=<sp:message code="login_action"/> ></td> </tr>
</table>
</form>
</div>
</center>
<script type="text/javascript">
var access_token='';
function getToken(){
	    $.post(sys_ctx_p+'/oauth/token', {grant_type: 'password',client_id:'oauth2_client',client_secret:'123456',username:$('#username').val(),password:$('#password').val()}
       , function(data){
    	   var d=eval(data);
    	   access_token=d.access_token;
    	   getResource();
   });
}

function getResource(){
	 window.location.href= sys_ctx_p+'/oauth2/hello?access_token='+access_token;
}


</script>
</body>
</html>