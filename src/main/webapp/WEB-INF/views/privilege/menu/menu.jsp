<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head></head>
<body>
<jsp:include page="../../../common/common.jsp"></jsp:include>
<input id="menu_title" type="hidden" value=<sp:message code="menu_title"/> >

    <div>
     <div id="menu_sub_sys_div" style="width:300px;float:left">
     </div>
     <div style="float:left">
        <table class="flexdata_grid">  </table>
     </div>
     </div>
     
    <div style="visibility: hidden">
	<div id="dialog" >
	<form id="actionform" action="menu/save">
	<table id="formtable">
	<tr><td><input type="hidden" id="id" name="id"></td></tr>
	  <tr><td><sp:message code="menu_name_en"/>:</td><td><input type="text" id="name_en" name="name_en"></td></tr>
	  <tr><td><sp:message code="menu_name_zh"/>:</td><td><input type="text" id="name_zh" name="name_zh"></td></tr>
	  <tr><td><sp:message code="menu_url"/>:</td><td><input type="text" id="url" name="url"></td></tr>
	   <tr><td><sp:message code="menu_pId"/>:</td><td>  
	            <input type="hidden" id="pId" name="pId" />
	            <input type="text" id="pId_name" name="pId_name" readonly="readonly"/>
            </td></tr>
	   <tr><td><sp:message code="menu_urltype"/>:</td><td>  
	    <select id="urltype" name="urltype">
         </select>
            </td></tr>
	  <tr><td><sp:message code="menu_type"/>:</td><td>  
	    <select id="type" name="type">
         </select>
            </td></tr>
      <tr><td><sp:message code="common_active"/>:</td><td> 
        <select id="active" name="active">
         </select></td></tr>       
	  <tr><td><sp:message code="menu_code"/>:</td><td><input type="text" id="code" name="code" ></td></tr>
	  <tr><td><sp:message code="menu_sort"/>:</td><td><input type="text" id="sort" name="sort"></td></tr>
	</table>
	</form>
</div>
</div>

<script type="text/javascript">

