$(document).ready(function(){
	// Button Functions
	$('#launch').click(function(){
		var player_1 = $("form[name = 'players']").find('input[name=player_1]').val();
		var player_2 = $("form[name = 'players']").find('input[name=player_2]').val();
		var urlString = "http://localhost:8080/creategame?players=%5B%7B%22name%22%3A%22" + player_1 + "%22%2C%22turnOrder%22%3A0%2C%22money%22%3A1500%2C%22currentPosition%22%3A0%2C%22doublesCount%22%3A0%2C%22jailCard%22%3Afalse%2C%22inJail%22%3Afalse%2C%22jailTime%22%3A0%2C%22ownedProperties%22%3A%5B%5D%2C%22monopolyGroup%22%3A%5B0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%5D%2C%22monopolyProperties%22%3A%5B%5D%2C%22tokenNumber%22%3A0%7D%2C%7B%22name%22%3A%22" + player_2 + "%22%2C%22turnOrder%22%3A0%2C%22money%22%3A1500%2C%22currentPosition%22%3A0%2C%22doublesCount%22%3A0%2C%22jailCard%22%3Afalse%2C%22inJail%22%3Afalse%2C%22jailTime%22%3A0%2C%22ownedProperties%22%3A%5B%5D%2C%22monopolyGroup%22%3A%5B0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%5D%2C%22monopolyProperties%22%3A%5B%5D%2C%22tokenNumber%22%3A1%7D%5D&randomize=false";
		
		$.ajax({
			url: `${urlString}`,
			method: "GET",
			dataType: "json",
			contentType: "text/plain;charset=UTF-8",
			success: function() {
				alert("game created");
			}
		});

		window.setTimeout(function(){
			// Move to a new location or you can do something else
			window.location.href = "http://localhost:8080/gameplay";
	
		}, 2000);
	});
});