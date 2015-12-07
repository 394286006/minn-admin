<%@page import="p.minn.privilege.utils.Constant"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <meta HTTP-EQUIV="pragma" CONTENT="no-cache">  
<%
	 response.setHeader( "Pragma", "no-cache" );   
     response.setDateHeader("Expires", 0);   
     response.addHeader( "Cache-Control", "no-cache" );
     response.addHeader( "Cache-Control", "no-store" );
%>
    <%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
<html>
<head></head>
<body>
<jsp:include page="../../../common/common.jsp"></jsp:include>
<input id="menu_title" type="hidden" value=<sp:message code="user_title"/> >

    <div>
  
     <div style="float:left">
        <table class="flexdata_grid">  </table>
     </div>
       <div style="width:150px;float:left">  
         <input type="button" value="<sp:message code="user_save_user_resource"/>" onclick="save_user_role()">
           <div id="menu_sub_sys_div" style="width:150px;float:left"></div>
       
        </div>
     </div>
     
    <div style="visibility: hidden">
	<div id="dialog" >
	
	<table id="formtable">
	<tr><td><input type="hidden" id="id" name="id"></td></tr>
	  <tr><td><sp:message code="user_name"/>:</td><td><input type="text" id="name" name="name"></td></tr>
	  <tr><td><sp:message code="user_pwd"/>:</td><td> <input type="text" id=pwd name="pwd"></td></tr>
	  <tr><td><sp:message code="user_type"/>:</td><td><select id="type" name="type">
         </select></td></tr>
	  <tr><td><sp:message code="user_logintype"/>:</td><td><select id="logintype" name="logintype">
         </select></td></tr>
      <tr><td><sp:message code="common_active"/>:</td><td> 
        <select id="active" name="active">
         </select></td></tr>       
	
	</table>
	
</div>
</div>

<script type="text/javascript">

var columnstr='id,name,type_name,logintype_name,active_name,type,logintype,active'; 
var columnarr=columnstr.split(",");
var selected_user_id='';
$(function() {
	
    $(".flexdata_grid").flexigrid({
        url : sys_ctx_p+"/user?method=query&column="+columnstr, 
        dataType : 'json',
        colModel : [{
            display : 'ID',
            name : 'id',
            width : 90,
            sortable : true,
            align : 'center',
            hide:true
            },{
            display : '<sp:message code="user_name"/>',
            name : 'name',
            width : 90,
            sortable : true,
            align : 'center'
            }, {
                display : '<sp:message code="user_type"/>',
                name : 'type_name',
                width : 120,
                sortable : true,
                align : 'left'
            },  {
                display : '<sp:message code="user_logintype"/>',
                name : 'logintype_name',
                width : 120,
                sortable : true,
                align : 'left'
            }, {
            display : '<sp:message code="common_active" />' ,
            name : 'active_name',
            width : 80,
            sortable : true,
            align : 'right'
    } ,{
            display : '<sp:message code="user_type"/>', 
            name : 'type',
            width : 80,
            sortable : true,
            align : 'left',
            hide:true
		},{
             display : 'LOGINTYPE', 
             name : 'logintype',
             width : 80,
             sortable : true,
             align : 'left',
             hide:true
			},{
             display : 'ACTIVE', 
             name : 'active',
             width : 80,
             sortable : true,
             align : 'left',
             hide:true
						 }],
        buttons : [ {
            name : '<sp:message code="common_add"/>',
            bclass : 'add',
            onpress : dualAction
            }
            ,
            {
                name : '<sp:message code="common_edit"/>',
                bclass : 'edit',
                onpress : dualAction
            }
            ,
            {
                name : '<sp:message code="common_del"/>',
                bclass : 'delete',
                onpress : dualAction
            },
            {
                separator : true
            } 
        ],
        searchitems : [{
                display : '<sp:message code="common_search_name"/>',
                name : 'name',
                isdefault : true
        } ],
        sortname : "id",
        sortorder : "asc",
        usepager : true,
        title : '<sp:message code="user_title"/>',
        useRp : true,
        singleSelect: true,
        rp : 20,
        showToggleBtn : false,
        width : 700,
        height : 400,
        preProcess:flexigriddata
    });

  
    $(".flexdata_grid").click(function(e,grid){
    	if($('.trSelected', grid).length>0){
    	  var row=$('.trSelected', grid)[0].children;
    	  selected_user_id=row[0].innerText;
    	  if(selected_user_id==''||selected_user_id==undefined||selected_user_id=='undefined'){
    		  selected_user_id=row[0].textContent;
    	  }
    	  get_user_role(selected_user_id);
    	}
    });
    
    function dualAction(com, grid) {
    	
    	 reset('formtable');
        if (com =='<sp:message code="common_del"/>') {
        	if($('.trSelected', grid).length==0){
	          	   alert('请先选择数据!');
	          	  return;
	            }
        	  var row=$('.trSelected', grid)[0].children;
                
              del(row[0].innerText);
           
        } else if (com =='<sp:message code="common_edit"/>') {
	        	 if($('.trSelected', grid).length==0){
	          	   alert('请先选择数据!');
	          	  return;
	            }
        	   var row=$('.trSelected', grid)[0].children;
           
        	   
                  for(var i=0;i<columnarr.length;i++){
                	  $('#'+columnarr[i]).val(row[i].innerText);
                  }
                 
                  $('#type').val(parseInt(row[5].innerText));
                  $('#logintype').val(parseInt(row[6].innerText));
                  $('#active').val(parseInt(row[7].innerText));
         
                  $( "#dialog" ).dialog({title:com,width: 400,buttons: [{text: '<sp:message code="common_commit"/>',
                 	 click: function() {
                 		  edit();
                 		 }
                  },
                  {
                 	 text:  '<sp:message code="common_cancel"/>',
                 	 click: function() {
                 		 $( this ).dialog( "close" );
                 		 }
                  }
                  ]});
              
        }else if (com == '<sp:message code="common_add"/>') {
            // collect the data
           
      
             $( "#dialog" ).dialog({title:com,width: 400,buttons: [{text: '<sp:message code="common_commit"/>',
            	 click: function() {
            		   save();
            		 }
             },
             {
            	 text:  '<sp:message code="common_cancel"/>',
            	 click: function() {
            		 $( this ).dialog( "close" );
            		 }
             }
             ]});
        
        }
    }
    
  });
  
  //删除
   function del(id){
	   if(confirm("你确定要删除该数据吗!") == true){
		   $.post(sys_ctx_p+'/user?method=del', {id:id }
	       , function(data){
	    	   var d=eval(data);
	    	   if(d.success==true){
	    		   alert('Processing is complete！');
	    		   $(".flexdata_grid").flexReload();
	    	   }else{
	    		   alert(d.data);
	    	   }
	   });
	   }
   }
   
  //修改
    function edit(){
    	  $.post(sys_ctx_p+'/user?method=edit', { id:$('#id').val(),name: $('#name').val(),active:$('#active').val(),pwd:$.md5($('#pwd').val()),type:$('#type').val(),loginType:$('#logintype').val() }
	       , function(data){
	    	   var d=eval(data);
	    	   if(d.success==true){
	    		   alert('Processing is complete！');
	    		   $(".flexdata_grid").flexReload();
	    		   $( "#dialog" ).dialog('close');
	    	   }else{
	    		   alert(d.data);
	    	   }
	   });
	
    }
  
  //保存
	function save(){
		
	        
		    $.post(sys_ctx_p+'/user?method=save', {name: $('#name').val(),active:$('#active').val(),pwd:$.md5($('#pwd').val()),type:$('#type').val(),loginType:$('#logintype').val()}
	       , function(data){
	    	   var d=eval(data);
	    	   if(d.success==true){
	    		   alert('Processing is complete！');
	    		   $(".flexdata_grid").flexReload();
		           $( "#dialog" ).dialog('close');
	    	   }else{
	    		   alert(d.data);
	    	   }
	   });
	}

	

