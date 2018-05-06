$('#musicButton').addEventListener("click", function() {
	if (this.paused == false) {
		this.pause();
		alert('music paused');
	} else {
		this.play();
		alert('music playing');
	}
});