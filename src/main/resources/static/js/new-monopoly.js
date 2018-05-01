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

function startTurn() {
	rollDie();
	var die_1 = $('#dice1').html();
	var die_2 = $('#dice2').html();
	var total = Number(die_1) + Number(die_2);
	if (Number(die_1) == Number(die_2)) {
		incrementDoublesCount(1, names[index]);
		doublesRolled = true;
	}
	var value = total;
	addAlert(names[index] + " rolled " + total + ".");

	setTimeout(function() {
		// Move Player
		total = (playersPosition[index] + total) % 40;
		playersPosition[index] = total;
		move(pieces[index], row[total], column[total], false);
		movePlayer(1, names[index], value);
		setTimeout(function() {
			// Check Space Action and Type
			checkSpaceActionAndType(1, names[index]);
		}, 500);
	}, 500);
}


window.onload = function() {
	setup();

	var $playerBlocks = $('#player1, #player2, #player3, #player4, #player5, #player6');
	var $playerTokens = $('#piece_1, #piece_2, #piece_3, #piece_4, #piece_5, #piece_6');
	var $playerNames = $('#player1-name, #player2-name, #player3-name, #player4-name, #player5-name, #player6-name');
	 
	// Start game.
	addAlert("It is " + names[index] + "'s turn!");
	// First Die Button Appears.
	$('#dieButton').show();
	$('#dieButton').click(function() {
		startTurn();
	});
}