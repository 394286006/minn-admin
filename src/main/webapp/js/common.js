/**
×÷Õß£ºminn
QQ:394286006
email£ºfreemanfrelift@gmail.com
*/
var img1='services/uploadfile/imglevel1/';
var img2='services/uploadfile/imglevel2/';
var img_circle='img_circle_';
var private_key='';
var page_size=2; 
var page_flag=1;
var group_flag=2;
var pagegroup=new Array();
var page_group_size=3;
var getjson=function(data){
	return eval("("+decodeURIComponent(data)+")");
}

var decodedata=function(data){
	var arr=getjson(data);
	arr[2]=getjson(arr[2]);
	return arr;
}

var queryTest=function(cf){
	$.ajax({
       url: "services/test/securityRamdom.php",
       datatype: "text",
        success: function (data) {
		private_key=data;
			if(typeof(cf)!='undefined'){
				cf();
			}
			  
        },
        error: function () { alert("´íÎó"); }
                });
}

var stopBubble=function(e){

	if(e&&e.stopPropagation){
	  e.preventDefault();
	  e.stopPropagation();
     }else {
	 window.event.cancelBubble = true; 
	 }
}

var generated=function(type,curgroup,idx,curpage,lab,newgroup,subfix){

     if(typeof(newgroup)!='undefined'&&newgroup)
	 $('#page_id').html('');
	  if(typeof(subfix)=='undefined')
	  subfix='';
	  if(typeof(newgroup)=='undefined')
	  newgroup=false;
		  
	 var e='<li id="page_li_'+subfix+idx+'" ';
	 if(idx==curpage){
		e+=' class="disabled"';
	 }
	 e+='/>';
	 $(e).appendTo('#page_id');
	 e='<a id="page_li_a_'+subfix+idx+'" href="#" ';
	  if(idx==curpage){
		  e+='onClick="stopBubble(event)" ';
	  }else{
		   e+='onClick="gopage('+type+','+curgroup+','+idx+','+curpage+',\''+lab+'\','+newgroup;
		   if(subfix!=''){
			   e+=',\''+subfix+'\'';
		   }
		   e+=')"';
	  }
	 e+='/>';
	 $(e).appendTo('#page_li_'+subfix+idx);
	 $('#page_li_a_'+subfix+idx).text(lab);
			
}



var gopage=function(type,curgroup,idx,curpage,lab,newgroup,subfix){
	querypage(idx);
	 var page=pagegroup[curgroup];
  
	  if(curgroup>0){
		   generated(group_flag,curgroup-1,page[0]-1,idx,'<<',true,'pre_');
	  }
	
	 for(var i=0;i<page.length;i++){
		 var flag=false;
		    if(curgroup==0&&i==0){
				flag=true;
			}
		   generated(page_flag,curgroup,page[i],idx,page[i]+1,flag);
		
	 }
	
	 if(pagegroup.length-1>curgroup)
	   generated(group_flag,curgroup+1,page[page.length-1]+1,idx,'>>',false,'next_');
	 
	
	
}

var gengpagegroup=function(total){
	 var page;
			
	        for(var j=0;j<total;j++){
	
				if((j%(page_group_size*page_size))==0){
					   page=new Array();
					   pagegroup.push(page);
					  
				}
				if(j>0&&(j%(page_size))==0){
					  page.push(parseInt(j/(page_size)));
					 if(pagegroup.length==1){
					  generated(page_flag,0,parseInt(j/(page_size)),0,parseInt(j/(page_size))+1);
					 }
				}
				
		
				 
				 if(pagegroup.length==1){

					 if(j==0){
			
					   page.push(0);
					   generated(page_flag,0,0,0,1,true);
					  }
					
					
				 }
		    }
}