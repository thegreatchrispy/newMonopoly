function popup_details(boxID){
	var modal = document.getElementById('modalInfo');
	var captionText = document.getElementById("caption");
	var priceText = document.getElementById("priceID");
	var btn = document.getElementById(boxID);
	btn.onclick = function() {
		function loadJSON(callback) {
			var xobj = new XMLHttpRequest();
			xobj.overrideMimeType("application/json");
			xobj.open('GET', 'js/spaces.json', true);
			xobj.onreadystatechange = function () {
				if (xobj.readyState == 4 && xobj.status == "200") {
					callback(xobj.responseText);
				}
			};
			xobj.send(null); 
		}
		loadJSON(function(response) {
			var jsonContent = JSON.parse(response);
			var buildingText = "";

			modal.style.display = "block";
			for (var i = 0; i < jsonContent.length; i++){
				if(jsonContent[i].name == boxID){
					if(jsonContent[i].buildings > 4){
						buildingText = '1 HOTEL';
					} else if(jsonContent[i].buildings == 1) {
						buildingText = jsonContent[i].buildings + ' HOUSE';
					} else if(jsonContent[i].buildings == 0) {
						buildingText = 'NO HOUSES BUILT ON';
					} else {
						buildingText = jsonContent[i].buildings + ' HOUSES';
					}

					if(jsonContent[i].type != "utility"){
						captionText.innerHTML = '<p>'+jsonContent[i].name+'<br>'+
						'PRICE: $'+jsonContent[i].price+'<br>'+
						'RENT: $'+jsonContent[i].currentRent+'<br>'+
						'HOUSE COST: $'+jsonContent[i].houseCost+'<br>'+
						'OWNED BY: '+jsonContent[i].ownedBy+'<br>'+
						'BUILDINGS: '+buildingText+'<br>'+
						'MORTGAGED: '+jsonContent[i].mortgaged+'<br>'+
						'SEASON: '+''+
						'</p>';
					} else {
						captionText.innerHTML = '<p>'+jsonContent[i].name+'<br>'+
						'PRICE: $'+jsonContent[i].price+'<br>'+
						'OWNED BY: '+jsonContent[i].ownedBy+
						'</p>';
					}
				}
			}
		});
	}
	var closeMark = document.getElementById('close2');
	closeMark.onclick = function() {
		modal.style.display = "none";
	}
}

function popup_specialBoxes_details(boxID, name){
	var modal = document.getElementById('modalInfo');
	var captionText = document.getElementById("caption");
	var btn = document.getElementById(boxID);
	btn.onclick = function() {
	    modal.style.display = "block";
	    captionText.innerHTML = name;
	}
	var closeMark = document.getElementById('close2');
	closeMark.onclick = function() {
	    modal.style.display = "none";
	}
}