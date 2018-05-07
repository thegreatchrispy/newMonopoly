/* Retrievals to the Database. */

// Retrieve the number of players.
function getNumberOfPlayers() {
	return $.ajax({
    	url:"http://localhost:8080/getnumberofplayers?gameid=1",
    	async: false,
    	success:function(data) {
    		return data;
		}
	});
}

// Retrieve the names of all the players.
function getNamesOfPlayers() {
	return $.ajax({
    	url:"http://localhost:8080/getnamesofplayers?gameid=1",
    	async: false,
    	success:function(data) {
    		return data;
		}
	});
}

// Retrieve the randomized status from the board.
function isRandomized() {
	return $.ajax({
    	url:"http://localhost:8080/getrandomized?gameid=1",
    	async: false,
    	success:function(data) {
    		return data;
		}
	});
}

// Retrieve the swapped string from the board.
function getSwappedString() {
	return $.ajax({
    	url:"http://localhost:8080/getswappedstring?gameid=1",
    	async: false,
    	success:function(data) {
    		return data;
		}
	});
}

// Retrieve a player's doubles count.
function getDoublesCount(id, playerName) {
    var urlString = "http://localhost:8080/getdoublescount?gameid=" + id + "&player=" + playerName;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            return data;
		}
	});
}

// Retrieve a player's money.
function getMoney(id, playerName) {
    var urlString = "http://localhost:8080/getmoney?gameid=" + id + "&player=" + playerName;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            return data;
		}
	});
}

// Retrieve a player's current position.
function getPosition(id, playerName) {
    var urlString = "http://localhost:8080/getposition?gameid=" + id + "&player=" + playerName;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            return data;
		}
	});
}

// Retrieve a player's inJail status.
function getInJail(id, playerName) {
    var urlString = "http://localhost:8080/getinjail?gameid=" + id + "&player=" + playerName;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            return data;
		}
	});
}

// Retrieve a player's building eligibility.
function getBuildStatus(id, playerName) {
    var urlString = "http://localhost:8080/getbuildstatus?gameid=" + id + "&player=" + playerName;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            return data;
		}
	});
}

// Retrieve a player's building eligibility.
function getMonopolyGroups(id, playerName) {
    var urlString = "http://localhost:8080/getmonopolygroups?gameid=" + id + "&player=" + playerName;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            return data;
		}
	});
}

// Retrieve a player's building eligibility.
function getBuildingInfo(id, playerName, group) {
    var urlString = "http://localhost:8080/getbuildinginfo?gameid=" + id + "&player=" + playerName + "&group=" + group;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            return data;
		}
	});
}

// Retrieve a board's trading eligibility.
function getTradeStatus(id) {
    var urlString = "http://localhost:8080/gettradestatus?gameid=" + id;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            return data;
		}
	});
}

// Retrieve a board's trading eligibility.
function getMortgageStatus(id, playerName) {
    var urlString = "http://localhost:8080/getmortgagestatus?gameid=" + id + "&player=" + playerName;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            return data;
		}
	});
}

// Retrieve a player's building eligibility.
function getMortgageInfo(id, playerName, group) {
    var urlString = "http://localhost:8080/getmortgageinfo?gameid=" + id + "&player=" + playerName;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            return data;
		}
	});
}

// Retrieve a player's owned properties.
function getOwnedProperties(id, playerName) {
	var urlString = "http://localhost:8080/getownedproperties?gameid=" + id + "&player=" + playerName;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            return data;
		}
	});
}

/* Sending calls to set a given value in the Database. */

// Send a call to send a player to jail.
function setInJail(id, playerName) {
    var urlString = "http://localhost:8080/moveplayertojail?gameid=" + id + "&player=" + playerName;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            addAlert(data);
		}
	});
}

// Send a call to increment the player's double count by one.
function incrementDoublesCount(id, name) {
    var urlString = "http://localhost:8080/incrementdoubles?gameid=" + id + "&player=" + name;

    $.ajax({
        url: `${urlString}`,
        async: false,
        success: function(data) {
            addAlert(data);
        }
    });
}

