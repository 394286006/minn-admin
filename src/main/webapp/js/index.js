// JavaScript Document
/**
作者：minn
QQ:394286006
email：freemanfrelift@gmail.com
*/
var fadeout=4500;
var fadein=2000;
var delay=3500;
var picdata;

$(document).ready(function(){
  $("#relaction_id").click(function(){
    $(".relaction").html('<div class="alert fade in"><button type="button" class="close" data-dismiss="alert">&times;</button> <strong >&#20851;&#20110;</strong>&#25216;&#26415;&#25903&#25345; :minn &#37038;&#20214;:freemanfreelift@gmai.com &#21338;&#23458;:http://fmfl.iteye.com QQ:394286006</div>');
 
  });
  
queryTest(query);	


/*
  var pds=new Array();
   var pd=new Object();

   pd.show=1;
  pds.push(pd);
     var pd=new Object();
   pd.show=0;
  pds.push(pd);
     var pd=new Object();
   pd.show=0;
  pds.push(pd);
  picdata.push(pds);
    var pds=new Array();
   var pd=new Object();

   pd.show=1;
  pds.push(pd);
     var pd=new Object();
   pd.show=0;
  pds.push(pd);
     var pd=new Object();
   pd.show=0;
  pds.push(pd);
  picdata.push(pds);
  

  for(var i=0;i<picdata.length;i++){
	  $("#img_circle_"+i+"_0").hover(function(evt){
					
					   $("#"+$(this).attr('id')).stop();
							
				},function(evt){
					   var id=$(this).attr('id');
					  pichidden(id[2],id[3]);
					});
	    pichidden(i,0);
		
  }
*/

});


    

var query=function(){
	$.ajax({
       url: "data/firstpic.txt",
       datatype: "json",
        success: function (data) {

			var jarr=getjson(data);
		  picdata=new Array();
		$('.carousel-inner').html('');
            for(var i=0;i<jarr.length;i++){
				
			      if(i==1){
					   $('<div id="item_'+i+'"  class="item active"/>').appendTo('.carousel-inner');
				  }else{
					   $('<div id="item_'+i+'"  class="item "/>').appendTo('.carousel-inner');
				  }
				//alert(jarr[i]._photos.length);
				 var ps=jarr[i]._photos;
				 $('<img alt="" src="'+img1+ps[0].imgpath+'"/>').appendTo('#item_'+i);
				 $('<div id="container_'+i+'" class="container"/>').appendTo('#item_'+i);
				 $('<div id="carousel-caption_'+i+'" class="carousel-caption"/>').appendTo('#container_'+i);
				 $('<h1>'+jarr[i].name+'</h1>').appendTo('#carousel-caption_'+i);
				 $('<p class="lead">'+jarr[i].name+'</p>').appendTo('#carousel-caption_'+i);
				 $('<a id="a_'+i+'" href="#" class="btn btn-large btn-primary"></a').appendTo('#carousel-caption_'+i);
				 $('#a_'+i).text('更多');
				 
		
				   if(i<3){
				 var pds=new Array();
				 for(var j=0;j<ps.length;j++){
					  var pd=new Object();
					  pd.id=img_circle+i+'_'+j;
					  if(j==0){
						  // filter:alpha(Opacity=80);-moz-opacity:0.5;opacity: 0.5;z-index:100; background-color:#ffffff
						    $('<div id="'+pd.id+'"  class="span3" style="cursor:hand;"/>').appendTo('.row');
					  }else{
						    $('<div id="'+pd.id+'"  class="span3"  style=" display:none;cursor:hand;"/>').appendTo('.row');
					  }
					 
					   $('<img  class="img-circle" data-src="holder.js/120x120"  style="cursor:hand;" src="'+img2+ps[j].imgpath+'"/>').appendTo('#'+pd.id);
					 //  $('<h2 style="cursor:hand;">'+jarr[i].name+'</h2>').appendTo('#'+pd.id);
					  $('<a id="a_'+i+'_'+j+'" href="#" class="btn btn-large btn-primary"></a').appendTo('#'+pd.id);
				     $('#a_'+i+'_'+j).text(jarr[i].name);
					   $("#"+pd.id).hover(function(evt){
					
					        //$("#"+$(this).attr('id')).stop();
							
				         },function(evt){
					      var id=$(this).attr('id').split('_');
						 
					       //  pichidden(id[2],id[3]);
					    });
					  if(j==0){
						  pd.show=1;
					  }else{
						  pd.show=0;
					  }
					 pds.push(pd);
				 }

			  
			   picdata.push(pds);
			   
			     pichidden(i,0);
				}
			}

			
			  
        },
        error: function () { alert("错误"); }
                });
}



var picshow=function(pidx,idx){
		
	     if(idx>=picdata[pidx].length){
			 idx=0;
		 }

		 $("#"+img_circle+pidx+"_"+idx).fadeIn(fadein,function(){pichidden(pidx,idx) });
		 picdata[pidx][idx].show=1;

	
}

var pichidden=function(pidx,idx){
	
   $("#"+img_circle+pidx+"_"+idx).delay(delay+pidx*fadeout/1.5).fadeOut(fadeout,function(){picshow(pidx,++idx) });

}
