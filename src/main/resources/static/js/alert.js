function addAlert(message) {
	var currentAlert = $("#alert").html();
	var newAlert = currentAlert + "<br>" + message;
	$("#alert").html(newAlert);
}

function addAuctionAlert(message) {
	var currentAlert = $("#alert_auction").html();
	var newAlert = currentAlert + "<br>" + message;
	$("#alert_auction").html(newAlert);
}

function addBuildAlert(message) {
	var currentAlert = $("#alert_build").html();
	var newAlert = currentAlert + "<br>" + message;
	$("#alert_build").html(newAlert);
}

function incrementIndex() {
	index = (index + 1) % numPlayers;
}

function incrementAuctionIndex() {
	auction_index = (auction_index + 1) % numPlayers;
}