// Send a call to decrement the player's jail time by one.
function decrementJailTime(id, name) {
    var urlString = "http://localhost:8080/decrementjailtime?gameid=" + id + "&player=" + name;

    $.ajax({
        url: `${urlString}`,
        async: false,
        success: function(data) {
            addAlert(data);
        }
    });
}

// Send a call to set a player's double count to zero.
function setDoubles(id, name) {
	var urlString = "http://localhost:8080/setdoubles?gameid=" + id + "&player=" + name;

	$.ajax({
            url: `${urlString}`,
            async: false,
			success: function(data) {
			}
		});
}

// Send a call to set the player out of jail for free.
function getOutOfJailFree(id, playerName) {
    var urlString = "http://localhost:8080/getoutofjailfree?gameid=" + id + "&player=" + playerName;
	$.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            addAlert(data);
		}
	});
}

// Send a call to set the player out of jail at a cost.
function getOutOfJail(id, playerName) {
    var urlString = "http://localhost:8080/getoutofjail?gameid=" + id + "&player=" + playerName;
	$.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            addAlert(data);
		}
	});
}

/* Logical calls to the Database. */

// Send a call to perform the space action of the player's current position.
function performSpaceAction(id, playerName) {
    var urlString = "http://localhost:8080/performspaceaction?gameid=" + id + "&player=" + playerName;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            if (data.split(";")[0] != "") {
                addAlert(data.split(";")[0]);
            }
            return data;
		}
	});
}

// Send a call to move the player by a given value.
function movePlayer(id, name, value) {
	var urlString = "http://localhost:8080/moveplayer?gameid=" + id + "&player=" + name + "&value=" + value;

	$.ajax({
            url: `${urlString}`,
            async: false,
			success: function(data) {
                addAlert(data);
			}
		});
}

// Send a call to accept a purchase of the player's current position.
function acceptPurchase(id, name) {
	var urlString = "http://localhost:8080/acceptpurchase?gameid=" + id + "&player=" + name;

	$.ajax({
            url: `${urlString}`,
            async: false,
			success: function(data) {
                addAlert(data);
			}
		});
}

// Send a call to purchase the property for the winner of an auction
function auctionPurchase(id, name, winner, value) {
	var urlString = "http://localhost:8080/auctionpurchase?gameid=" + id + "&player=" + name + "&winner=" + winner + "&amount=" + value;

	$.ajax({
            url: `${urlString}`,
            async: false,
			success: function(data) {
                addAlert(data);
			}
		});
}

// Check if a player has a new monopoly.
function addMonopolyAfterPurchase(id, playerName) {
    var urlString = "http://localhost:8080/addmonopolyafterpurchase?gameid=" + id + "&player=" + playerName;
	$.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            addAlert(data);
		}
	});
}

// Check if a player has a new monopoly.
function addMonopolyAfterAuction(id, indexName, playerName) {
    var urlString = "http://localhost:8080/addmonopolyafterpurchase?gameid=" + id + "&indexPlayer=" + indexName + "&player=" + playerName;
	$.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            addAlert(data);
		}
	});
}

// Send a call to purchase buildings of specific group.
function purchaseBuildings(id, playerName, spaceName, numberOfBuildings) {
	var urlString = "http://localhost:8080/purchasebuildings?gameid=" + id + "&player=" + playerName + "&space=" + spaceName + "&buildings=" + numberOfBuildings;

	$.ajax({
            url: `${urlString}`,
            async: false,
			success: function(data) {
			}
		});
}

// Send a call to purchase buildings of specific group.
function addMortgage(id, playerName, spaceName, mortgageValue) {
	var urlString = "http://localhost:8080/addmortgage?gameid=" + id + "&player=" + playerName + "&space=" + spaceName + "&value=" + mortgageValue;

	$.ajax({
            url: `${urlString}`,
            async: false,
			success: function(data) {
			}
		});
}

// Send a call to purchase buildings of specific group.
function payMortgage(id, playerName, spaceName, loanValue) {
	var urlString = "http://localhost:8080/paymortgage?gameid=" + id + "&player=" + playerName + "&space=" + spaceName + "&value=" + loanValue;

	$.ajax({
            url: `${urlString}`,
            async: false,
			success: function(data) {
			}
		});
}