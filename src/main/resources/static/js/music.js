$('#musicButton').on("click", function() {
	toggleMusic();
});

function toggleMusic() {
	var bool = $("#music").prop("muted");
    $("#music").prop("muted",!bool);
}