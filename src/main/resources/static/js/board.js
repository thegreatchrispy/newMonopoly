function endTurn(id, playerName) {
    console.log("Ending players turn.");  
    console.log("");
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
    console.log("Player's Turn is about to end..");  
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
    addMonopoly(id, playerName);
    console.log("Waiting for player to continue..");  
    $('#continueButton').show();
    var continueButton = document.getElementById("continueButton");
    continueButton.onclick = function() {
        playerDecision(id, playerName);
    }
}

function updateMoney(id, playerName) {
    console.log("Updating Money Overall.");  
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
    console.log("Updating money after specified move.");  
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
    console.log("Choosing to Buy Property.");    
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
    console.log("Moving Player to Jail.");
    moveJail(pieces[index]);
    doublesRolled = false;
    endTurn(id, playerName);
}

function movePlayerToNewSpace(id, playerName) {
    console.log("Moving Player To New Space.");
    //movePlayerToJail(id, playerName);
    var newPosition = getPosition(id, playerName).responseText;
    console.log("New Position: " + newPosition);
    move(pieces[index], row[newPosition], column[newPosition]);
    updateMoneyAfterMove(id, playerName);
    spaceAction(id, playerName);
}

function spaceAction(id, playerName) {
    console.log("Performing SpaceAction.");
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