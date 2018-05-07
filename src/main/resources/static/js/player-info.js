$(document).ready(function() {
	document.getElementById('FirstBtn').onclick = function(){
		var playerInfo = playerData(1,names[0]).responseText.split(";");
		player_info(playerInfo,'0,0,0');
	};
	document.getElementById('SecondBtn').onclick = function(){
		var playerInfo = playerData(1,names[1]).responseText.split(";");
		player_info(playerInfo,'0,0,255');
	};
	document.getElementById('ThirdBtn').onclick = function(){
		var playerInfo = playerData(1,names[2]).responseText.split(";");
		player_info(playerInfo,'255,0,0');
	};
	document.getElementById('FourthBtn').onclick = function(){
		var playerInfo = playerData(1,names[3]).responseText.split(";");
		player_info(playerInfo,'0,128,0');
	};
	document.getElementById('FifthBtn').onclick = function(){
		var playerInfo = playerData(1,names[4]).responseText.split(";");
		player_info(playerInfo,'255,165,0');
	};
	document.getElementById('SixthBtn').onclick = function(){
		var playerInfo = playerData(1,names[5]).responseText.split(";");
		player_info(playerInfo,'221,160,221');
	};


	function player_info(playerdata, color){
		var modal = document.getElementById('myModal');
		var captionText = document.getElementById("PlayerInfo");

		modal.style.background = "rgba("+color+",0.9)";
		modal.style.display = "block";

		var outofjailText = "";
		if(playerdata[3]=="false"){
			outofjailText = "You don't have this card.";
		} else {
			outofjailText = "You got one card."
		}

		var groups = playerdata[4].slice(1,23);
		groups = groups.replace(/\s+/g, '');
		var groupsArray = groups.split(',');
		var groupStorage = "";
		var i;
		for(i=0; i<8 ; i++){
			if(groupsArray[i]=="1"){
				groupStorage += (i+1)+" ";
			}
		}

		captionText.innerHTML = '<p>'+playerdata[0]+'<br>'+
							'Money: $'+playerdata[1]+'<br>'+
							'<br>'+
							'Properties: '+playerdata[2]+'<br>'+
							'<br>'+
							'Jail Card: '+outofjailText+'<br>'+
							'Monopoly Groups Owned: '+groupStorage+
							'</p>';
		var closeMark = document.getElementById('close1');
		closeMark.onclick = function() {
		    modal.style.display = "none";
		}
	}
});