function toggleMusic() {
	var bool = $("#music").prop("muted");
	$("#music").prop("muted",!bool);
	
	if (bool) {
		$("#musicButton").text("Mute");
	}
	else {
		$("#musicButton").text("Unmute");
	}
}