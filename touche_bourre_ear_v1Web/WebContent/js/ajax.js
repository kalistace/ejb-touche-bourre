/**
 * @author Michel
 */
function AjaxRequest(method, url, params, async, type){
    this.getMethod = function(){
        return method;
    }
    this.getUrl = function(){
        return url;
    }
    this.getParams = function(){
        return params;
    }
    this.isAsync = function(){
        return async;
    }
	this.getType = function(){
		return type;
	}
    this.xhr = this.createXHR();
}

AjaxRequest.prototype.createXHR = function(){
    var xlmHttp = null;
    try {//test pour les navigateurs : Mozilla, Opera, ...
        xmlHttp = new XMLHttpRequest();
    } 
    catch (error) {
        try {//test pour les navigateurs Internet Explorer > 5.0
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        } 
        catch (error) {
            try {//test pour le navigateur Internet Explorer 5.0
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            } 
            catch (error) {
                xmlHttp = null;
            }
        }
    }
    return xmlHttp;
}
AjaxRequest.prototype.process = function(){
    if (this.xhr) {
    	try {
            if (this.getMethod() == "GET" || this.getMethod() == "get") {
                this.xhr.open(this.getMethod(), this.getUrl() + (this.getParams() == "" ? "" : "?" + this.getParams()), this.isAsync());
            }
            else {
                this.xhr.open(this.getMethod(), this.getUrl(), this.isAsync());
            }
            var that = this;
            this.xhr.onreadystatechange = function(){
                if (that.xhr.readyState == 4) {
					try {
						if (that.xhr.status == 200) {
							try {
								that.handleResponse();
							} 
							catch (e) {
								that.displayError("Probleme de traitement de la reponse : " + e.toString());
							}
						}
						else {
							that.displayError("Probleme de reception de la reponse :\n" + that.xhr.status + " - " + that.xhr.statusText);
						}
					} catch (e) {
						// ignore : application fermee
					}
                }
            }
            if (this.getMethod() == "GET" || this.getMethod() == "get") {
                this.xhr.send(null);
            }
            else {
				if (this.getType() == "XML" || this.getType() == "xml")
               		this.xhr.setRequestHeader("Content-Type", "text/xml");
				else
                	this.xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				this.xhr.send(this.getParams());
            }
        } 
        catch (error) {
            this.displayError("Probleme de connexion au serveur :\n" + error.toString());
        }
    }
}

AjaxRequest.prototype.displayError = function(message) {
	alert(message);
}

AjaxRequest.prototype.handleResponse = function(){
}
