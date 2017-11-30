"use strict";

function init() {
	document.getElementById("state-noscript").style.display = "none";
	document.getElementById("state-userjoin").style.display = "block";
	
	CONTROL.state = 1;
}
function error(err) {
	document.getElementById("userjoin-alert").style.display = "block";
	document.getElementById("userjoin-alert").innerHTML = "Error in socket: " + err;
}

function userjoin_checkbox_toggled() {

	if (document.getElementById("userjoin-settings-checkbox").checked) {
		document.getElementById("userjoin-lobbysettings").style.display = "block";
	} else{
		document.getElementById("userjoin-lobbysettings").style.display = "none";
	}
}


/* gamecode */
var CONTROL = new Object();
CONTROL.state = 0; /* 0=preinit, 1=lobbyjoin, 2=lobby, 3=game-select, 4=game*/
CONTROL.socket = new WebSocket('ws://localhost:9985/');
CONTROL.socket.onopen = function () {
	document.getElementById("state-noscript").style.display = "none";
	document.getElementById("state-userjoin").style.display = "block";
};
CONTROL.socket.onerror = function (err) {
	console.log('WebSocket Error ' + err);
	error("Error in Websocket. Check the console for detail");
};
CONTROL.socket.onmessage = function (e) {
	PLAYER.receive(JSON.parse(e.data));
};
CONTROL.send = function(msg) {
	CONTROL.socket.send(msg);
}

var PLAYER = {
	name: "undef",
	id: "undef"
};

PLAYER.send = function(obj) {
	CONTROL.socket.send(JSON.stringify(obj));
}
PLAYER.receive = function() {
	
}


function join_lobby() {
	var username = document.getElementById("userjoin-username").value;
	
	var json_playerjoin = new Object();
	json_playerjoin.jsonType = "player_join";
	json_playerjoin.name = username;
	
	PLAYER.receive = join_lobby_step2;
	PLAYER.send(json_playerjoin);
}

function join_lobby_step2(json_result){
	if (json_result.jsonType != "") {
		error("cant handle json type " + json_result.jsonType + "in join_lobby_step2");
		return;
	}
	
	PLAYER.name = json_result.name;
	PLAYER.id = json_result.id;
	
	var lobbyname = document.getElementById("userjoin-username").value;
	var use_password = document.getElementbyId("userjoin-password-need").checked;
	var password = document.getElementById("userjoin-password").value;
	
	var json_lobbyjoin = new Object();
	json_lobbyjoin.lobbyname = lobbyname;
	
	if (use_password) {
		json_
	}
}


/* utility */

var UTIL = new Object();
UTIL.sha512 = function(str) {
	var buffer = new TextEncoder("utf-8").encode(str);
	
	var result = new Object();
	result.then = function(str) {};
	
	crypto.subtle.digest("SHA-512", buffer).then(function(digest) {
			result.then(UTIL.hex(digest));
	});
	return result;
};
UTIL.hex = function (buffer) {
	var hexCodes = [];
	var view = new DataView(buffer);
	for (var i = 0; i < view.byteLength; i += 4) {
		// Using getUint32 reduces the number of iterations needed (we process 4 bytes each time)
		var value = view.getUint32(i)
		// toString(16) will give the hex representation of the number without padding
		var stringValue = value.toString(16)
		// We use concatenation and slice for padding
		var padding = '00000000'
		var paddedValue = (padding + stringValue).slice(-padding.length)
		hexCodes.push(paddedValue);
	}
	
	// Join all the hex strings into one
	return hexCodes.join("");
}
UTIL.checkCrypto = function(success, fail) {
	var target = "0a50261ebd1a390fed2bf326f2673c145582a6342d523204973d0219337f81616a8069b012587cf5635f6925f1b56c360230c19b273500ee013e030601bf2425"
				
	UTIL.sha512("foobar").then = function(digest) {
		if(target==digest) {
			success();
		}
	}
}
