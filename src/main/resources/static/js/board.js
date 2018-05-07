function endTurn(id, playerName) {
    console.log("Ending players turn.");  
    console.log("");
    if (doublesRolled) {
        addAlert("It is " + names[index] + "'s turn again!");
        doublesRolled = false;
        $('#buildButton').hide();
        $('#tradeButton').hide();
        $('#accountsButton').hide();
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
        $('#accountsButton').hide();
        $('#endButton').hide();
        $('#dieButton').show();
    }
}

function validateDevelopment(money, balance, numberOfHotels, numberOfHouses, totalHotels, totalHouses, maxBuildings, minBuildings) {
    var status = "";

    balance = balance * -1;

    if ((numberOfHotels - totalHotels) < 0) {
        status = "hotels";
    } else if ((numberOfHouses - totalHouses) < 0) {
        status = "houses";
    } else if ((money - balance) <= 0) {
        status = "money";
    } else if ((maxBuildings - minBuildings) > 1) {
        status = "even";
    } else {
        status = "pass";
    }

    return status;
}

function updateBalance(numberOfProperties, currentBuildings, buyingCosts, sellingCosts) {
    console.log("In updateBalance");
    var balance = 0;
    for (i = 0; i < numberOfProperties; i++) {
        var newBuildings = Number($('#field' + (i+1)).val());
        console.log(newBuildings);
        console.log(currentBuildings[i]);
        if (newBuildings > currentBuildings[i]) {
            balance = balance - ( (newBuildings - currentBuildings[i]) * buyingCosts[i] );
        } else if (newBuildings < currentBuildings[i]) {
            balance = balance + ( (currentBuildings[i] - newBuildings) * sellingCosts[i]);
        }
    }
    console.log(balance);
    $("#numberhouses_sub").html("BALANCE: $" + balance);
}

function buildHouses(id, playerName, group) {
    $("#numberhouses_sub").html("BALANCE: $0");
    var money = getMoney(id, playerName).responseText;
    var string = getBuildingInfo(id, playerName, group).responseText.split(":");
    var numberOfProperties = Number(string[0]);
    var numberOfHotels = Number(string[1]);
    var numberOfHouses = Number(string[2]);
    var properties = string[3].split(";")
    var currentBuildings = [];
    var buyingCosts = [];
    var sellingCosts = [];
    
    for (i = 0; i < numberOfProperties; i++) {
        var property = properties[i].split(",");
        var card = "#card-" + (i+1);
        var details = "#details-" + (i+1);
        $(card).show();
        $(details).html(property[0]);
        $('#field' + (i+1)).val(property[1]);
        currentBuildings[i] = Number(property[1]);
        buyingCosts[i] = Number(property[2]);
        sellingCosts[i] = Number(property[3]);
    }

    $('.qtyplus').click(function(e){
        // Stop acting like a button
        e.preventDefault();
        // Get the field name
        fieldName = $(this).attr('field');
        // Get its current value
        var currentVal = parseInt($('input[name='+fieldName+']').val());
        // If is not undefined
        if (!isNaN(currentVal) && currentVal < 5) {
            // Increment
            $('input[name='+fieldName+']').val(currentVal + 1);
        } else {
            // Otherwise put a 0 there
            $('input[name='+fieldName+']').val(5);
        }
        updateBalance(numberOfProperties, currentBuildings, buyingCosts, sellingCosts);
    });
    // This button will decrement the value till 0
    $(".qtyminus").click(function(e) {
        // Stop acting like a button
        e.preventDefault();
        // Get the field name
        fieldName = $(this).attr('field');
        // Get its current value
        var currentVal = parseInt($('input[name='+fieldName+']').val());
        // If it isn't undefined or its greater than 0
        if (!isNaN(currentVal) && currentVal > 0) {
            // Decrement one
            $('input[name='+fieldName+']').val(currentVal - 1);
        } else {
            // Otherwise put a 0 there
            $('input[name='+fieldName+']').val(0);
        }
        updateBalance(numberOfProperties, currentBuildings, buyingCosts, sellingCosts);
    });

    $('#submit_button').html("PURCHASE");
    $('#submit_button').show();
    var submit = document.getElementById("submit_button");
    submit.onclick = function() {
        var balance = $("#numberhouses_sub").html().split("$")[1];
        var totalHouses = 0;
        var totalHotels = 0;
        var maxBuildings = 0;
        var minBuildings = 5;

        // Find total number of houses and hotels.
        for (i = 0; i < numberOfProperties; i++) {
            var number = Number($('#field' + (i+1)).val());
            // Find total number of houses and hotels.
            if(number == 5) {
                totalHotels++;
            } else {
                totalHouses += number;
            }

            // Find max and min number of buildings out of all properties.
            if(number >= maxBuildings) {
                maxBuildings = number;
            }

            if(number <= minBuildings) {
                minBuildings = number;
            }
        }

        var status = validateDevelopment(money, balance, numberOfHotels, numberOfHouses, totalHotels, totalHouses, maxBuildings, minBuildings);
        switch (status) {
            case "hotels":
                addAlert("There are not enough hotels available!");
                break;
            case "houses":
                addAlert("There are not enough houses available!");
                break;
            case "money":
                addAlert("You don't have enough money to process this!");
                break;
            case "even":
                addAlert("You must even build!");
                break;
            case "pass":
                addAlert(playerName + " has built improvements!");
                for (i = 0; i < numberOfProperties; i++) {
                    var property = properties[i].split(",");
                    var number = Number($('#field' + (i+1)).val());
                    purchaseBuildings(id, playerName, property[0], number);
                }
                setTimeout(function() {
                    $('#submit_button').html("SUBMIT");
                    $('#submit_button').show();
                    $('#build').hide();
                    $('#numberhouses').hide();
                    $('#options-box').hide();
                    $("#alert_build").html("");
                    $('#card-1').hide();
                    $('#card-2').hide();
                    $('#card-3').hide();
                    updateMoney(id, playerName);
                }, 500);
                break;
        }

    }
}

