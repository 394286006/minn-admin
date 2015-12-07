<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
   <% 
       String configMethod= (String)request.getAttribute("configMethod");
       String fileName= (String)request.getAttribute("fileName");
       String autototalsql= (String)request.getAttribute("autototalsql");
       String autosql= (String)request.getAttribute("autosql");
    %>
<html>
<head></head>
<body>
<jsp:include page="../../../common/common.jsp"></jsp:include>
    <div>
        <div id="querycoinfigdiv_id">
    </div>
     <div style="float:left"  id="flexdata_grid_id">
     </div>
     
     </div>


<script type="text/javascript">

 var querykey=null;
 var columnstr=null;
 var autosql=null;
 var autototalsql=null;
 var configMethod='<%=configMethod%>';
 var fileName='<%=fileName%>';

 //加载数据字典
 
 configQueryChange();
 
 function configQueryChange(){
	 $('#querycoinfigdiv_id').empty();
	 $("#flexdata_grid_id").empty();
	 $("#flexdata_grid_id").append('<div class="flexdata_grid"/>');
	 
	 $.ajax({url: sys_ctx_p+"/common?method=getView",data:{name:configMethod,filename:fileName},dataType: 'json'}).done(function( data ) {
		  var d=eval(data);
		
		  autosql=d.data.autosql;
		  autototalsql=d.data.autototalsql;
	 	  var querydic=d.data.querydic;
	 	  querykey=d.data.querykey;
	 	  var gridkey=d.data.gridkey;
	 	 
	 	  generatorQuery(querykey,querydic);
	 	  
	 	  generatorgrid(gridkey);
	 	 
	 	
	 });
	 
 }
 
 function generatorgrid(gridkey){
	 var columns=new Array();
	 columnstr='';
	 for(var i=0;i<gridkey.length;i++){
		 columns.push({display:gridkey[i][gridkey[i].resourcekey],name:gridkey[i].column,hide:gridkey[i].hide,width:gridkey[i].width});
		 if(columnstr!=''){
			 columnstr+=",";
		 }
		 columnstr+=gridkey[i].column;
	 }
	 
	 $(".flexdata_grid").flexigrid({ 
	        url : sys_ctx_p+"/common?method=configquery&name="+configMethod+"&filename="+fileName+"&column="+columnstr+"&autototalsql="+autototalsql+"&autosql="+autosql+"&qkey=&qval=", 
	        dataType : 'json',
	        colModel : columns,
	        sortname : "id",
	        sortorder : "asc",
	        usepager : true,
	        title : $('#configquery_id').find("option:selected").text(),
	        useRp : true,
	        singleSelect: true,
	        rp : 20,
	        showToggleBtn : false,
	        width : 800,
	        height : 400,
	        preProcess:flexigriddata
	    });
	 
 }
 
 
 function generatorQuery(querykey,querydic){
	
	  for(var i=0;i<querykey.length;i++){
		  
		  $('#querycoinfigdiv_id').append("<span>"+querykey[i][querykey[i].resourcekey]+":</span>");
		  if(querykey[i].ctype=='select'){
			  var tmp="<select id='"+querykey[i].id+"' name='"+querykey[i].id+"'>";
			  var dic=querydic[querykey[i].mkey];
			  tmp+="<option/>";
			 for(var j=0;j<dic.length;j++){
				  tmp+="<option value='"+dic[j].id+"'>"+dic[j].text+"</option>";
			  }
			  
			  tmp+="</select>";
			 $('#querycoinfigdiv_id').append(tmp);
		  }
		
		  if(querykey[i].ctype=='text'){
			 $('#querycoinfigdiv_id').append("<input id='"+querykey[i].id+"' name='"+querykey[i].id+"' type='"+querykey[i].ctype+"'/>");
		  }
	  }
	 $('#querycoinfigdiv_id').append('<input type="button" onclick="queryCondition()" value=\'<sp:message code="comnon_search"/>\'>');
 }
 
 

 
 function queryCondition(){
	 var queryurl=sys_ctx_p+"/common?method=configquery&name="+configMethod+"&filename="+fileName+"&column="+columnstr+"&autototalsql="+autototalsql+"&autosql="+autosql;
	 var qk='';
	 var qv='';
	  for(var i=0;i<querykey.length;i++){
		  if(qk!=''){
			  qk+=',';
			  qv+=',';
		  }
		 qk+=querykey[i].id;
		 qv+=($('#'+querykey[i].id).val()==''? 'unknown':$('#'+querykey[i].id).val());
	  }
	 $(".flexdata_grid").flexOptions({url:queryurl+"&qkey="+qk+"&qval="+qv});
	 $(".flexdata_grid").flexReload();
 }
 
 
</script>

</body>
</html>


