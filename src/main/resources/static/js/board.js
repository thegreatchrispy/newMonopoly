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
        $("#alert").html("");
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

function waitContinue(id, playerName) {
    $('#continueButton').show();
    var continueButton = document.getElementById("continueButton");
    continueButton.onclick = function() {
        playerDecision(id, playerName);
    }
}

function updateMoney(id, playerName) {
    var name = "";
    for (i = 0; i < numPlayers; i++) {
        name = names[i];
        var money = getMoney(id, name).responseText;
        var moneyPosition = -1;
        for(i = 0; i < numPlayers; i++) {
            moneyPosition++;
            if (names[moneyPosition] == name)
                break;
        }
        moneyPosition++;
        var moneyID = "#money-" + moneyPosition;
        $(moneyID).html("$" + money);
    }
    waitContinue(id, playerName);
}

function updateMoneyAfterMove(id, playerName) {
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

    $('#acceptButton').show();
    var accept = document.getElementById("acceptButton");
    accept.onclick = function() {
        acceptPurchase(id, playerName);
        setTimeout(function() {
            $('#acceptButton').hide();
            $('#declineButton').hide();
            updateMoney(id, playerName);
        }, 500);
    };

    // Auction Function.
    $('#declineButton').show();
    $('#declineButton').onclick = function() {declinePurchase(id, playerName)};

}

function movePlayerToJail(id, playerName) {
    playersPosition[index] = 10;
    move(pieces[index], 11, 1, true);
    doublesRolled = false;
    endTurn(id, playerName);
}

function movePlayerToNewSpace(id, playerName) {
    //movePlayerToJail(id, playerName);
    var newPosition = getPosition(id, playerName).responseText;
    playersPosition[index] = newPosition;
    move(pieces[index], row[newPosition], column[newPosition], false);
    updateMoneyAfterMove(id, playerName);
    spaceAction(id, playerName);
}

function spaceAction(id, playerName) {
    $('#dieButton').hide();
   var string = performSpaceAction(id, playerName).responseText.split(";");
   switch (string[string.length - 1]) {
        case "movingToJail":
            movePlayerToJail(id, playerName);
            break;
        case "moneyChange":
            updateMoney(id, playerName);
            break;
        case "mortgaged":
            console.log(string[0]);
            break;
        case "nothing":
            updateMoney(id, playerName);
            break;
        case "chooseToBuy":
            chooseToBuy(id, playerName);
            break;
        case "move":
            movePlayerToNewSpace(id, playerName);
            break;
   }
}