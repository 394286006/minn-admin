<%@page import="p.minn.privilege.utils.Constant"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>


<script type="text/javascript">

$(document).ready(function() {
   $('#submit_form').submit(function(){
	   $('#pwd').val($.md5($('#pwd').val()));
	  return true ;
   });
});

function changelanguage(){
	var url=window.location.href+"?lang="+$('#lang_name').val();
	url=url.substring(0,url.indexOf('?lang='))+"?lang="+$('#lang_name').val();
	window.location.href=url;
	window.location.load();
}


</script>
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
<form action="account" method="post" id="submit_form" name="submit_form" class="form-signin" role="form">
 <h2 class="form-signin-heading"><sp:message code="login_title"></sp:message></h2>
<table>
 <tr>
  <td><input type="hidden" name="method" value="login" /></td>
 <tr>
  <td><sp:message code="login_name"></sp:message>: </td><td><input class="form-control" type="text" id="username" name="username" value="admin"/></td></tr>
  <tr> <td><sp:message code="login_pwd"></sp:message>:</td><td> <input class="form-control" type="password" id="password" name="password" value="admin" /></td></tr>
 <tr> <td colspan="2" > <input type="submit" class="btn btn-lg btn-primary btn-block" value=<sp:message code="login_action"/> ></td> </tr>
</table>
</form>
</div>
</center>