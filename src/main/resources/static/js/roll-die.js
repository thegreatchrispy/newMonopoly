$('#dieButton').addEventListener("click", function() {
	rollDie();
	// hides roll dice button;
	// move player
	// pull from database
});

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