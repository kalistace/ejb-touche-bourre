/**
 * 
 */

window.onbeforeunload = function() {
	if (confirm("Vous �tes sur le point d'abandonner cette partie.\n\n" +
			"Si celle-ci a d�j� d�but�, elle sera compt�e comme d�faite. \n\nVoulez-vous continuer ?")) {
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