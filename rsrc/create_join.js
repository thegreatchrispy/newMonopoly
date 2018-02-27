$(document).ready(function(){
    $(".dropdown-toggle").dropdown();

    $('.choice').click(function(){
    	var id = $(this).attr("rel");

		if($('#join').is(":visible")) {
			$('#join').hide();
		}

		if($('#create').is(":visible")) {
			$('#create').hide();
		}

    	$('#'+id).show();
	});
});

