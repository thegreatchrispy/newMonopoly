function popup_bars(playerboxID, condition){
	var playerBox = document.getElementById(playerboxID);

	if(condition=='yei'){
		playerBox.style.visibility = "visible";
	}
	if(condition=='nei'){
		playerBox.style.visibility = "hidden";
	}
}