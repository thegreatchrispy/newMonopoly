$(document).ready(function(){
	// Button Functions
	$('#about_button').click(function(){
		$('#option').hide();
		$('#about').show();
		// $('body').css('background-image', 'url("../images/create.jpg")');
	});

	$('#join_button').click(function(){
		$('#option').hide();
		$('#join').show();
		$('body').css('background-image', 'url("../images/join.jpg")');
	});

	$('#create_back').click(function(){
		$('#create').hide();
		$('body').css('background-image', 'url("../images/option.jpg")');
		$('#option').show();
	});

	$('#join_back').click(function(){
		$('#join').hide();
		$('body').css('background-image', 'url("../images/option.jpg")');
		$('#option').show();
	});
});

