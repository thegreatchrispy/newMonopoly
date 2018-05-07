function checkIfGameHasWinner() {
    var count = 0;
    var winner_index = 0;
    for (i = 0; i < numPlayers; i++) {
        if (players_in_game[i] == 1) {
            count++;
            winner_index++;
        }
    }

    if (count == 1) {
        window.location.href = "http://localhost:8080/gameover?player=" + names[winner_index];
    }
}

function incrementTurnCounter(seasonChange) {
    turnCount += 1;
    if((turnCount % seasonChange) == 0) {
        changeNextSeason();
    }
}

function changeNextSeason() {
    currentSeason += 1;
    $(".center").css("background","url(images/"+currentSeason+".jpg)");
    $(".center").css("background-size","50%");

    if (currentSeason == 3){
        currentSeason = -1;
    }
}

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
        checkIfGameHasWinner();
        incrementTurnCounter(2);
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

    var cancel = document.getElementById("cancel_button");
    cancel.onclick = function() {
        $('#numberhouses').hide();
        $('#build').hide();
        $('#options-box').hide();
        $('#alert_build').html("");
        playerDecision(id, playerName);
    }
}

function buildProperties(id, playerName) {
    $('#build_sub').html("SELECT GROUP");
    var monopoly_group = getMonopolyGroups(id, playerName).responseText.split(";");

    var cancel = document.getElementById("cancel_button");
    cancel.onclick = function() {
        $('#build').hide();
        $('#options-box').hide();
        $('#alert_build').html("");
        $('#submit_button').html("SUBMIT");
        playerDecision(id, playerName);
    }
    $('#submit_button').html("CONTINUE");
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

function tradeProperties(id, playerName) {
    var cancel = document.getElementById("cancel_button");
    cancel.onclick = function() {
        console.log("cancel");
        $('#options-box').hide();
        $('#trade').hide();
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
            $('#accountsButton').hide();
            $('#endButton').hide();
            buildProperties(id, playerName);
            console.log("You can build!");
        } else {
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#accountsButton').hide();
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
            $('#accountsButton').hide();
            $('#endButton').hide();
            tradeProperties(id, playerName);
            console.log("You can trade!");
        } else {
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#accountsButton').hide();
            $('#endButton').hide();
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
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#accountsButton').hide();
            $('#endButton').hide();
            mortgageProperties(id, playerName);
            console.log("You can mortgage!");
        } else {
            $('#buildButton').hide();
            $('#tradeButton').hide();
            $('#accountsButton').hide();
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
        console.log(money);
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
    var playerMoney = [];
    for (i = 0; i < numPlayers; i++) {
        playerMoney[i] = getMoney(id, names[i]).responseText;
    }


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
                } if (playerMoney[auction_index] - bid <= 0) {
                    addAuctionAlert(names[auction_index] + ", you must enter a bid that you can afford!");
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
        var spaceName = getSpaceName(id, playerName).responseText;
        addAlert("An auction has begun for " + spaceName + "!");
        $('#acceptButton').hide();
        $('#declineButton').hide();
        $('#options-box').show()
        $('#auction').show();
        $('#auction_sub').html("CURRENT BID: $0");
        addAuctionAlert("An Auction Has Begun for " + spaceName + "!");
        $('#cancel_button').html("PASS");
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

/* DEBT FUNCTION */
function validateDebtDevelopment(money, debt, balance, numberOfHotels, numberOfHouses, totalHotels, totalHouses, maxBuildings, minBuildings) {
    var status = "";

    if ((numberOfHotels - totalHotels) < 0) {
        status = "hotels";
    } else if ((numberOfHouses - totalHouses) < 0) {
        status = "houses";
    } else if (balance < debt) {
        status = "money";
    } else if ((maxBuildings - minBuildings) > 1) {
        status = "even";
    } else {
        status = "pass";
    }

    return status;
}

function updateDebt(numberOfProperties, currentBuildings, buyingCosts, sellingCosts) {
    console.log("In updateDebt");
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
    var debt = Number( $("#numberhouses_sub").html().split("$")[1] );
    debt = debt + balance;
    console.log(balance);
    $("#numberhouses_sub").html("AMOUNT DEBT: $" + debt);
}

function sellHouses(id, playerName, group, debt, debtToAll, debtInJail) {
    $("#numberhouses_sub").html("AMOUNT DUE: $" + debt);
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
        updateDebt(numberOfProperties, currentBuildings, buyingCosts, sellingCosts);
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
        updateDebt(numberOfProperties, currentBuildings, buyingCosts, sellingCosts);
    });

    $('#cancel_button').show();
    $('#cancel_button').html("BACK");
    var cancel = document.getElementById("cancel_button");
    cancel.onclick = function() {
        $('#numberhouses').hide();
        $("#alert_build").html("");
        $('#card-1').hide();
        $('#card-2').hide();
        $('#card-3').hide();
        selectHousesToSell(id, playerName, debt, debtToAll);
    }

    $('#submit_button').html("PURCHASE");
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

        var status = validateDebtDevelopment(money, debt, balance, numberOfHotels, numberOfHouses, totalHotels, totalHouses, maxBuildings, minBuildings);
        switch (status) {
            case "hotels":
                addAlert("There are not enough hotels available!");
                break;
            case "houses":
                addAlert("There are not enough houses available!");
                break;
            case "money":
                addAlert("This is not satisfying the current amount owed!");
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
                    updateMoneyAfterMove(id, playerName);
                    $('#submit_button').html("SUBMIT");
                    $('#build').hide();
                    $('#numberhouses').hide();
                    $('#options-box').hide();
                    $("#alert_build").html("");
                    $('#card-1').hide();
                    $('#card-2').hide();
                    $('#card-3').hide();

                    var playerMoney = getMoney(id, playerName).responseText;
                    var newDebt = debt + playerMoney;
                    if (newDebt >= 0) {
                        addAlert(playerName + " has paid off their debt!");
                        if (debtToAll) {
                            giveToAllPlayers(id, playerName, debt);
                            setTimeout(function() {
                                updateMoney(id, playerName);
                            }, 500);
                        } else if (debtInJail) {
                            getOutOfJail(id, playerName);
                            setTimeout(function() {
                                var lockup = 'in-jail' + (index + 1);
                                popup_bars(lockup,'nei');
                                executeTurnWithoutDoubles(id, playerName);
                            }, 500);
                        } else {
                            payToPlayer(id, playerName, debt);
                            setTimeout(function() {
                                updateMoney(id, playerName);
                            }, 500);
                        }
                    } else {
                        debt(id, playerName, newDebt, debtToAll, debtInJail);
                    }
                }, 500);
                break;
        }

    }
}

