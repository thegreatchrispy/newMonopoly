$(document).ready(function(){
	// Dropdown Function
	$(".dropdown-toggle").dropdown();
	
	// Generate either "Create Game" or "Join Game" display. These displays will be hidden by default, and only appear on user command.
    $('.choice').click(function(){
    	var id = $(this).attr("rel");

		if($('#join').is(":visible")) {
			$('#join').hide();
		}

		if($('#create').is(":visible")) {
			$('#create').hide();
		}

		$('#content').height(500);
		$('#'+id).show();
		
	});

	// Radio Functions
	$("input[type='radio']").change(function(){
		// Lobby Type Radio
		if($(this).val()=='private') {
			$('#lobby_password_label').show();
			$('#show_password_label').show();
			$('#lobby_password').show();
			$('#show_lobby_password').show();
		}
		if($(this).val()=='public') {
			$('#lobby_password_label').hide();
			$('#show_password_label').hide();
			$('#lobby_password').hide();
			$('#show_lobby_password').hide();
		}

		// Difficulty Radio
		if($(this).val()=='on') {
			$('#difficulty').show();
			$('#difficulty_label').show();
		}
		if($(this).val()=='off') {
			$('#difficulty').hide();
			$('#difficulty_label').hide();
		}
	});

	// Show Lobby Password Function
	$("#show_lobby_password").change(function(){
		$('#show_password').val($('#lobby_password').val());
		var isChecked = $(this).prop('checked');
		if (isChecked) {
			$('#lobby_password').hide();
			$('#show_password').show();
		}
		else {
			$('#lobby_password').show();
			$('#show_password').hide();
		}
	});

	// Turns Function
	$("#unlimited_check").change(function(){
		var isChecked = $(this).prop('checked');
		if (isChecked) {
			$("#turns_quantity").attr("disabled", "disabled");
			$("#turns_quantity").val("");
			$("#turns_quantity").css("background-color", "#DEDEDE");
		}
		else {
			$("#turns_quantity").removeAttr("disabled");
			$("#turns_quantity").css("background-color", "#FFFFFF");
		}
	})
});

