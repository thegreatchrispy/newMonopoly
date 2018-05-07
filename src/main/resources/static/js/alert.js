function addAlert(message) {
	var currentAlert = $("#alert").html();
	var newAlert = currentAlert + "<br>" + message;
	$("#alert").html(newAlert);
	$("#header ul").append('<li><a href="/user/messages"><span class="tab">Message Center</span></a></li>');
	$("#log_entry_list").append('<li>' + newAlert + '</li>');
}

function addAuctionAlert(message) {
	var currentAlert = $("#alert_auction").html();
	var newAlert = currentAlert + "<br>" + message;
	$("#alert_auction").html(newAlert);
	$("#log_entry_list").append('<li>' + newAlert + '</li>');
}

function addBuildAlert(message) {
	var currentAlert = $("#alert_build").html();
	var newAlert = currentAlert + "<br>" + message;
	$("#alert_build").html(newAlert);
	$("#log_entry_list").append('<li>' + newAlert + '</li>');
}

function incrementIndex() {
	index = (index + 1) % numPlayers;
}

function incrementAuctionIndex() {
	auction_index = (auction_index + 1) % numPlayers;
}
