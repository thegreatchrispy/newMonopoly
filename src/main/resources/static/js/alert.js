function addAlert(message) {
	var currentAlert = $("#alert").html();
	var newAlert = currentAlert + "<br>" + message;
	$("#alert").html(newAlert);
}

function incrementIndex() {
	index = (index + 1) % numPlayers;
}