function selectHousesToSell(id, playerName, debt, debtToAll, debtInJail) {
    $('#options-box').show();
    $('#build').show();
    $('#build_sub').html("AMOUNT DUE: $" + debt);
    var monopoly_group = getHouseGroups(id, playerName).responseText.split(";");

    $('#cancel_button').hide();
    
    $('#submit_button').html("CONTINUE");
    var submit = document.getElementById("submit_button");
    submit.onclick = function() {
        var selected_group = $('#group_select').val();
        var compared_group = Number(monopoly_group[selected_group - 1]);
        if (selected_group == 0) {
            addBuildAlert("Please select a monoopoly group!");
        } else if (compared_group != 1) {
            addBuildAlert("You do not own any houses on this monopoly group! Please select a different one.");
        } else {
            $('#build').hide();
            $('#numberhouses').show();
            sellHouses(id, playerName, (selected_group), debt, debtToAll, debtInJail);
        }
    }
}

function mortgage(id, playerName, debt, debtToAll, debtInJail) {
    $('#options-box').show();
    $('#accounts').show();
    $('#accounts_sub').html("AMOUNT DUE: $" +debt);
    $('#ms_imp_list').html("");
    $('#ms_unimp_list').html("");

    /* Constants */
    var mortgageInfo = [];
    mortgageInfo = getMortgageInfo(id, playerName).responseText.split(":");
    console.log(mortgageInfo);
    var unmortgageProperties = mortgageInfo[0].split(";");
    var mortgageProperties = mortgageInfo[1].split(";");
    var money = Number(mortgageInfo[2]);

    /* New Lists */
    var additionalMortgages =  [];
    var additionalPayoffs = [];

    var totalLoanAmount = 0;
    for (i = 0; i < (unmortgageProperties.length - 1); i++) {
        var property = unmortgageProperties[i].split(",");
        var listItem = "<li id='ms_opt_u_" + i + "' role='option' aria-selected='false'>";
        $('#ms_imp_list').append($(listItem));
        $('#ms_opt_u_' + i).html(property[0]);
    }

    var totalMortgageValue = 0;
    for (i = 0; i < (mortgageProperties.length - 1); i++) {
        var property = mortgageProperties[i].split(",");
        var listItem = "<li id='ms_opt_m_" + i + "' role='option' aria-selected='false'>";
        $('#ms_unimp_list').append($(listItem));
        $('#ms_opt_m_' + i).html(property[0]);
    }

    var mortgage = document.getElementById("ex2-add");
    mortgage.onclick = function() {
        var balance = Number($("#accounts_sub").html().split("$")[1]);
        var subtotal = 0;
        var mortgage_list = document.getElementById("mortgaged").getElementsByTagName("li");
        var count = 0;
        for (i = 0; i < mortgage_list.length; i++) {
            if (mortgage_list[i].id.split("_")[2] == "u") {
                var property = unmortgageProperties[mortgage_list[i].id.split("_")[3]].split(",");
                additionalMortgages[count] = property;
                subtotal += Number(property[1]);
                count++;
            }
        }

        if ( count != 0 ) {
            totalMortgageValue = subtotal;
        } else {
            totalLoanAmount = 0;
            totalMortgageValue = 0;
        }

        balance = totalMortgageValue - totalLoanAmount;
        debt = debt + balance
        $("#accounts_sub").html("AMOUNT DUE: $" + debt);
    }

    var payOff = document.getElementById("ex2-delete");
    payOff.onclick = function() {
        var balance = Number($("#accounts_sub").html().split("$")[1]);
        var subtotal = 0;
        var unmortgage_list = document.getElementById("unmortgaged").getElementsByTagName("li"); 
        var count = 0;
        for (i = 0; i < unmortgage_list.length; i++) {
            if (unmortgage_list[i].id.split("_")[2] == "m") {
               var property = mortgageProperties[unmortgage_list[i].id.split("_")[3]].split(",");
               additionalPayoffs[count] = property;
               subtotal += Number(property[2]);
               count++;
            }
        }

        console.log(count);

        if ( count != 0 ) {
            totalLoanAmount = subtotal;
        } else {
            totalMortgageValue = 0;
            totalLoanAmount = 0;
        }
        balance = totalMortgageValue - totalLoanAmount;
        debt = debt + balance;
        $("#accounts_sub").html("AMOUNT DUE: $" + debt);
    }

    var submit = document.getElementById("submit_button");
    submit.onclick = function () {
        var balance = Number($("#accounts_sub").html().split("$")[1]);
        if (balance < 0) {
            addAlert("You still have a debt to pay!");
        } else {
            for (i = 0; i < additionalMortgages.length; i++) {
                var property = additionalMortgages[i];
                console.log(property[1]);
                addMortgage(id, playerName, property[0], Number(property[1]));
            }
            for (i = 0; i < additionalPayoffs.length; i++) {
                var property = additionalPayoffs[i];
                payMortgage(id, playerName, property[0], Number(property[2]));
            }
            setTimeout(function() {
                updateMoneyAfterMove(id, playerName);
                $('#accounts').hide();
                $('#options-box').hide();
                addAlert(playerName + " has made updates to their accounts!");

                var playerMoney = getMoney(id, playerName).responseText;
                addAlert(playerName + " has paid off their debt!");
                if (debtToAll) {
                    giveToAllPlayers(id, playerName, debt);
                    setTimeout(function() {
                        updateMoney(id, playerName);
                    }, 500);
                } else if (debtInJail) {
                    getOutOfJail(id, playerName);
                    setTimeout(function() {
                        var lockup = 'in-jail' + (index + 1);
                        popup_bars(lockup,'nei');
                        executeTurnWithoutDoubles(id, playerName);
                    }, 500);
                } else {
                    payToPlayer(id, playerName, debt);
                    setTimeout(function() {
                        updateMoney(id, playerName);
                    }, 500);
                }
            }, 500);
        }   
    }
    $('#cancel_button').hide();
}

