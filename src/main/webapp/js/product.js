// JavaScript Document
var jdata;
$(document).ready(function(){
		callBack();
	 });

var total;
var callBack=function(){
	
	var param ='messageBody={"page":0,"query":"2","qtype":"categoryid","rp":20}&lang="zh"';
	
	$.ajax({
       url: "frontpic?method=frontquery",
       datatype: "json",
	   data:param,
        success: function (data) {
	      var jrs=data.data;
		 if(data.success==true){
			 jdata=jrs.result;
	       	genproduct();
			
		 }
			 
        },
        error: function () { alert("错误"); }
                });
	
	
}

var querypage=function(cur){
	
		var param = JSON.stringify({'_sid':private_key, 'type_name':'','type_maxcategory_id':'','type_category_id':'','type_price_start':'','type_price_end':'','pageIndex':cur,'pageSize':page_size,'recordCount':total});
	$('.thumbnails>li').remove();
	$.ajax({
       url: "./json.php/front.product.ProductService.queryProduct/",
       datatype: "json",
	   data:param,
        success: function (data) {
	      var jarr=getjson(data);

		 if(jarr[0]==1){
			 total=jarr[3];

	    	jdata=jarr[2];
	       
			genproduct();
		 }
			  
        },
        error: function () { alert("错误"); }
                });
	
	
}

 var genproduct=function(){
	 for(var i=0;i<jdata.length;i++){
		  if(i%3==0){
				$('<li id="thumbnail_'+i+'"  class="span4"   style="margin-left:0px;"/>').appendTo('.thumbnails');
			
			  }else{
					$('<li id="thumbnail_'+i+'"  class="span4"/>').appendTo('.thumbnails');
			  }
    
		  $('<div id="content_'+i+'"  class="thumbnail"/>').appendTo('#thumbnail_'+i);
		  $('<img  src="'+jdata[i].imgpath+'"/>').appendTo('#content_'+i);
		  $('<h3>'+jdata[i].name+"</h3>").appendTo('#content_'+i);
		 // $('<p>'+jdata[i].descript+"</p>").appendTo('#content_'+i);

			}
}

var openswf=function(idx){
	 var ps=jdata[idx]._photos;
	 var param3=jdata[idx].name;
	 var param1='';
	 var param2='';
	 for(var i=0;i<ps.length;i++){
		 if(i>0){
			 param1+=',';
			 param2+=',';
		 }
		  param1+=ps[i].imgpath.split('.')[0];
		  param2+=ps[i].descript;
	 }
	
	window.open('minnPicShow.html?param1='+encodeURIComponent(param1)+'&param2='+encodeURIComponent(param2)+'&param3='+encodeURIComponent(param3),'newwindown');
}
   

var old_idx=-1;
var openimg=function(ev,el){
	stopBubble(ev);

	$('#thumbnail_sp_id').remove();
	
	var arr=el.split('_');

	if(old_idx==arr[1]){
		hiddenpic();
		old_idx=-1;
		return;
	}

	old_idx=arr[1];
	var curimg=jdata[arr[1]]._photos;
	var cl=$('.thumbnails').children().length;
    var r=parseInt(arr[1]/3);
    var eid='';
	if(r==0&&cl<3){
		eid=arr[0]+'_'+(cl-1);
	}else{
	   if(r==0&&cl>3){
		   eid=arr[0]+'_'+parseInt(arr[1]/3+2);
		  }else{
		     if(cl>3&&(cl%3==0)||(parseInt(arr[1]/3)!=parseInt(cl/3))){
			     eid=arr[0]+'_'+(parseInt(arr[1]/3)*3+2);
			   }else{
			  	 eid=arr[0]+'_'+(cl-1);
					}
	     }
	}
	
	
	var addel='<li id="thumbnail_sp_id"   style="margin-left:0px; width:100%;height:40%;"><div  class="thumbnail"> <div class="carousel slide"><ol class="carousel-indicators">';
	
	for(var i=0;i<curimg.length;i++){
		addel+='<li data-target="#carousel-example-generic" data-slide-to="'+i+'" ';
		 if(i==0){
			 addel+='class="active"';
		 }
		 
	   addel+='></li>';
	}
	addel+='</ol><div class="carousel-inner" >';
	for(var i=0;i<curimg.length;i++){
		addel+='<div class="item ';
		 if(i==0){
			 addel+=' active';
		 }
		 
	   addel+=' "><img  src="'+img1+curimg[i].imgpath+'"/></div>';
	}
	addel+=' </div><a class="left carousel-control" href="http://twitter.github.io/bootstrap/carousel.html#myCarousel" onClick="changepic(0)" data-slide="prev">‹</a><a class="right carousel-control" href="http://twitter.github.io/bootstrap/carousel.html#myCarousel" onClick="changepic(1)" data-slide="next">›</a></div></div></li>';
	
	$('#'+eid).after(addel);
	
	
}

var hiddenpic=function(){

		$('#thumbnail_sp_id').slideToggle();

}

var changepic=function(type,idx){

	var pl=$('.carousel-inner').children().length;
	var i=0;
	var curac=0;

 $('.carousel-inner>div').each(function(){
									
			if($(this).hasClass('active')){
				
				curac=i;
				 $('.carousel-indicators>li:eq('+curac+')').removeClass('active');
			  $(this).removeClass('active');
			}
			++i;
			
			});
 
   switch(type){
	   case 0:
	    
	    if((curac-1)<0){
			curac=pl;
		}
		$('.carousel-inner>div:eq('+(curac-1)+')').addClass('active');
			$('.carousel-indicators>li:eq('+(curac-1)+')').addClass('active');
		
	   break;
	   case 1:
	   
	   if((curac+1)>=pl){
			curac=-1;
			}
	     $('.carousel-inner>div:eq('+(curac+1)+')').addClass('active');
		 $('.carousel-indicators>li:eq('+(curac+1)+')').addClass('active');
	
	   break;
   }
}



