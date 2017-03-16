<%@page import="p.minn.workflow.Constant"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
<html>
<head></head>
<body>
<jsp:include page="../../../common/common.jsp"></jsp:include>
<input id="menu_title" type="hidden" value=<sp:message code="menu_title"/> >
    <div>
        <div >
           <span><sp:message code="log_operator_name"/>:</span>  <input id="operatorname" type="text"/>
            <span><sp:message code="log_resname"/>:</span>
           <select id="resname" name="resname">
	         </select>
	          <span><sp:message code="common_id"/>:</span>  <input id="change_id" type="text"/>
            <span><sp:message code="log_operator_signature" />:</span>
	       <select id="signature" name="signature">
	         </select>
	         <input type="button" onclick="queryCondition()" value="<sp:message code="comnon_search"/>">
    </div>
     <div style="float:left">
        <table class="flexdata_grid">  </table>
     </div>
       <div style="width:150px;float:left">  
          <table class="flexdata_grid_log_detail">  </table>
       
        </div>
     </div>


<script type="text/javascript">

var columnstr='id,operatorname,resname,account_ip,operator_date,signature_name,tablekey_name,signature,tablekey'; 
var columnarr=columnstr.split(",");
var selected_id='';
$(function() {
	
    $(".flexdata_grid").flexigrid({
        url : sys_ctx_p+"/log?method=query&column="+columnstr, 
        dataType : 'json',
        colModel : [{
            display : 'ID',
            name : 'id',
            width : 90,
            sortable : true,
            align : 'center',
            hide:true
            },{
            display : '<sp:message code="log_operator_name"/>',
            name : 'operatorname',
            width : 90,
            sortable : true,
            align : 'center'
            }, {
                display : '<sp:message code="log_resname"/>',
                name : 'resname',
                width : 120,
                sortable : true,
                align : 'left'
            },  {
                display : '<sp:message code="log_account_ip"/>',
                name : 'account_ip',
                width : 120,
                sortable : true,
                align : 'left'
            }, {
            display : '<sp:message code="log_operator_date" />' ,
            name : 'operator_date',
            width : 150,
            sortable : true,
            align : 'right'
    } , {
        display : '<sp:message code="log_operator_signature" />' ,
        name : 'signature_name',
        width : 80,
        sortable : true,
        align : 'right'
} ],
       
        sortname : "id",
        sortorder : "asc",
        usepager : true,
        title : '<sp:message code="log_title"/>',
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
    	  selected_id=row[0].innerText;
    	
    	  if(selected_id==''||selected_id==undefined||selected_id=='undefined'){
    		  selected_id=row[0].textContent;
    	  }
    	  
    	  get_log_detail(selected_id);
    	}
    });
    

    
  });
  
  
var columnstr_log='id,k_name,val'; 
var columnarr_log=columnstr_log.split(",");

$(".flexdata_grid_log_detail").flexigrid({
    url : sys_ctx_p+"/log?method=getLogDetail&column="+columnstr+"&id=-1", 
    dataType : 'json',
    colModel : [{
        display : 'ID',
        name : 'id',
        width : 90,
        sortable : true,
        align : 'center',
        hide:true
        },{
        display : '<sp:message code="log_detail_change_name"/>',
        name : 'k_name',
        width : 90,
        sortable : true,
        align : 'center'
        }, {
            display : '<sp:message code="log_detail_change_val"/>',
            name : 'val',
            width : 120,
            sortable : true,
            align : 'left'
        }],
   
    sortname : "id",
    sortorder : "asc",
    usepager : false,
    title : '<sp:message code="log_detail_title"/>',
    useRp : true,
    singleSelect: true,
    width : 200,
    height : 400,
    preProcess:flexigriddata
});


	

//树数据 
 function get_log_detail(r_id){

	 $(".flexdata_grid_log_detail").flexOptions({url:sys_ctx_p+"/log?method=getLogDetail&column="+columnstr_log+"&id="+r_id});
	 $(".flexdata_grid_log_detail").flexReload();
 }
 


 
 //加载数据字典
$.ajax({url: sys_ctx_p+"/log?method=getSignature",data:{},dataType: 'json'}).done(function( data ) {
	  var d=eval(data.data);
	  //urltype,type
	  var signatures=d.SIGNATURE;
	  var resnames=d.RESOURCE;
	  $("#signature").empty();
	  $("#resname").empty();
	  
	  $("#signature").append("<option ></option>");
	  for(var i=0;i<signatures.length;i++){
		  $("#signature").append("<option value='"+signatures[i].id+"'>"+signatures[i].text+"</option>");
	  }
	  $("#resname").append("<option ></option>");
	  for(var i=0;i<resnames.length;i++){
		  $("#resname").append("<option value='"+resnames[i].id+"'>"+resnames[i].text+"</option>");
	  }
	
});

 function queryCondition(){
	 
	 $(".flexdata_grid").flexOptions({url:sys_ctx_p+"/log?method=query&column="+columnstr+"&operatorname="+$('#operatorname').val()+"&resname="+$('#resname').find("option:selected").val()+"&changeid="+$('#change_id').val()+"&signature="+$('#signature').val()});
	 $(".flexdata_grid").flexReload();
 }
 
</script>

</body>
</html>


