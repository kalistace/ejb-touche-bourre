<%@ page language="java" contentType="text/html; charset=UTF-8"
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
	<script type="text/javascript" src="js/handleLeavePartie.js"></script>
	<link rel="stylesheet" type="text/css"  href="css/jquery-ui-1.8.16.custom.css"/>
	<link rel="stylesheet" type="text/css"  href="css/soiree.css"/>
	
	<script type="text/javascript">
	var nbrTables = 5;
	var pret = false;
	var tournee = 1;
	var bie = 5;           
	var myTables=new Array();
	var deuxiemeConnecte = 0;
	var refeshIntervalPretId;
	var refeshIntervalTour;
	var refreshIntervalDeco;
	var refreshIntervalId = setInterval( "deuxJoueursConnectes()", 3000 );
	

	
	
	$(document).ready(function() {
		
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
        			var params = "tables="+myTables.toString();
        			var req = new AjaxRequest("POST","pret.html",params, true);
        			req.handleResponse = function() {
        				rep=req.xhr.responseText;
        				if(rep == 1) {
        				  afficherBieres();
        				  $("#pret").fadeOut();
        				} else { 
            				$("#msgTop").fadeOut().fadeIn().text("En attente de l'autre f�tard!");
            				intervalPret();
            				$("#pret").fadeOut();
            			}
        			};
        			req.process();	

        		} 
        );
        
		$("#tabTirs td").bind("contextmenu", function(e) {
		    return false;
		});
		$("#tabTirs td").mousedown(function(event) {
			if(event.which==3){

				$(this).toggleClass("bleu");
			}
		});
		        

		var coord = new Array(); 
		    
        $("#tabTirs td").click(function(){
        		
        			var col = $(this).index();	
        			var row = $(this).parent().index();
        			
        			if(pret && $(this).text()==""){

        				bie--;
        				/*$("#bieres").children().last().fadeOut(function(){ 
        				$(this).remove()});*/
        				$("#bieres").children().last().remove();
        				
        				$(this).text(tournee);
        				coord.push(col+""+row);
        				if(bie == 0)
        				{
        					pret=false;
        					gererTournee(coord.toString());
        					coord = new Array();
        					tournee++;
        					
 
        					
        				}

        			
        			}
   	       
        	}
        );
	});
	
	function afficherBieres(nrBiere){

		var params = "";
		var req = new AjaxRequest("POST","casesTouchees.html",params, true);
		req.handleResponse = function() {
			rep=req.xhr.responseText;
			if(rep!=0){
			if($.trim(rep)=="")return;
			rep = $.trim(rep);
			var bieres = rep.split(",");		
			for ( b in bieres)
			{							
				//.append("<img alt='beer' src='img/beer.png' class='toucheBar'/>")
				 $("#tabPlacement").children().children().eq(bieres[b].charAt(1)).children().eq(bieres[b].charAt(0)).addClass("toucheBar");
			}
			}
		};
		req.process();
			
		$("#tablesAPlacer").append("<div id='bieres'></div>").hide().fadeIn("slow");
		$("#msgTop").text("Cliquez sur le bar de droite");
				
		for(i=0;i<nrBiere;i++){
			$("#bieres").append("<img alt='beer' src='img/beer.png'/>").fadeIn("slow");
		}
		$("#msgTopRes").fadeIn();
		$("#tablesTouchees").fadeIn();
	
	}
	
	function deuxJoueursConnectes(){
		var params = "tables=";
		var req = new AjaxRequest("POST","connecte.html",params, true);
		req.handleResponse = function() {
			rep=req.xhr.responseText;
			if(rep != 0) {
				$("#msgTop").fadeIn().text("Placez les tables dans le bar � gauche!");
				$("#tablesAPlacer").fadeIn();
				$("#adversaire").fadeIn().text(rep);
				deuxiemeConnecte=1;
			} 
		};
		req.process();	
		if(deuxiemeConnecte != 0){
			clearInterval(refreshIntervalId);
			refreshIntervalDeco = setInterval( "deco()", 3000 );
		} 
	}


	function intervalPret(){
		refeshIntervalPretId = setInterval(function (){
			var params = "tables=-1";
			var req = new AjaxRequest("POST","pret.html",params, true);
			req.handleResponse = function() {
				rep=req.xhr.responseText;
				if(rep == 1) {
				clearInterval(refeshIntervalPretId);
				courant();			
				} else { 
					$("#msgTop").fadeOut().fadeIn().text("En attente de l'autre f�tard!");
					
				}
			};
			req.process();	
		}, 3000);
	}

	function courant() {

		var params = "";
		var req = new AjaxRequest("POST","encours.html",params, true);
		req.handleResponse = function() {
			rep=req.xhr.responseText;
			if(rep > 0) {
				clearInterval(refeshIntervalTour);
				pret = true;
				bie = rep;
				afficherBieres(rep);
				$("#msgTop").fadeOut().fadeIn().text("Cliquez sur le bar de droite");
			} else if (rep == 0){ 
				$("#msgTop").fadeOut().fadeIn().text("Ce n'est pas votre tour!");
				clearInterval(refeshIntervalTour);
				refeshIntervalTour=setInterval("courant()",3000);		
			}else {
				window.onbeforeunload = function() {};
				alert("Partie Finie! "+$.trim(rep)+" a gagn�!");
				$("#msgTop").fadeOut();
				$("#btnAcc").fadeIn();
				clearInterval(refeshIntervalTour);
				clearInterval(refeshIntervalTour);
			}
		};
		req.process();	

	}

	function gererTournee(coord){
		var params = "coord="+coord;
		var req = new AjaxRequest("POST","tournee.html",params, true);
		req.handleResponse = function() {
			rep=req.xhr.responseText;
			tableServies(rep);
			courant();
		};
		req.process();	
	}
	
	function tableServies(rep){
		if($.trim(rep)=="")return;
		var table = rep.split(",");		
		for ( t in table)
		{
			var img = $(document.createElement("img")).attr({ src: 'img/servie.png'}).addClass("servie");
			var texte = $(document.createElement("span")).addClass("servieTexte").text(tournee-1);
			var tab =  $.trim(table[t]);
			var x = $("."+tab).children().length;
			for(var i=0;i<x;i++){
				if($("."+tab).children().eq(i).children().length == 1 ){
					$("."+tab).children().eq(i).append(img);
					$("."+tab).children().eq(i).append(texte);
					break;
				}
			}
		}
	}

	function deco() {
		var params = "";
		var req = new AjaxRequest("POST","deconnecte.html",params, true);
		req.handleResponse = function() {
			rep=req.xhr.responseText;
			if(rep==1){
				stopAllIntervals();
				window.onbeforeunload = function() {};
				alert("Adversaire d�connect�, partie termin�");
				$("#msgTop").fadeOut();
				$("#btnAcc").fadeIn();
			}else if(rep!=2){
				stopAllIntervals();
				window.onbeforeunload = function() {};
				alert("Adversaire d�connect�, vous avez gagn�!");
				$("#msgTop").fadeOut();
				$("#btnAcc").fadeIn();
				}
		};
		req.process();	
	}

	function stopAllIntervals(){
		clearInterval(refeshIntervalPretId);
		clearInterval(refeshIntervalTour);
		clearInterval(refreshIntervalDeco);
		clearInterval(refreshIntervalId);
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
<center><input id="pret" class = "btnSmall" type = "submit" value = "Pr�t" style="display:none" /></center>

</div>

<div class="milieu" style ="" >
<form action="accueil.html">
<p id="btnAcc" style="display:none;text-align:center;" ><input class = "btnSmall" type = "submit" value = "Accueil" /></p>
</form>
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

<p id="msgTopRes" style ="display:none;" class="msg">Tables servies:</p>
<div id="tablesTouchees" style ="display:none;" >
<!--  -->
<div style="width: 210px">
<div class="TableDeCouple" style="width: 90px">
	<div class="caseH"><img alt="case" src="img/couple1.png"/></div>
	<div class="caseH"><img alt="case" src="img/couple2.png"/></div>
</div>

<div class="TableDeFilles"  style="width: 130px">
	<div class="caseH"><img alt="case" src="img/filles1.png"/></div>
	<div class="caseH"><img alt="case" src="img/filles2.png"/></div>
	<div class="caseH"><img alt="case" src="img/filles3.png"/></div>
</div>

<div class="TableDeGarcons" style="width: 130px">
	<div class="caseH"><img alt="case" src="img/garcons1.png"/></div>
	<div class="caseH"><img alt="case" src="img/garcons2.png"/></div>
	<div class="caseH"><img alt="case" src="img/garcons3.png"/></div>
</div>

<div class="TableDePotes" style="width: 170px">
	<div class="caseH"><img alt="case" src="img/potes1.png"/></div>
	<div class="caseH"><img alt="case" src="img/potes2.png"/></div>
	<div class="caseH"><img alt="case" src="img/potes3.png"/></div>
	<div class="caseH"><img alt="case" src="img/potes4.png"/></div>
</div>
<div class="Comptoir" style="width: 210px">
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