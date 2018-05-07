$(document).ready(function() {
	document.getElementById('Baltic Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Baltic Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Mediterranean Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Mediterranean Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Reading Railroad').onclick = function(){
		var cardInfo = cardsData(1,'Reading Railroad').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Connecticut Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Connecticut Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Vermont Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Vermont Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Oriental Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Oriental Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Virginia Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Virginia Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('States Avenue').onclick = function(){
		var cardInfo = cardsData(1,'States Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Electric Company').onclick = function(){
		var cardInfo = cardsData(1,'Electric Company').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('St. Charles Place').onclick = function(){
		var cardInfo = cardsData(1,'St. Charles Place').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Pennsylvania Railroad').onclick = function(){
		var cardInfo = cardsData(1,'Pennsylvania Railroad').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('New York Avenue').onclick = function(){
		var cardInfo = cardsData(1,'New York Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Tennessee Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Tennessee Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('St. James Place').onclick = function(){
		var cardInfo = cardsData(1,'St. James Place').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Kentucky Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Kentucky Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Indiana Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Indiana Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Illinois Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Illinois Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('B.O. Railroad').onclick = function(){
		var cardInfo = cardsData(1,'B.O. Railroad').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Atlantic Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Atlantic Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Ventnor Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Ventnor Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Water Works').onclick = function(){
		var cardInfo = cardsData(1,'Water Works').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Marvin Gardens').onclick = function(){
		var cardInfo = cardsData(1,'Marvin Gardens').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Pacific Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Pacific Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('North Carolina Avenue').onclick = function(){
		var cardInfo = cardsData(1,'North Carolina Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Pennsylvania Avenue').onclick = function(){
		var cardInfo = cardsData(1,'Pennsylvania Avenue').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Short Line Railroad').onclick = function(){
		var cardInfo = cardsData(1,'Short Line Railroad').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Park Place').onclick = function(){
		var cardInfo = cardsData(1,'Park Place').responseText.split(";");
		popup_details(cardInfo);
	};
	document.getElementById('Boardwalk').onclick = function(){
		var cardInfo = cardsData(1,'Boardwalk').responseText.split(";");
		popup_details(cardInfo);
	};
	function popup_details(data){
		var modal = document.getElementById('modalInfo');
		var captionText = document.getElementById("caption");

		var buildingText = "";

		modal.style.display = "block";

		if(data[7] > 4){
			buildingText = '1 HOTEL';
		} else if(data[7] == 1) {
			buildingText = data[7] + ' HOUSE';
		} else if(data[7] == 0) {
			buildingText = 'NO HOUSES BUILT ON';
		} else {
			buildingText = data[7] + ' HOUSES';
		}

		var owner = "";
		if(data[6]=="-1"){
			owner = "Unowned";
		} else {
			owner = names[data[6]];
		}

		var mortgageText = "";
		if(data[8]=="false"){
			mortgageText = "no";
		} else {
			mortgageText = "yes";
		}

		var strongName = seasonName(data[9]);
		var weakName = seasonName(data[10]);

		if(data[11] == "property"){
			captionText.innerHTML = '<p>'+data[0]+'<br>'+
			'Group: '+data[1]+'<br>'+
			'PRICE: $'+data[2]+'<br>'+
			'RENT: $'+data[3]+'<br>'+
			'Multiplied RENT: $'+data[4]+'<br>'+
			'HOUSE COST: $'+data[5]+'<br>'+
			'OWNED BY: '+owner+'<br>'+
			'BUILDINGS: '+buildingText+'<br>'+
			'MORTGAGED: '+mortgageText+'<br>'+
			'Strong Season: '+strongName+'<br>'+
			'Weak Season: '+weakName+'<br>'+
			'</p>';
		}
		if(data[11] == "railroad"){
			captionText.innerHTML = '<p>'+data[0]+'<br>'+
			'PRICE: $'+data[2]+'<br>'+
			'RENT: $25 <br>'+
			'Multiplied RENT: $'+data[4]+'<br>'+
			'OWNED BY: '+owner+'<br>'+
			'MORTGAGED: '+mortgageText+'<br>'+
			'</p>';
		}
		if(data[11] == "utility"){

			captionText.innerHTML = '<p>'+data[0]+'<br>'+
			'PRICE: $'+data[2]+'<br>'+
			'OWNED BY: '+owner+'<br>'+
			'MORTGAGED: '+mortgageText+'<br>'+
			'</p>';
		}

		var closeMark = document.getElementById('close2');
		closeMark.onclick = function() {
			modal.style.display = "none";
		}
	}

	function seasonName(seasonid) {
		var newname = "";
		switch (seasonid) {
			case "0":
				newname = "Spring";
				break;
			case "1":
				newname = "Summer";
				break;
			case "2":
				newname = "Fall";
				break;
			case "3":
				newname = "Winter";
				break;
		}
		return newname;
	}
});