//树数据 
 function get_user_role(u_id){
	 $('#menu_sub_sys_div').empty();
	 $('#menu_sub_sys_div').removeAttr('class');
	 $('#menu_sub_sys_div').removeAttr('role');
	 $.ajax({url: sys_ctx_p+"/user?method=getUserRole",data:{userid:u_id},dataType: 'json'}).done(function( data ) {
		  var d=eval(data);
		  if(d.success==true){
		  	 $('#menu_sub_sys_div').jstree({ 'checkbox':{'three_state':false},'cascade':false,'plugins':["checkbox"],'core' : {'data' :d.data} });
		  }else{
			  alert(d.data);
		  }
	 });
 }
 
 get_user_role('');
 
$('#menu_sub_sys_div').on("click.jstree", function (e, data) {
	 //  var nodes= $('#menu_sub_sys_div').jstree(true).get_selected ();
	 
	 
 });
 
 //加载数据字典
$.ajax({url: sys_ctx_p+"/dic?method=getDic",data:{type:"'ACCOUNTTYPE','ACTIVETYPE','LOGINTYPE'"},dataType: 'json'}).done(function( data ) {
	  var d=eval(data.data);
	  //urltype,type
	  var accountTypes=d.ACCOUNTTYPE;
	  var actives=d.ACTIVETYPE;
	  var loginTypes=d.LOGINTYPE;
	  
	  $("#type").empty();
	  $("#active").empty();
	  $("#logintype").empty();
	  for(var i=0;i<accountTypes.length;i++){
		  $("#type").append("<option value='"+accountTypes[i].id+"'>"+accountTypes[i].text+"</option>");
	  }
	  for(var i=0;i<actives.length;i++){
			  $("#active").append("<option value='"+actives[i].id+"'>"+actives[i].text+"</option>");
	   }
	  for(var i=0;i<loginTypes.length;i++){
		  $("#logintype").append("<option value='"+loginTypes[i].id+"'>"+loginTypes[i].text+"</option>");
   }
	
});

 function save_user_role(){

	 var  nodes=$('#menu_sub_sys_div').jstree(true).get_checked(true);
	 var role_ids='';
	 var key=new Object();
	 for(var i=0;i<nodes.length;i++){
			var pathstr= $('#menu_sub_sys_div').jstree(true).get_path(nodes[i],',',true);
			var ids=pathstr.split(",");
			for(var j=0;j<ids.length;j++){
				key['p_'+ids[j]]=ids[j];
			}
			
		 }
	 for(var k in key){
		 if(role_ids!=""){
			 role_ids+=",";
		 }
		 role_ids+=key[k];
		
	 }
	 if(selected_user_id==''){
		 alert('请先选择用户数据!');
		 return;
	 }
	  $.post(sys_ctx_p+'/user?method=saveUserRole', {  userid:selected_user_id,roleids:role_ids  }
      , function(){
    	  alert('Processing is complete！');
  });
 }
 

 
</script>
