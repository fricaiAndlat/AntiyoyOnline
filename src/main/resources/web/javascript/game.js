function init() {
	document.getElementById("state-noscript").style.display = "none";
	document.getElementById("state-userjoin").style.display = "block";
}

function userjoin_checkbox_toggled() {

	if (document.getElementById("userjoin-settings-checkbox").checked) {
		document.getElementById("userjoin-lobbysettings").style.display = "block";
	} else{
		document.getElementById("userjoin-lobbysettings").style.display = "none";
	}

}
