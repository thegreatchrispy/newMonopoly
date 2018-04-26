$(document).ready(function(){
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
	
	var numPlayers = getNumberOfPlayers().responseText;
	
	var names = getNamesOfPlayers().responseText.split(" ");

	

    // var div_count = 0;
    var $playerBlocks = $('#player1, #player2, #player3, #player4, #player5, #player6');
    var $playerTokens = $('#piece_1, #piece_2, #piece_3, #piece_4, #piece_5, #piece_6');
    var $playerNames = $('#player1-name, #player2-name, #player3-name, #player4-name, #player5-name, #player6-name');
    // var $allButtons = $('#back, #page_back, #forward');
    // console.log($allDivs.length);
	// Button Functions
	

	for(i = 0; i < numPlayers; i++) {
		$playerBlocks.eq( i ).show();
		$playerTokens.eq( i ).show();
		$playerNames.eq( i ).html(names[i]);
	}
});
