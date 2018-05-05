function mortgageProperties(id, playerName) {
    $('#accounts_sub').html("BALANCE: $0");
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
        $("#accounts_sub").html("BALANCE: $" + balance);
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
        $("#accounts_sub").html("BALANCE: $" + balance);
    }

    var submit = document.getElementById("submit_button");
    submit.onclick = function () {
        var balance = Number($("#accounts_sub").html().split("$")[1]);
        balance = balance * -1;
        if (money - balance <= 0) {
            addAlert("You cannot afford this transaction!");
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
                $('#accounts').hide();
                $('#options-box').hide();
                addAlert(playerName + " has made updates to their accounts!");
                updateMoney(id, playerName);
            }, 500);
        }   
    }

    var cancel = document.getElementById("cancel_button");
    cancel.onclick = function() {
        $('#accounts').hide();
        $('#options-box').hide();
        playerDecision(id, playerName);
    }
}

