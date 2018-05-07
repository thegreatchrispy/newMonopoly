function tradeProperties(id, playerName) {
    $('#trade_imp_list').html("");
	$('#trade_unimp_list').html("");

    // Set the players on the trade_player_selection
    for (i = 0; i < numPlayers; i++) {
        if (names[i] != names[index]) {
            $('#trade_player_selection').append($('<option>', {
                value: (i+1),
                text: names[i]
            }))
        }
	}

	var propertiesAndPrices = []; // Temp variable for list of property name and price pairs
	var currentPrices = []; // Store the current player's owned property prices
	var tradePrices = []; // Store the selected trade player's owned property prices
	var currentOwnedProperties = []; // Store the current player's owned property names
	var tradeOwnedProperties = []; // Store the selected trade player's owned property names
	var currentAmount = 0; // Store the amount of money the current player wishes to trade
	var tradeAmount = 0; // Store the amount of money the selected trade player wishes to trade

    var cancel = document.getElementById("cancel_button");
    cancel.onclick = function() {
        console.log("cancel");
        $('#other_trade_amount').hide();
        $('#trade-listbox-area').hide();
        $('#options-box').hide();
        $('#trade').hide();

        $('#trade_player_selection')
        .find('option')
        .remove()
        .end()
        .append('<option value="0" selected="selected">Select Player to trade with</option>')
        .val('0');

        playerDecision(id, playerName);
    }

    $('#trade_player_selection').change(function() {
        if ($('#trade_player_selection').val() == "0") {
            $('#other_trade_amount').hide();
			$('#trade-listbox-area').hide();
        }
        else {
            $('#other_trade_amount').show();
			$('#trade-listbox-area').show();
			// populate current player side
			currentOwnedProperties = getOwnedProperties(id, playerName).responseText.split(";");
			console.log("current properties: " + currentOwnedProperties[0]);
			for (var i = 0; i < currentOwnedProperties.length - 1; i++) {
				var listItem = "<li id='trade_opt_o_" + i + "' role='option' aria-selected='false'>";
        		$('#trade_imp_list').append($(listItem));
        		$('#trade_opt_o_' + i).html(currentOwnedProperties[i]);
			}

			//populate selected trade player side
			tradeOwnedProperties = getOwnedProperties(id, $("#trade_player_selection option:selected").text()).responseText.split(";");
            console.log("trade properties: " + tradeOwnedProperties[i]);
			for (var i = 0; i < tradeOwnedProperties.length - 1; i++) {
				var listItem = "<li id='trade_opt_t_" + i + "' role='option' aria-selected='false'>";
				$('#trade_unimp_list').append($(listItem));
        		$('#trade_opt_t_' + i).html(tradeOwnedProperties[i]);
			}
        }
    });
}