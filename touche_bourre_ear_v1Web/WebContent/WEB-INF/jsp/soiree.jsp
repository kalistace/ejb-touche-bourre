<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<title>${nomSoiree}</title>

	<script src="js/jquery-1.7.1.min.js" type="text/javascript"></script>		
	<script src="js/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
	<script src="js/ajax.js" type="text/javascript"></script>	
	<script type="text/javascript" src="WebContent/js/keepAlive.js"></script>
	<script type="text/javascript" src="js/handleLeavePartie.js"></script>
	<link rel="stylesheet" type="text/css"  href="css/jquery-ui-1.8.16.custom.css"/>
	<link rel="stylesheet" type="text/css"  href="css/soiree.css"/>
	
	<script type="text/javascript">
	var nbrTables = 5;
	var pret = false;
	var tournee = 1;
	var myTables=new Array();
	var deuxiemeConnecte = 0;
	$(document).ready(function() {

	//	alert('${nomSoiree}');
		
		
		$( ".draggable" ).draggable({ revert: 'invalid',
									  grid: [ 40,40 ],		  
								  	  snapMode: 'inner'
                                    });
	
	
	
		$( ".droppable" ).droppable({
			drop: function( event, ui ) {
			
				
				var typeTable = ui.draggable.attr("class");
				typeTable = typeTable.charAt(10);	

				
				var t = ui.draggable.children().length;
				
				var x = $( this ).index();
				
				var y = $( this ).parent().index();
				
				
				var placementH = true;
				var placementV = true;
				
				
				if(ui.draggable.children().eq(0).hasClass("caseV")){
				//vertical
				var dec=Math.floor(t/2.5);
				y  = y-dec;
				for(var i=0;i<t;i++){
					if(y<0 || (y+t)>10) placementV = false;
					var casesd = $("#tabPlacement").children().children().eq(y+i).children().eq(x);
					if(casesd.html() != "")	placementV = false;		
				}
				//placement
				if(placementV){
					myTables.push("T" + typeTable);
					for( i=0;i<t;i++){
						casesd = $("#tabPlacement").children().children().eq(y+i).children().eq(x);				
						casesd.html(ui.draggable.children().eq(i).html()).addClass("case");
						myTables.push(x);
						myTables.push(y+i);
					}
					casesd.removeClass("droppable");
					
					$("."+typeTable).fadeOut();
					
					ui.draggable.remove();	
					nbrTables--;
									
				}else {
						ui.draggable.draggable('option','revert',true);
						
					 }
				////////////////////	
				}else{
				
				//horizontal
				var dec=Math.floor(t/2);
				x  = x-dec;
				for(var i=0;i<t;i++){
					if(x<0 || (x+t)>10) placementH = false;
					var casesd = $("#tabPlacement").children().children().eq(y).children().eq(x+i);
					if(casesd.html() != "")	placementH = false;		
				}
				if(placementH){
					myTables.push("T" + typeTable);
					for(var i=0;i<t;i++){
						casesd = $("#tabPlacement").children().children().eq(y).children().eq(x+i);				
						casesd.html(ui.draggable.children().eq(i).html()).addClass("case");
						myTables.push(x+i);
						myTables.push(y);												
					}
					casesd.removeClass("droppable");
					$("."+typeTable).fadeOut();
					ui.draggable.remove();	
					nbrTables--;
				}else {
						ui.draggable.draggable('option','revert',true);
						
					 }
				////////////////////
				}
				if(nbrTables==0){ 
					$("#pret").fadeIn();		
				}										
			}
			

		});

		
		$("#pret").click(function(){		
        			pret=true;
        			var params = "tables="+myTables.toString();
        			var req = new AjaxRequest("POST","pret.html",params, true);
        			req.handleResponse = function() {
        				rep=req.xhr.responseText;
        				//alert(rep);
        				//if(rep != 0) {
        			//		$("#msgTop").fadeIn().text("Placez les tables dans le bar à gauche!");
        				//	$("#tablesAPlacer").fadeIn();
        				//	$("#adversaire").fadeIn().text(rep);
        				//	deuxiemeConnecte=1;
        				//} 
        			};
        			req.process();	
        		
        			afficherBieres();
        			$(this).fadeOut();
        		} 
        );


		if(deuxiemeConnecte==0){
			setInterval( "deuxJoueursConnectes()", 3000 );
		} 
       
		$("#tabPlacement td").click(function(){
        		
        			var col = $(this).index();	
        			var row = $(this).parent().index();
        			//alert("col: "+col+" row: "+row);
        		}
        
        );

	    
                                    
        $("#tabTirs td").click(function(){
        		
        			var col = $(this).index();	
        			var row = $(this).parent().index();
        			if(pret && $(this).text()==""){
        				
        				$("#bieres").children().last().fadeOut(function(){ 
        				$(this).remove()});
        				$(this).text(tournee);
        				
        				if($("#bieres").children().length <= 1)
        				{
        					pret=false;
        					tournee++;
        					//afficher resulat
        					//donne la main à l'autre joueur
        					$("#msgTop").text("Attendez...");
        				}

        			
        			}
   	       
        	}
        );
	});
	
	function afficherBieres(){
		
		$("#tablesAPlacer").append("<div id='bieres'></div>").hide().fadeIn("slow");
		$("#msgTop").text("Cliquez sur le bar de droite");
				
		for(i=0;i<5;i++){
			$("#bieres").append("<img alt='beer' src='img/beer.png'/>").hide().fadeIn("slow");
		}
	
	}
	function deuxJoueursConnectes(){
		var params = "tables=";
		var req = new AjaxRequest("POST","pret.html",params, true);
		req.handleResponse = function() {
			rep=req.xhr.responseText;
			//alert(rep);
			if(rep != 0) {
				$("#msgTop").fadeIn().text("Placez les tables dans le bar à gauche!");
				$("#tablesAPlacer").fadeIn();
				$("#adversaire").fadeIn().text(rep);
				deuxiemeConnecte=1;
			} 
		};
		req.process();	
	}
	
	</script>
