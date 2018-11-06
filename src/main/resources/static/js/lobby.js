function createGame() {
	var urlString = "http://localhost:8080/creategame?players=%5B";

	for (var i = 0; i < 6; i++) {
		var playerName = $("form[name='players']").find("input[name=player_"+(i+1)+"]").val();
		console.log("player " + (i+1) + ": " + playerName);
		if (playerName != '') {
			urlString += "%7B%22name%22%3A%22" + playerName + "%22%2C%22turnOrder%22%3A0%2C%22money%22%3A1500%2C%22currentPosition%22%3A0%2C%22doublesCount%22%3A0%2C%22jailCard%22%3Afalse%2C%22inJail%22%3Afalse%2C%22jailTime%22%3A0%2C%22ownedProperties%22%3A%5B%5D%2C%22monopolyGroup%22%3A%5B0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%5D%2C%22monopolyProperties%22%3A%5B%5D%2C%22tokenNumber%22%3A" + i + "%7D%2C";
		}
	}

	urlString = urlString.slice(0, -3);
	console.log("After truncation:");
	console.log(urlString);
	urlString += "%5D&randomize=true";
	
	return $.ajax({
		url: `${urlString}`,
		method: "GET",
		dataType: "json",
		contentType: "text/plain;charset=UTF-8",
		success: function(data) {
			console.log("Game id from ajax call: " + data);
			sessionStorage.setItem("currentBoardId", data);
			alert("game created");
			return data;
		}
	});
}

$(document).ready(function(){
	$('#submit').click(function(){
		if($('#myMessage').val() == ''){
		   alert('Input can not be left blank');
		}
	 });
	// Button Functions
	$('#launch').click(function() {
		var id = createGame().responseText;
		window.setTimeout(function() {
			// Move to a new location or you can do something else
			window.location.href = "http://localhost:8080/gameplay";
	
		}, 2000);
	});
});