function debt(id, playerName, debt, debtToAll, debtInJail) {
    var playerOwnsHouses = hasAnyHouses(id, playerName).responseText;
    debt = debt * -1;
    if (playerOwnsHouses == "true") {
        selectHousesToSell(id, playerName, debt, debtToAll, debtInJail);
    } else {
        mortgage(id, playerName, debt, debtToAll, debtInJail);
    }
}

/* BANKRUPT FUNCTION. */

function removePlayer(id, playerName) {
    doublesRolled = false;
    players_in_game[index] = 0;
    $(playerBlocks[i]).hide();
    $(playerTokens[i]).hide();
    playerBlocks.push(array.splice(index, 1)[0]);
    playerTokens.push(array.splice(index, 1)[0]);
    playerNames.push(array.splice(index, 1)[0]);
    pieces.push(array.splice(index, 1)[0]);
    names.push(array.splice(index, 1)[0]);
    numPlayers = numberPlayer - 1;
    removePlayerFromBoard(id, playerName);
    addAlert(playerName + " has been removed from the game!");
}

function auctionBankruptProperty(id, playerName, spaceName) {
    var playerMoney = [];
    for (i = 0; i < numPlayers; i++) {
        playerMoney[i] = getMoney(id, names[i]).responseText;
    }
    players_in_auction[index] = 0;


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
                } if (playerMoney[auction_index] - bid <= 0) {
                    addAuctionAlert(names[auction_index] + ", you must enter a bid that you can afford!");
                } else {
                    addAuctionAlert("");
                    $('#auction_sub').html("CURRENT BID: $" + bid);
                    incrementAuctionIndex();
                    auctionBankruptProperty(id, playerName, spaceName);
                }
            }

            var pass = document.getElementById("cancel_button");
            pass.onclick = function() {
                addAuctionAlert(names[auction_index] + " has left the auction!");
                addAuctionAlert("");
                changePlayerAuctionStatus();
                incrementAuctionIndex();
                auctionBankruptProperty(id, playerName, spaceName);
            }
        } else {
            incrementAuctionIndex();
        }
    } else {
        var string = $('#auction_sub').html().split("$");
        var currentBid = Number(string[1]);
        addAlert(names[findAuctionWinner()] + " has won the auction!");
        auctionBankruptPurchase(id, names[findAuctionWinner()], currentBid, spaceName);
        setTimeout(function() {
            $('#cancel_button').html("CANCEL");
            $('#auction').hide();
            $('#options-box').hide();
            $('#alert_auction').html("");
            $('#bid_amount').val(0);
            addMonopolyAfterAuction(id, playerName, names[findAuctionWinner()]);
            updateMoneyAfterMove(id, names[findAuctionWinner()]);
            bidStatus = true;
            players_in_auction = [1, 1, 1, 1, 1, 1];
            bankruptProperties.shift();
        }, 500);
    }
}

