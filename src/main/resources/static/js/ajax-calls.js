function getNumberOfPlayers() {
	return $.ajax({
    	url:"http://localhost:8080/getnumberofplayers?gameid=1",
    	async: false,
    	success:function(data) {
    		return data;
		}
	});
}

function getNamesOfPlayers() {
	return $.ajax({
    	url:"http://localhost:8080/getnamesofplayers?gameid=1",
    	async: false,
    	success:function(data) {
    		return data;
		}
	});
}

function getSpaceActionAndType(id, playerName) {
    var urlString = "http://localhost:8080/getspaceactionandtype?gameid=" + id + "&player=" + playerName;
	return $.ajax({
    	url:`${urlString}`,
    	async: false,
    	success:function(data) {
            return data;
		}
	});
}

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

function movePlayerToJail(id, name) {
	var urlString = "http://localhost:8080/moveplayertojail?gameid=" + id + "&player=" + name;

	$.ajax({
            url: `${urlString}`,
            async: false,
			success: function(data) {
                addAlert(data);
			}
		});
}

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

function setDoubles(id, name) {
	var urlString = "http://localhost:8080/setdoubles?gameid=" + id + "&player=" + name;

	$.ajax({
            url: `${urlString}`,
            async: false,
			success: function(data) {
			}
		});
}

function askToBuy(id, name) {
	var urlString = "http://localhost:8080/asktobuy?gameid=" + id + "&player=" + name;

	$.ajax({
            url: `${urlString}`,
            async: false,
			success: function(data) {
                addAlert(data);
			}
		});
}

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

function payRent(id, name, owner) {
    var urlString = "http://localhost:8080/payrent?gameid=" + id + "&player=" + name + "&owner=" + owner;

	$.ajax({
            url: `${urlString}`,
            async: false,
			success: function(data) {
                addAlert(data);
			}
		});
}