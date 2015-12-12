<%@page import="p.minn.privilege.utils.Constant"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head></head>
<meta http-equiv='X-Frame-Options' content='SAMEORIGIN' />
<body>
<jsp:include page="../../../common/common.jsp"></jsp:include>
<input id="menu_title" type="hidden" value=<sp:message code="menu_title"/> >

    <div>
     <div style="float:left">
        <table class="flexdata_grid">  </table>
     </div>
       <div style="width:150px;float:left">  
         <input type="button" value="<sp:message code="role_save_role_resource"/>" onclick="save_role_res()">
           <div id="menu_sub_sys_div" style="width:150px;float:left"></div>
       
        </div>
     </div>
     
    <div style="visibility: hidden">
	<div id="dialog" >
	 
	<table id="formtable">
	<tr><td><input type="hidden" id="id" name="id"></td></tr>
	  <tr><td><sp:message code="role_name_en"/>:</td><td><input type="text" id="name_en" name="name_en"></td></tr>
	  <tr><td><sp:message code="role_name_zh"/>:</td><td><input type="text" id="name_zh" name="name_zh"></td></tr>
	  <tr><td><sp:message code="role_comment"/>:</td><td> <input type="text" id="comment" name="comment"></td></tr>
      <tr><td><sp:message code="common_active"/>:</td><td> 
        <select id="active" name="active">
            <option value="0">禁用</option>
            <option value="1">启用</option>
         </select></td></tr>       
	  <tr><td><sp:message code="menu_code"/>:</td><td><input type="text" id="code" name="code" ></td></tr>
	  <tr><td><sp:message code="menu_sort"/>:</td><td><input type="text" id="sort" name="sort"></td></tr>
	</table>
	
</div>
</div>

<script type="text/javascript">

var columnstr='id,name_en,name_zh,comment,code,sort,active_name,active'; 
var columnarr=columnstr.split(",");
var selected_role_id='';
$(function() {
	
    $(".flexdata_grid").flexigrid({
        url : sys_ctx_p+"/role?method=query&column="+columnstr, 
        dataType : 'json',
        colModel : [{
            display : 'ID',
            name : 'id',
            width : 90,
            sortable : true,
            align : 'center',
            hide:true
            },{
            display : '<sp:message code="role_name_en"/>',
            name : 'name_en',
            width : 90,
            sortable : true,
            align : 'center'
            }, {
                display : '<sp:message code="role_name_zh"/>',
                name : 'name_zh',
                width : 120,
                sortable : true,
                align : 'left'
            },  {
                display : '<sp:message code="role_comment"/>',
                name : 'comment',
                width : 120,
                sortable : true,
                align : 'left'
            }, {
            display : '<sp:message code="role_code" />' ,
            name : 'code',
            width : 80,
            sortable : true,
            align : 'right'
    } , {
        display : '<sp:message code="role_sort"/>',
        name : 'sort',
        width : 80,
        sortable : true,
        align : 'right'
		},{
              display : '<sp:message code="common_active"/>', 
              name : 'active_name',
              width : 80,
              sortable : true,
              align : 'left'
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
            }
            ,
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
        title : '<sp:message code="role_title"/>',
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
    	  selected_role_id=row[0].innerText;
    	
    	  if(selected_role_id==''||selected_role_id==undefined||selected_role_id=='undefined'){
    		  selected_role_id=row[0].textContent;
    	  }
    	  
    	  get_role_res(selected_role_id);
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
	   $.post(sys_ctx_p+'/role?method=del', { id:id }
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
	  
    	  $.post(sys_ctx_p+'/role?method=edit', { id:$('#id').val(), nameEn: $('#name_en').val(),nameZh: $('#name_zh').val(), code: $('#code').val(),sort: $('#sort').val() ,active:$('#active').val(),comment:$('#comment').val() }
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
		
		 
		    $.post(sys_ctx_p+'/role?method=save', {  nameEn: $('#name_en').val(),nameZh: $('#name_zh').val(), code: $('#code').val(),sort: $('#sort').val(),active:$('#active').val(),comment:$('#comment').val()  }
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
 function get_role_res(r_id){
	 $('#menu_sub_sys_div').empty();
	 $('#menu_sub_sys_div').removeAttr('class');
	 $('#menu_sub_sys_div').removeAttr('role');
	 $.ajax({url: sys_ctx_p+"/role?method=getRoleRes",data:{roleid:r_id},dataType: 'json'}).done(function( data ) {
		  var d=eval(data);
		  if(d.success==true){
			  $('#menu_sub_sys_div').jstree({ 'checkbox':{'three_state':false},'cascade':false,'plugins':["checkbox"],'core' : {'data' :d.data} });
		  }else{
			  alert(d.data);
		  }
		
	 });
 }
 
get_role_res('');
	
$('#menu_sub_sys_div').on("click.jstree", function (e, data) {
	 //  var nodes= $('#menu_sub_sys_div').jstree(true).get_selected ();
	 
	 
 });
 
 //加载数据字典
$.ajax({url: sys_ctx_p+"/dic?method=getDic",data:{type:"'RESOURCETYPE','RESOURCEURLTYPE','ACTIVETYPE'"},dataType: 'json'}).done(function( data ) {
	  var d=eval(data.data);
	  //urltype,type
	  var urltypes=d.RESOURCEURLTYPE;
	  var actives=d.ACTIVETYPE;
	  var types=d.RESOURCETYPE;
	  $("#urltype").empty();
	  $("#active").empty();
	  $("#type").empty();
	  for(var i=0;i<urltypes.length;i++){
		  $("#urltype").append("<option value='"+urltypes[i].id+"'>"+urltypes[i].text+"</option>");
	  }
	  for(var i=0;i<actives.length;i++){
		  $("#active").append("<option value='"+actives[i].id+"'>"+actives[i].text+"</option>");
      }
	  for(var i=0;i<types.length;i++){
		  $("#type").append("<option value='"+types[i].id+"'>"+types[i].text+"</option>");
	  }
	
});

 function save_role_res(){

	 var  nodes=$('#menu_sub_sys_div').jstree(true).get_checked(true);
	 var resource_ids='';
	 var key=new Object();
	 for(var i=0;i<nodes.length;i++){
			var pathstr= $('#menu_sub_sys_div').jstree(true).get_path(nodes[i],',',true);
			var ids=pathstr.split(",");
			for(var j=0;j<ids.length;j++){
				key['p_'+ids[j]]=ids[j];
			}
			
		 }
	 for(var k in key){
		 if(resource_ids!=""){
			 resource_ids+=",";
		 }
		 resource_ids+=key[k];
		
	 }
	 if(selected_role_id==''){
		 alert('请先选择角色数据!');
		 return;
	 }
	  $.post(sys_ctx_p+'/role?method=saveRoleRes', {roleid:selected_role_id,resourceids:resource_ids  }
      , function(){
    	  alert('Processing is complete！');
  });
 }
 

 
</script>


</body>
</html>

