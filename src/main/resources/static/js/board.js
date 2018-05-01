function endTurn(id, playerName) {
    if (doublesRolled) {
        addAlert("It is " + names[index] + "'s turn again!");
        doublesRolled = false;
        $('#buildButton').hide();
        $('#tradeButton').hide();
        $('#mortgageButton').hide();
        $('#endButton').hide();
        $('#dieButton').show();
    }
    else {
        //checkIfGameHasWinner();
        incrementIndex();
        setDoubles(id, playerName);
        addAlert("It is " + names[index] + "'s turn!");
        $('#buildButton').hide();
        $('#tradeButton').hide();
        $('#mortgageButton').hide();
        $('#endButton').hide();
        $('#dieButton').show();
    }
}

function playerDecision(id, playerName) {
    $('#acceptButton').hide();
    $('#declineButton').hide();
    $('#continueButton').hide();
    addAlert("Your turn is about to come to an end, " + playerName + ".");
    addAlert("Please make a decision.");
    
    $('#buildButton').show();

    $('#tradeButton').show();

    $('#mortgageButton').show();

    $('#endButton').show();
    var end = document.getElementById("endButton");
    
    end.onclick = function() {
        endTurn(id, playerName);
    };
}

function updateMoney(id, playerName) {
    var money = getMoney(id, playerName).responseText;
    var moneyPosition = -1;
    for(i = 0; i < numPlayers; i++) {
        moneyPosition++;
        if (names[moneyPosition] == playerName)
            break;
    }
    moneyPosition++;
    var moneyID = "#money-" + moneyPosition;
    $(moneyID).html("$" + money);
}

function chooseToBuy(id, playerName) {
    askToBuy(id, playerName);

    $('#acceptButton').show();
    var accept = document.getElementById("acceptButton");
    accept.onclick = function() {
        acceptPurchase(id, playerName);
        setTimeout(function() {
            updateMoney(id, playerName);
            playerDecision(id, playerName);
        }, 500);
    };

    // Auction Function.
    $('#declineButton').show();
    $('#declineButton').onclick = function() {declinePurchase(id, playerName)};

}

function payProperty(id, playerName, ownerName) {
    $('#continueButton').show();
    var continueButton = document.getElementById("continueButton");

    payRent(id, playerName, ownerName);
    setTimeout(function() {
        updateMoney(id, playerName);
    }, 500);
    setTimeout(function() {
        updateMoney(id, ownerName);
    }, 1000);

    continueButton.onclick = function() {
        playerDecision(id, playerName);
    }
}

function movingToJail(id, playerName) {
    movePlayerToJail(id, playerName);
    playersPosition[index] = 10;
    move(pieces[index], 11, 1, true);
    doublesRolled = false;
    endTurn(id, playerName);
}

function checkSpaceActionAndType(id, playerName) {
    $('#dieButton').hide();
   var string = getSpaceActionAndType(id, playerName).responseText.split(" ");
   switch (string[0]) {
        case "movingToJail":
            movingToJail(id, playerName);
            break;
        case "pay":
            switch (string[1]) {
                case "property":
                    payProperty(id, playerName, string[2]);
                    break;
                case "railroad":
                    console.log(string[1]);
                    break;
                case "utility":
                    console.log(string[1]);
                    break;
            }
            break;
        case "mortgaged":
            console.log(string[0]);
            break;
        case "nothing":
            playerDecision(id, playerName);
            break;
        case "chooseToBuy":
            chooseToBuy(id, playerName);
            break;
        case "draw":
            console.log(string[0]);
            break;
   }
}