function buildProperties(id, playerName) {
    $('#build_sub').html("SELECT GROUP");
    var monopoly_group = getMonopolyGroups(id, playerName).responseText.split(";");

    var cancel = document.getElementById("cancel_button");
    cancel.onclick = function() {
        $('#options-box').hide();
        $('#build').hide();
        $('#alert_build').html("");
        playerDecision(id, playerName);
    }
    $('#submit_button').html("CONTINUE");
    $('#submit_button').show();
    var submit = document.getElementById("submit_button");
    submit.onclick = function() {
        var selected_group = $('#group_select').val();
        var compared_group = Number(monopoly_group[selected_group - 1]);
        if (selected_group == 0) {
            addBuildAlert("Please select a monoopoly group or quit!");
        } else if (compared_group != 1) {
            addBuildAlert("You do not own this monopoly group! Please select a different one.");
        } else {
            $('#build').hide();
            $('#numberhouses').show();
            buildHouses(id, playerName, (selected_group));
        }
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
            $('#submit_button').show();
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#accountsButton').hide();
            $('#endButton').hide();
            $('#viewLogButton').hide();
            buildProperties(id, playerName);
            console.log("You can build!");
        } else {
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#accountsButton').hide();
            $('#endButton').hide();
            $('#viewLogButton').hide();
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
            $('#submit_button').show();
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#accountsButton').hide();
            $('#endButton').hide();
            $('#viewLogButton').hide();
            tradeProperties(id, playerName);
            console.log("You can trade!");
        } else {
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#accountsButton').hide();
            $('#endButton').hide();
            $('#viewLogButton').hide();
            addAlert("There are currently no owned properties for any players! Go out and buy some!");
            waitContinue(id, playerName);
        }
    }

    $('#accountsButton').show();
    var accounts = document.getElementById("accountsButton");
    accounts.onclick = function() {
        var canMortgage = getMortgageStatus(id, playerName).responseText;
        if (canMortgage == "true") {
            addAlert(playerName + " is currently viewing their accounts!");
            $('#options-box').show();
            $('#accounts').show();
            $('#submit_button').show();
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#accountsButton').hide();
            $('#endButton').hide();
            $('#viewLogButton').hide();
            mortgageProperties(id, playerName);
            console.log("You can mortgage!");
        } else {
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#accountsButton').hide();
            $('#endButton').hide();
            $('#viewLogButton').hide();
            addAlert("You currently own no properties!");
            waitContinue(id, playerName);
        }
    }

    $('#endButton').show();
    var end = document.getElementById("endButton");
    end.onclick = function() {
        endTurn(id, playerName);
    };

    $('#viewLogButton').show();
    var viewLog = document.getElementById("viewLogButton");
    viewLog.onclick = function() {
        $('#options-box').show();
        $('#log_window').show();
        $('#buildButton').hide();
        $('#tradeButton').hide();
        $('#accountsButton').hide();
        $('#endButton').hide();
        $('#viewLogButton').hide();
        $('#submit_button').hide();
        addAlert("Viewing Log...");
        var cancel = document.getElementById("cancel_button");
        cancel.onclick = function() {
            $('#options-box').hide();
            $('#log_window').hide();
            playerDecision(id, playerName);
        }
    }
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

