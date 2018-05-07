function setup() {
	
	names = getNamesOfPlayers().responseText.split(" ");

	numPlayers = getNumberOfPlayers().responseText;

	var status = isRandomized().responseText;
	console.log(status);

	var string = getSwappedString().responseText.split(";");

	if (status == "true") {
		for(j = 0; j < 4; j++) {
			var group_pairs = string[j].split(" ");
			var group1 = "group_" + group_pairs[0];
			var group2 = "group_" + group_pairs[1];
			if (!(Number(group_pairs[0]) > 4 && Number(group_pairs[1]) > 4)) {
				if (!(Number(group_pairs[0]) <= 4 && Number(group_pairs[1]) <= 4)) {
					reverse(group_pairs[0]);
					reverse(group_pairs[1]);
				}
			}
			swap(group1, group2);
		}
	}

    for(i = 0; i < numPlayers; i++) {
		$(playerBlocks[i]).show();
		$(playerTokens[i]).show();
		$(playerNames[i]).html(names[i]);
	}
}

function rollDie(){
	console.log("Rolling Dice.");
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
	$('#dieButton').hide();
}

function startTurn() {
	console.log("Starting Turn.");
	var playerName = names[index];
	var id = 1;
	console.log("Current Position: " + getPosition(id, playerName).responseText);
	var string = getInJail(1, playerName).responseText.split(";");

	if (string[0] == "true") {
		addAlert(playerName + " is still locked up!");
		inJail(id, playerName, string);
	} else {
		executeTurn(id, playerName);
	}
	
}

function inJail(id, playerName, string) {
	console.log("inJail Function.");
	$('#dieButton').hide();

	var useCard = document.getElementById("jailCardButton");
	useCard.onclick = function() {
		getOutOfJailFree(id, playerName);
		setTimeout(function() {
			$('#jailCardButton').hide();
			$('#jailRollButton').hide();
			$('#jailPayButton').hide();
			executeTurn(id, playerName);
			}, 500);
	};
	var testLuck = document.getElementById("jailRollButton");

	var pay = document.getElementById("jailPayButton");
	pay.onclick = function() {
		getOutOfJail(id, playerName);
		setTimeout(function() {
			$('#jailCardButton').hide();
			$('#jailRollButton').hide();
			$('#jailPayButton').hide();
			var lockup = 'in-jail' + (index + 1);
    		popup_bars(lockup,'nei');
			executeTurnWithoutDoubles(id, playerName);
		}, 500);
	}
	
	// Player has more than one turn in jail.
	if (string[2] != "1") {
		// Player has Jail Card
		if (string[1] == "true"){
			addAlert(playerName + ", please know that you do have a Get Out Of Jail Free Card.");
			$('#jailCardButton').show();
		}
		testLuck.onclick = function() {
			rollDie();
			var die_1 = $('#dice1').html();
			var die_2 = $('#dice2').html();
			var value = Number(die_1) + Number(die_2);
			if (Number(die_1) == Number(die_2)) {
				addAlert(playerName + " rolled doubles!");
				doublesRolled = true;
				getOutOfJailFree(id, playerName);
				setTimeout(function() {
					$('#jailCardButton').hide();
					$('#jailRollButton').hide();
					$('#jailPayButton').hide();
					var lockup = 'in-jail' + (index + 1);
    				popup_bars(lockup,'nei');
					executeTurn(id, playerName);
				}, 500);
			} else {
				addAlert(playerName + " did not roll doubles.");
				$('#jailCardButton').hide();
				$('#jailRollButton').hide();
				$('#jailPayButton').hide();
				decrementJailTime(id, playerName);
				waitContinue(id, playerName);
			}
		}
		$('#jailRollButton').show();
		$('#jailPayButton').show();
	}
	// Player has one turn left in jail.
	if (string[2] == "1") {
		// Player has Jail Card
		if (string[1] == "true"){
			addAlert(playerName + ", please know that you do have a Get Out Of Jail Free Card.");
			$('#jailCardButton').show();
		}
		testLuck.onclick = function() {
			rollDie();
			var die_1 = $('#dice1').html();
			var die_2 = $('#dice2').html();
			var value = Number(die_1) + Number(die_2);
			if (Number(die_1) == Number(die_2)) {
				addAlert(playerName + " rolled doubles!");
				doublesRolled = true;
				getOutOfJailFree(id, playerName);
				setTimeout(function() {
					$('#jailCardButton').hide();
					$('#jailRollButton').hide();
					$('#jailPayButton').hide();
					var lockup = 'in-jail' + (index + 1);
    				popup_bars(lockup,'nei');
					executeTurn(id, playerName);
				}, 500);
			} else {
				addAlert(playerName + " did not roll doubles.");
				getOutOfJail(id, playerName);
				setTimeout(function() {
					$('#jailCardButton').hide();
					$('#jailRollButton').hide();
					$('#jailPayButton').hide();
					var lockup = 'in-jail' + (index + 1);
    				popup_bars(lockup,'nei');
					executeTurnWithoutDoubles(id, playerName);
				}, 500);
			}
		}
		$('#jailRollButton').show();
		$('#jailPayButton').show();
	}
}

function executeTurn(id, playerName) {
	console.log("Executing a free man's turn.");
	rollDie();
	var die_1 = $('#dice1').html();
	var die_2 = $('#dice2').html();
	var value = Number(die_1) + Number(die_2);
	// var value = 39; // for testing
	if (Number(die_1) == Number(die_2)) {
		incrementDoublesCount(id, playerName);
		doublesRolled = true;
	}
	var doublesCount = getDoublesCount(id, playerName).responseText;
	if (doublesCount == 3) {
		setInJail(id, playerName);
		movePlayerToJail(id, playerName);
	} else {
		addAlert(playerName + " rolled " + value + ".");
		setTimeout(function() {
			// Move Player
			movePlayer(id, playerName, value);
			setTimeout(function() {
				var position = getPosition(id, playerName).responseText;
				move(pieces[index], row[position], column[position]);
				console.log("Current Position: " + position);
				updateMoneyAfterMove(id, playerName); // In case the player passes GO.
				spaceAction(id, playerName);
			}, 500);
		}, 500);
	}
}

function executeTurnWithoutDoubles(id, playerName) {
	console.log("Executing a FeelsBadMan's turn.");
	rollDie();
	var die_1 = $('#dice1').html();
	var die_2 = $('#dice2').html();
	var value = Number(die_1) + Number(die_2);
	addAlert(playerName + " rolled " + value + ".");

	setTimeout(function() {
		// Move Player
		movePlayer(id, playerName, value);
		setTimeout(function() {
			var position = getPosition(id, playerName).responseText;
			move(pieces[index], row[position], column[position]);
			console.log("Current Position: " + position);
			updateMoneyAfterMove(id, playerName); // In case the player passes GO.
			spaceAction(1, playerName);
		}, 500);
	}, 500);
}

window.onload = function() {
	setup();
	// Start game.
	addAlert("It is " + names[index] + "'s turn!");
	// First Die Button Appears.
	$('#dieButton').show();
	$('#dieButton').click(function() {
		startTurn();
	});
}