function bankrupt(id, playerName) {
    if (bankruptProperties.length == 0) {
        addAlert("All of " + playerName + "'s properties have been sold!");
        removePlayer(id, playerName);
        setTimeout(function() {
            waitContinueAfterLockedUp(id, playerName);
        }, 500);
    } else {
        addAlert("An auction has begun for " + bankruptProperties[0] + "!");
        $('#options-box').show()
        $('#auction').show();
        $('#auction_sub').html("CURRENT BID: $0");
        $('#cancel_button').html("PASS");
        addAuctionAlert("An auction has begun for " + bankruptProperties[0]);
        auctionBankruptProperty(id, playerName, bankruptProperties[0]);
    }
}

/* SPACE ACTION */
function spaceAction(id, playerName) {
    console.log("Performing SpaceAction.");
    var string = performSpaceAction(id, playerName).responseText.split(";");
    setTimeout(function() {
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
            case "bankrupt":
                bankruptProperties = getOwnedProperties(id, playerName).responseText.split(";");
                bankrupt(id, playerName);
                break;
            case "debt":
                var debtPrice = Number(string[string.length - 2]); //Number();
                debt(id, playerName, debtPrice, false, false);
                break;
            case "debtToAll":
                var debtPrice = Number(string[string.length - 2]); //Number();
                debt(id, playerName, debtPrice, true, false);
                break;
       }
    }, 500);
}
