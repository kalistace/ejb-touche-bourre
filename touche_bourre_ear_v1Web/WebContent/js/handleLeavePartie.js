/**
 * 
 */

window.onbeforeunload = function() {
	if (confirm("Vous êtes sur le point d'abandonner cette partie.\n\n" +
			"Si celle-ci a déjà débuté, elle sera comptée comme défaite. \n\nVoulez-vous continuer ?")) {
		var params = "tables=";
		var req = new AjaxRequest("POST","handleleavepartie.html",params, true);
		req.handleResponse = function() {
			rep=req.xhr.responseText;
		};
		req.process();	
	} else {
		return false;
	}
};