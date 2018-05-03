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

function buildProperties(id, playerName) {
    var cancel = document.getElementById("cancel_button");
    cancel.onclick = function() {
        $('#options-box').hide();
        $('#build').hide();
        playerDecision(id, playerName);
    }
}

function tradeProperties(id, playerName) {
    var cancel = document.getElementById("cancel_button");
    cancel.onclick = function() {
        console.log("cancel");
        $('#options-box').hide();
        $('#trade').hide();
        playerDecision(id, playerName);
    }
}

function mortgageProperties(id, playerName) {
    var cancel = document.getElementById("cancel_button");
    cancel.onclick = function() {
        $('#options-box').hide();
        $('#mortgage').hide();
        playerDecision(id, playerName);
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
    var build = document.getElementById("buildButton");
    build.onclick = function() {
        var canBuild = getBuildStatus(id, playerName).responseText;
        if (canBuild == "true") {
            addAlert(playerName + " is currently building!");
            $('#options-box').show();
            $('#build').show();
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#mortgageButton').hide();
            $('#endButton').hide();
            buildProperties(id, playerName);
            console.log("You can build!");
        } else {
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#mortgageButton').hide();
            $('#endButton').hide();
            addAlert(playerName + ", you are not eligible to build!");
            waitContinue(id, playerName);
        }
    }

    $('#tradeButton').show();
    var trade = document.getElementById("tradeButton");
    trade.onclick = function() {
        var canTrade = getTradeStatus(id).responseText;
        if(canTrade == "true") {
            addAlert(playerName + " is currently trading!");
            $('#options-box').show();
            $('#trade').show();
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#mortgageButton').hide();
            $('#endButton').hide();
            tradeProperties(id, playerName);
            console.log("You can trade!");
        } else {
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#mortgageButton').hide();
            $('#endButton').hide();
            addAlert("There are currently no owned properties for any players! Go out and buy some!");
            waitContinue(id, playerName);
        }
    }

    $('#mortgageButton').show();
    var mortgage = document.getElementById("mortgageButton");
    mortgage.onclick = function() {
        var canMortgage = getMortgageStatus(id, playerName).responseText;
        if (canMortgage == "true") {
            addAlert(playerName + " is currently viewing mortgages!");
            $('#options-box').show();
            $('#mortgage').show();
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#mortgageButton').hide();
            $('#endButton').hide();
            mortgageProperties(id, playerName);
            console.log("You can mortgage!");
        } else {
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#mortgageButton').hide();
            $('#endButton').hide();
            addAlert("You currently own no properties!");
            waitContinue(id, playerName);
        }
    }

    $('#endButton').show();
    var end = document.getElementById("endButton");
    end.onclick = function() {
        endTurn(id, playerName);
    };
}

function waitContinue(id, playerName) {
    console.log("Waiting for player to continue..");  
    $('#continueButton').show();
    var continueButton = document.getElementById("continueButton");
    continueButton.onclick = function() {
        playerDecision(id, playerName);
    }
}

function waitContinueAfterLockedUp(id, playerName) {
    console.log("Waiting for player to continue..");  
    $('#continueButton').show();
    var continueButton = document.getElementById("continueButton");
    continueButton.onclick = function() {
        addAlert(playerName + ", your turn has automatically ended.");
        $('#continueButton').hide();
        endTurn(id, playerName);
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

function auction(id, startingBid) {
    console.log(startingBid);
    //$('#cancel_button').html("CANCEL");
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
            addMonopolyAfterPurchase(id, playerName);
            updateMoney(id, playerName);
        }, 500);
    };

    // Auction Function.
    $('#declineButton').show();
    var decline = document.getElementById("declineButton");
    decline.onclick = function() {
        addAlert("An auction has begun!");
        $('#acceptButton').hide();
        $('#declineButton').hide();
        $('#options-box').show()
        $('#auction').show();
        $('#cancel_button').html("PASS");
        var startingBid = getMortgageValue(id, playerName).responseText;
        auction(id, startingBid);
    }
    $('#declineButton').onclick = function() {declinePurchase(id, playerName)};

}

function movePlayerToJail(id, playerName) {
    console.log("Moving Player to Jail.");
    moveJail(pieces[index]);
    doublesRolled = false;
    waitContinueAfterLockedUp(id, playerName);
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
            updateMoney(id, playerName);
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