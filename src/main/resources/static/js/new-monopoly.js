var numPlayers = 0;
var names = [];
var row = [11,11,11,11,11,11,11,11,11,11,11,
		   10, 9, 8, 7, 6, 5, 4, 3, 2, 1,
		    1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
		    2, 3, 4, 5, 6, 7, 8, 9, 10];
var column = [11,10, 9, 8, 7, 6, 5, 4, 3, 2, 1,
		       1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
		       2, 3, 4, 5, 6, 7, 8, 9, 10,11,
		       11,11,11,11,11,11,11,11,11];
var playersPosition = [];
var pieces = ["piece_1"];

function getNumberOfPlayers() {
	return $.ajax({
    	url:"http://localhost:8080/getnumberofplayers?gameid=1",
    	async: false,
    	success:function(data) {
    		return data;
		}
	});
}

function getNamesOfPlayers() {
	return $.ajax({
    	url:"http://localhost:8080/getnamesofplayers?gameid=1",
    	async: false,
    	success:function(data) {
    		return data;
		}
	});
}

function movePlayer(id, name, value) {
	var urlString = "http://localhost:8080/moveplayer?gameid=" + id + "&player=" + name + "&value=" + value;

	$.ajax({
			url: `${urlString}`,
			method: "POST",
			dataType: "json",
			contentType: "text/plain;charset=UTF-8",
			success: function() {
				alert("account created");
			}
		});
}

function rollDie(){
	var button = document.getElementById('dieButton');
	var content1 = document.getElementById('dice1');
	var content2 = document.getElementById('dice2');

	var dice = {
	  sides: 6,
	  roll: function () {
	    var randomNumber = Math.floor(Math.random() * this.sides) + 1;
	    return randomNumber;
	  }
	}

	var result1 = dice.roll();
	var result2 = dice.roll();
	content1.innerHTML = result1;
	content2.innerHTML = result2;
}

function setup() {
	
	names = getNamesOfPlayers().responseText.split(" ");

	numPlayers = getNumberOfPlayers().responseText;

	var $playerBlocks = $('#player1, #player2, #player3, #player4, #player5, #player6');
	var $playerTokens = $('#piece_1, #piece_2, #piece_3, #piece_4, #piece_5, #piece_6');
	var $playerNames = $('#player1-name, #player2-name, #player3-name, #player4-name, #player5-name, #player6-name');

    // var div_count = 0;
    // var $allButtons = $('#back, #page_back, #forward');
    // console.log($allDivs.length);

    for(i = 0; i < numPlayers; i++) {
		$playerBlocks.eq( i ).show();
		$playerTokens.eq( i ).show();
		$playerNames.eq( i ).html(names[i]);
		playersPosition[i] = 0;
	}
}


window.onload = function() {
	setup();

	var index = 0;
	var $playerBlocks = $('#player1, #player2, #player3, #player4, #player5, #player6');
	var $playerTokens = $('#piece_1, #piece_2, #piece_3, #piece_4, #piece_5, #piece_6');
	var $playerNames = $('#player1-name, #player2-name, #player3-name, #player4-name, #player5-name, #player6-name');
	 
	$("#alert").html("It is " + names[index] + "'s turn!");
	
	$('#dieButton').click(function() {
		rollDie();
		var die_1 = $('#dice1').html();
		var die_2 = $('#dice2').html();
		var total = Number(die_1) + Number(die_2);
		var value = total;
		$("#alert").html(names[index] + " rolled " + total + ".");
		// moveplayer
		total = (playersPosition[index] + total) % 40;
		playersPosition[index] = total;
		move(pieces[index], row[total], column[total], false);
		movePlayer(1, names[index], value);
	});
}