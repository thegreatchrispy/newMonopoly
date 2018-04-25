$(document).ready(function(){
    var div_count = 0;
    var $allDivs = $('#option, #about, #rules_1, #rules_2, #rules_3, #rules_4, #rules_5');
    var $allButtons = $('#back, #page_back, #forward');
    console.log($allDivs.length);
	// Button Functions
	$('#about_button').click(function(){
        $allDivs.eq( div_count ).hide();
        div_count++;
        $allDivs.eq( div_count ).show();
        
        $allButtons.eq( 0 ).show();
		// $('body').css('background-image', 'url("../images/create.jpg")');
    });

    $('#rules_button').click(function(){
        $allDivs.eq( div_count ).hide();
        div_count = 2;
        $allDivs.eq( div_count ).show();

        $allButtons.eq( 0 ).show();
        $allButtons.eq( 2 ).show();
	});
    
    $('#back').click(function(){
        $allDivs.eq( div_count ).hide();
        div_count = 0;
        $allDivs.eq( div_count ).show();

        for(i = 0; i < $allButtons.length; i++) {
            $allButtons.eq( i ).hide();
        }
    });

	$('#forward').click(function(){
        $allDivs.eq( div_count ).hide();
        div_count++;
        $allDivs.eq( div_count ).show();
        if (div_count == $allDivs.length - 1) {
            $allButtons.eq( 2 ).hide();

        }
        $allButtons.eq( 1 ).show();
    });

    $('#page_back').click(function(){
        $allDivs.eq( div_count ).hide();
        div_count--;
        $allDivs.eq( div_count ).show();
        $allButtons.eq( 2 ).show();
        if (div_count == 2) {
            $allButtons.eq( 1 ).hide();
        }
	});
    
    $('#rules_page_back').click(function(){
        var parent = $(this).parent().attr("id");
        var current_parent = "#" + parent;

        var fields = parent.split("_");
        var next_parent_num = Number(fields[1]) - 1;
        var new_parent = "#" + fields[0] + "_" + next_parent_num.toString();

        $(current_parent).hide();
        $(new_parent).show();
	});
});

