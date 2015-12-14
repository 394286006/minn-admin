<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">

/*
 * Base structure
 */

/* Move down content because we have a fixed navbar that is 50px tall */
body {
  padding-top: 50px;
}


/*
 * Global add-ons
 */

.sub-header {
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

/*
 * Top navigation
 * Hide default border to remove 1px line.
 */
.navbar-fixed-top {
  border: 0;
}

/*
 * Sidebar
 */

/* Hide for mobile, show later */
.sidebar {
  /*display: none;*/
}
@media (min-width: 768px) {
  .sidebar {
    position: fixed;
    top: 51px;
    bottom: 0;
    left: 0;
    z-index: 1000;
    display: block;
    padding: 20px;
    overflow-x: hidden;
    overflow-y: auto; /* Scrollable contents if viewport is shorter than content. */
    background-color: #f5f5f5;
    border-right: 1px solid #eee;
  }
}

/* Sidebar navigation */
.nav-sidebar {
  margin-right: -21px; /* 20px padding + 1px border */
  margin-bottom: 20px;
  margin-left: -20px;
}
.nav-sidebar > li > a {
  padding-right: 20px;
  padding-left: 20px;
}
.nav-sidebar > .active > a,
.nav-sidebar > .active > a:hover,
.nav-sidebar > .active > a:focus {
  color: #fff;
  background-color: #428bca;
}


/*
 * Main content
 */

.main {
  padding: 20px;
}
@media (min-width: 768px) {
  .main {
    padding-right: 40px;
    padding-left: 40px;
  }
}
.main .page-header {
  margin-top: 0;
}


/*
 * Placeholder dashboard ideas
 */

.placeholders {
  margin-bottom: 30px;
  text-align: center;
}
.placeholders h4 {
  margin-bottom: 0;
}
.placeholder {
  margin-bottom: 20px;
}
.placeholder img {
  display: inline-block;
  border-radius: 50%;
}

</style>
<nav class="navbar navbar-inverse navbar-fixed-top " role="navigation">
<div class="container-fluid  btn-primary btn-block">
        <div class="navbar-header">
          <a class="navbar-brand" style="color:black" href="#"><sp:message code="main_title"/></a>
            <a class="navbar-brand" style="color:black" href="#"><sp:message code="main_login_account"/>:</a>
            <a  class="navbar-brand" style="color:black" href="#"  id="login_name_show"></a>
            <input class="navbar-brand" type="button" onclick="logout();" value=<sp:message code="login_out"/>>
        </div>
      </div>
</nav>
 <div class="container-fluid">
      <div class="row">
        <div id="jstree_menu_div" class="sidebar" style="float: left">
        </div>
        <div  id="componment_page_id" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main" style="float: left" >
        </div>
      </div>
    </div>
    <form action="j_spring_cas_security_logout" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

 <script type="text/javascript">
 
   $('#login_name_show').html(login_account);
 
    /**
     *@author minn
     *
     */
 	var sys_pmenu=new Object();
 
	 /**
	  * 菜单工具
	  */
	 function generatedMenu(m){
	 	 for(var i=0;i<m.length;i++){
				 sys_pmenu['p_'+m[i].id]=m[i];
			 }
	 	  $('#jstree_menu_div').jstree({ 'core' : {'data' :m},data:true });
	 }
	 
	 /**
	  * 查找菜单
	  */
	 function getMenu(id){
	 	return sys_pmenu['p_'+id];
	 }
	/**
	 * 页面查询
	 */
	 function go2page(node){
		   if(node.urltype==0){
			   if(node.type==0){
				   $('#componment_page_id').html("<iframe width='100%' height='768' style='border: none;' src="+sys_ctx_p+"/common?method=index&pidx="+node.url+"&mid="+node.id+"></iframe>");
			   }
		   }else{
			   if(node.type==0)
				   $('#componment_page_id').html("<iframe width='100%' height='768' style='border: none;' src="+node.url+"&mid="+node.id+"></iframe>");
		   }
		
	 }
	
 
 $.ajax({url: sys_ctx_p+"/menu?method=getPrivateMenu",data:{},dataType: 'json'}).done(function( data,status ,info) {
		 var d=eval(data);
		 if(d.success==true){
			 generatedMenu(d.data);
		 }else{
			 alert(d.data);
		 }
		
	  });

  $('#jstree_menu_div').on("click.jstree", function (e, data) {
	   var nodes= $('#jstree_menu_div').jstree(true).get_selected (true);
	   if(nodes.length>0){
		   var node=getMenu(nodes[0].id);
		   go2page(node);
	   }
	});
 
  
  function logout(){
	  window.location.href="http://127.0.0.1:8084/cas/logout?service=http://127.0.0.1:8080/admin/logout";
	 // document.getElementById("logoutForm").submit();
	}
    </script>
</body>
</html>
    