</head>
<body>
<div id="container">

<div id="head"></div>
<div class="gauche">
<p class="msg">${pseudo}</p>
<table class="tab" id="tabPlacement">

<tr>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
</tr>

<tr>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
</tr>

<tr>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
</tr>

<tr>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
</tr>

<tr>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
</tr>

<tr>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
</tr>

<tr>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
</tr>

<tr>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
</tr>

<tr>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
</tr>

<tr>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
<td class="droppable"></td>
</tr>

</table>
<input id="pret" class = "btnSmall" type = "submit" value = "Prêt" style="display:none" />

</div>

<div class="milieu" style ="" >
<p id="msgTop" class="msg">Attendez l'autre joueur...</p>
<div id="tablesAPlacer" style ="display:none;" >
<div class="draggable 1">
	<div class="caseV"><img alt="case" src="img/coupleV2.png"/></div>
	<div class="caseV"><img alt="case" src="img/coupleV1.png"/></div>
</div>

<div class="draggable 2">
	<div class="caseV"><img alt="case" src="img/fillesV1.png"/></div>
	<div class="caseV"><img alt="case" src="img/fillesV2.png"/></div>
	<div class="caseV"><img alt="case" src="img/fillesV3.png"/></div>
</div>


<div class="draggable 3">
	<div class="caseV"><img alt="case" src="img/garconsV1.png"/></div>
	<div class="caseV"><img alt="case" src="img/garconsV2.png"/></div>
	<div class="caseV"><img alt="case" src="img/garconsV3.png"/></div>
</div>

<div class="draggable 4">
	<div class="caseV"><img alt="case" src="img/potesV1.png"/></div>
	<div class="caseV"><img alt="case" src="img/potesV2.png"/></div>
	<div class="caseV"><img alt="case" src="img/potesV3.png"/></div>
	<div class="caseV"><img alt="case" src="img/potesV4.png"/></div>	
</div>

<div class="draggable 5">
	<div class="caseV"><img alt="case" src="img/comptoireV1.png"/></div>
	<div class="caseV"><img alt="case" src="img/comptoireV2.png"/></div>
	<div class="caseV"><img alt="case" src="img/comptoireV3.png"/></div>
	<div class="caseV"><img alt="case" src="img/comptoireV4.png"/></div>	
	<div class="caseV"><img alt="case" src="img/comptoireV5.png"/></div>	
</div>


<!--  -->
<div style="width: 210px">
<div class="draggable 1" style="width: 90px">
	<div class="caseH"><img alt="case" src="img/couple1.png"/></div>
	<div class="caseH"><img alt="case" src="img/couple2.png"/></div>
</div>

<div class="draggable 2"  style="width: 130px">
	<div class="caseH"><img alt="case" src="img/filles1.png"/></div>
	<div class="caseH"><img alt="case" src="img/filles2.png"/></div>
	<div class="caseH"><img alt="case" src="img/filles3.png"/></div>
</div>

<div class="draggable 3" style="width: 130px">
	<div class="caseH"><img alt="case" src="img/garcons1.png"/></div>
	<div class="caseH"><img alt="case" src="img/garcons2.png"/></div>
	<div class="caseH"><img alt="case" src="img/garcons3.png"/></div>
</div>

<div class="draggable 4" style="width: 170px">
	<div class="caseH"><img alt="case" src="img/potes1.png"/></div>
	<div class="caseH"><img alt="case" src="img/potes2.png"/></div>
	<div class="caseH"><img alt="case" src="img/potes3.png"/></div>
	<div class="caseH"><img alt="case" src="img/potes4.png"/></div>
</div>
<div class="draggable 5" style="width: 210px">
	<div class="caseH"><img alt="case" src="img/comptoire1.png"/></div>
	<div class="caseH"><img alt="case" src="img/comptoire2.png"/></div>
	<div class="caseH"><img alt="case" src="img/comptoire3.png"/></div>
	<div class="caseH"><img alt="case" src="img/comptoire4.png"/></div>
	<div class="caseH"><img alt="case" src="img/comptoire5.png"/></div>
</div>
</div>
</div>

</div><!-- millieu -->


<div class="droite">
<p class="msg" id="adversaire">...</p>
<table class="tab" id="tabTirs">
<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>

<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>


</table>

</div>

</div>
</body>
</html>