var columnstr='id,name_en,name_zh,url,pNode,type,code,sort,urltype,type_v,urltype_v,active,active_v,pId'; 
var columnarr=columnstr.split(",");
$(function() {
	
    $(".flexdata_grid").flexigrid({
        url : sys_ctx_p+"/menu?method=query&column="+columnstr+"&nodeid=-1", 
        dataType : 'json',
        colModel : [{
            display : '<sp:message code="common_id"/>',
            name : 'id',
            width : 90,
            sortable : true,
            align : 'center',
            hide:true
            },{
            display : '<sp:message code="menu_name_en"/>',
            name : 'name_en',
            width : 90,
            sortable : true,
            align : 'center'
            }, {
                display : '<sp:message code="menu_name_zh"/>',
                name : 'name_zh',
                width : 120,
                sortable : true,
                align : 'left'
            }, {
                display : '<sp:message code="menu_url"/>',
                name : 'url',
                width : 120,
                sortable : true,
                align : 'left'
            }, {
                display : '<sp:message code="menu_pId"/>',
                name : 'pNode',
                width : 80,
                sortable : true,
                align : 'left'
            }, {
                display : '<sp:message code="menu_type"/>',
                name : 'type',
                width : 80,
                sortable : true,
                align : 'right'
        }, {
            display : '<sp:message code="menu_code" />' ,
            name : 'code',
            width : 80,
            sortable : true,
            align : 'right'
    } , {
        display : '<sp:message code="menu_sort"/>',
        name : 'sort',
        width : 80,
        sortable : true,
        align : 'right'
		}, {
	        display : '<sp:message code="menu_urltype"/>',
	        name : 'urltype',
	        width : 80,
	        sortable : true,
	        align : 'right',
	        hide:false
			}	, {
		        display : 'TYPE_V',
		        name : 'type_v',
		        width : 80,
		        sortable : true,
		        align : 'right',
		        hide:true
				}	, {
			        display : 'URLTYPE_V',
			        name : 'urltype_v',
			        width : 80,
			        sortable : true,
			        align : 'right',
			        hide:true
					}	, {
				        display : '<sp:message code="common_active"/>',
				        name : 'active',
				        width : 80,
				        sortable : true,
				        align : 'right',
				        hide:false
						}, {
					        display : 'ACTIVE_V',
					        name : 'active_v',
					        width : 80,
					        sortable : true,
					        align : 'right',
					        hide:true
						},{
			                display : 'PID',
			                name : 'pId',
			                width : 80,
			                sortable : true,
			                align : 'left',
			                hide:true
				            }			],
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
        sortname : "pNode",
        sortorder : "asc",
        usepager : true,
        title : '<sp:message code="menu_title"/>',
        useRp : true,
        singleSelect: true,
        rp : 20,
        showToggleBtn : false,
        width : 750,
        height : 400,
        preProcess:flexigriddata});

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
	        	 $('#code').attr('disabled','disabled'); 
        	   var row=$('.trSelected', grid)[0].children;
           
        	   
                  for(var i=0;i<columnarr.length;i++){
                	  $('#'+columnarr[i]).val(row[i].innerText);
                  }
                  $('#pId_name').val(row[4].innerText);
                  $('#urltype').val(parseInt(row[10].innerText));
                  $('#type').val(parseInt(row[9].innerText));
                  $('#active').val(parseInt(row[12].innerText));
                  $('#pId').val(parseInt(row[13].innerText));
         
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
            
            $('#code').removeAttr('disabled'); 
             var nodes= $('#menu_sub_sys_div').jstree(true).get_selected (true);
            if(nodes.length==0){
            	alert('请先选择数据!');
            	return;
            }
             $('#pId').val(nodes[0].id);
             $('#pId_name').val(nodes[0].text);
             $( "#dialog" ).dialog({title:com,width: 400,buttons: [{text: '<sp:message code="common_commit"/>',
            	 click: function() {
            		 checkcode();
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
    
   // $('#actionform').submit(function(event){
    	
    //	var fd=$('#actionform').serialize();
    	
    	//event.preventDefault();
  //  });
    
    
  });
  
  //删除
   function del(id){
	   if(confirm("你确定要删除该数据吗!") == true){
		   
			   $.post(sys_ctx_p+'/menu?method=del', { id:id}
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
	    
    	  $.post(sys_ctx_p+'/menu?method=edit', { id:$('#id').val(), nameEn: $('#name_en').val(),nameZh: $('#name_zh').val(), url: $('#url').val(),type: $('#type').val(),code: $('#code').val(),sort: $('#sort').val(),urlType:$('#urltype').val() ,active:$('#active').val(),pId:$('#pId').val() }
	       , function(data){
	    	   var d=eval(data);
	    	   if(d.success==true){
	    		   alert('Processing is complete！');
	    		   $(".flexdata_grid").flexReload();
		           $("#dialog").dialog('close');
	    	   }else{
	    		   alert(d.data);
	    	   }
	   });
	
    }
  
  //保存
	function save(){
		
		    $.post(sys_ctx_p+'/menu?method=save', {nameEn: $('#name_en').val(),nameZh: $('#name_zh').val(), url: $('#url').val(),type: $('#type').val(),code: $('#code').val(),sort: $('#sort').val(),pId:$('#pId').val(),urlType:$('#urltype').val(),active:$('#active').val()  }
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

  function checkcode(){
	 
	  if($('#type').val()==-1&&$('#code').val().length!=4){
		  alert('子系统识别码为4位数');
		  return;
	  }
	  if($('#type').val()==0&&$('#code').val().length!=8){
		  alert('菜单识别码为8位数,识别码=所属子系统识别码+自定义4位识别码');
		  return;
	  }
	  
	  if($('#type').val()==1&&$('#code').val().length!=12){
		  alert('按钮识别码为12位数，识别码=所属菜单识别码+自定义4位识别码');
		  return;
	  }
	  
	  
	  $.ajax({url: sys_ctx_p+"/menu?method=checkCode",data:{code:$('#code').val(),type:$('#type').val()},dataType: 'json'}).done(function( data ) {
		  var d=eval(data);
		  if(d.success==true){
			  if(d.data.count==0){
				  save();
			  }else{
				  alert('识别码已存在!');
			  }
		  }else{
			  alert(d.data);
		  }
		
	 });
	  
  }
	

//树数据 
$.ajax({url: sys_ctx_p+"/menu?method=getResource",data:{},dataType: 'json'}).done(function( data ) {
	  var d=eval(data.data);
	  $('#menu_sub_sys_div').jstree({ 'core' : {'data' :d},data:true });
	
 });

$('#menu_sub_sys_div').on("click.jstree", function (e, data) {
	   var nodes= $('#menu_sub_sys_div').jstree(true).get_selected ();
	   if(nodes.length>0){
		   $(".flexdata_grid").flexOptions({url:sys_ctx_p+"/menu?method=query&column="+columnstr+"&nodeid="+nodes[0]});
		   $(".flexdata_grid").flexReload();
	   }
	 
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

</script>

</body>
</html>
