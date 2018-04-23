function player_info(btnID, message, color){
	var modal = document.getElementById('myModal');
	var captionText = document.getElementById("PlayerInfo");
	var btn = document.getElementById(btnID);
	btn.onclick = function() {
		modal.style.background = "rgba("+color+",0.9)";
	    modal.style.display = "block";
	    captionText.innerHTML = message;
	}
	var closeMark = document.getElementById('close1');
	closeMark.onclick = function() {
	    modal.style.display = "none";
	}
}