function bidStillActive() {
    var count = 0;
    for(i = 0; i < numPlayers; i++) {
        if (players_in_auction[i] == 1) {
            count++;
        }
    }

    if (count > 1) {
        bidStatus = true;
    } else {
        bidStatus = false;
    }
}

function findAuctionWinner() {
    var winner_index;
    for (i = 0; i < numPlayers; i++) {
        if (players_in_auction[i] == 1) {
            winner_index = i;
            break;
        }
    }
    return winner_index;
}

function changePlayerAuctionStatus() {
    players_in_auction[auction_index] = 0;
}

function auction(id, playerName) {
    bidStillActive();
    if (bidStatus) {
        if(players_in_auction[auction_index]) {
            addAuctionAlert("Please enter a bid more than the current bid amount, or pass the auction, " + names[auction_index]);
            var string = $('#auction_sub').html().split("$");
            var currentBid = Number(string[1]);

            var submit = document.getElementById("submit_button");
            submit.onclick = function() {
                var bid = $('#bid_amount').val();
                if (bid <= currentBid) {
                    addAuctionAlert(names[auction_index] + ", you must enter a bid greater than the current bid!");
                } else {
                    addAuctionAlert("");
                    $('#auction_sub').html("CURRENT BID: $" + bid);
                    incrementAuctionIndex();
                    auction(id, playerName);
                }
            }

            var pass = document.getElementById("cancel_button");
            pass.onclick = function() {
                addAuctionAlert(names[auction_index] + " has left the auction!");
                addAuctionAlert("");
                changePlayerAuctionStatus();
                incrementAuctionIndex();
                auction(id, playerName);
            }
        } else {
            incrementAuctionIndex();
        }
    } else {
        var string = $('#auction_sub').html().split("$");
        var currentBid = Number(string[1]);
        addAlert(names[findAuctionWinner()] + " has won the auction!");
        auctionPurchase(id, playerName, names[findAuctionWinner()], currentBid);
        setTimeout(function() {
            $('#cancel_button').html("CANCEL");
            $('#submit_button').show();
            $('#auction').hide();
            $('#options-box').hide();
            $('#alert_auction').html("");
            $('#bid_amount').val(0);
            addMonopolyAfterAuction(id, playerName, names[findAuctionWinner()]);
            bidStatus = true;
            players_in_auction = [1, 1, 1, 1, 1, 1];
            updateMoney(id, playerName);
        }, 500);
    }

    //$('#cancel_button').html("CANCEL");
}

function chooseToBuy(id, playerName) {
    console.log("Choosing to Buy Property.");    
    $('#acceptButton').show();
    var accept = document.getElementById("acceptButton");
    accept.onclick = function() {
        acceptPurchase(id, playerName);
        $('#acceptButton').hide();
        $('#declineButton').hide();
        setTimeout(function() {
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
        $('#auction_sub').html("CURRENT BID: $0");
        addAuctionAlert("An Auction Has Begun!");
        $('#cancel_button').html("PASS");
        $('#submit_button').show();
        auction(id, playerName);
    }
    $('#declineButton').onclick = function() {declinePurchase(id, playerName)};

}

function movePlayerToJail(id, playerName) {
    console.log("Moving Player to Jail.");
    moveJail(pieces[index]);
    doublesRolled = false;
    var lockup = 'in-jail' + (index + 1);
    popup_bars(lockup,'yei');
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
