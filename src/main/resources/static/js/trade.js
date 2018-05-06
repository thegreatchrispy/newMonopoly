function tradeProperties(id, playerName) {
    $('#ms_imp_list').html("");
	$('#ms_unimp_list').html("");

    // Set the players on the trade_player_selection
    for (i = 0; i < numPlayers; i++) {
        if (names[i] != names[index]) {
            $('#trade_player_selection').append($('<option>', {
                value: i,
                text: names[i]
            }))
        }
	}

	var propertiesAndPrices = [];
	var ownedPrices = [];
	var tradePrices = [];
	var ownedProperties = [];
	var tradeOwnedProperties = [];
	var ownedAmount = 0;
	var tradeAmount = 0;

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

    $('#trade_player_selection').change(function(){
        if ($('#trade_player_selection').val() == "0") {
            $('#other_trade_amount').hide();
			$('#trade-listbox-area').hide();
			propertiesAndPrices = getOwnedProperties(id, playerName).responseText.split(";");
			for (var i = 0; i < propertiesAndPrices.length - 1; i++) {
        		$('#trade_imp_list').remove($('#trade_opt_o_' + i));
			}
        }
        else {
            $('#other_trade_amount').show();
			$('#trade-listbox-area').show();
			propertiesAndPrices = getOwnedProperties(id, playerName).responseText.split(";");
			for (var i = 0; i < propertiesAndPrices.length - 1; i++) {
				var listItem = "<li id='trade_opt_o_" + i + "' role='option' aria-selected='false'>";
        		$('#trade_imp_list').append($(listItem));
        		$('#trade_opt_o_' + i).html(propertiesAndPrices[i]);
			}
        }
    });
}