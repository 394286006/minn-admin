<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page language="java" import="p.minn.privilege.entity.User" %>
<%@ page language="java" import="p.minn.privilege.utils.Constant" %>
    <% String sys_ctx_p= request.getContextPath(); 
       User loginuser=(User)session.getAttribute(Constant.LOGINUSER);
       String user_account="";
       if(loginuser!=null){
    	   user_account=loginuser.getName();
       }
    %>
<script type="text/javascript">
/**
 * @author minn
 * 
 */
  var  sys_ctx_p='<%=sys_ctx_p %>';
  var login_account='<%= user_account%>';
 
</script>
<input id="common_lang" type="hidden" value=<%= response.getLocale().getLanguage() %> >
<link rel="stylesheet" href="<%=sys_ctx_p %>/css/themes/default/style.css" />
<link rel="stylesheet" href="<%=sys_ctx_p %>/css/jquery-ui.css" />
<link rel="stylesheet" href="<%=sys_ctx_p %>/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=sys_ctx_p %>/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="<%=sys_ctx_p %>/css/flexigrid.pack.css" />
<script type="text/javascript" src="<%=sys_ctx_p %>/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=sys_ctx_p %>/js/jquery.md5.js"></script>
<script  type="text/javascript" src="<%=sys_ctx_p %>/js/jstree.js"></script>
<script  type="text/javascript" src="<%=sys_ctx_p %>/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=sys_ctx_p %>/js/flexigrid.pack.js"></script>
<script type="text/javascript" src="<%=sys_ctx_p %>/js/bootstrap.min.js"></script>
<script type="text/javascript">
  var flexigriddata=function(jsondata){
		if(jsondata.success==true){
    		return jsondata.data;
    	}else{
    		alert(jsondata.data);
    	}
  };
  
  var reset=function (rname){
		 $('#'+rname).find('input').val('');
	  };
</script>
