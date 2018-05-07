$(document).ready(function(){
	// Button Functions
	$('#create_button').click(function(){
		$('#option').hide();
		$('#create').show();
		$('body').css('background-image', 'url("../images/create.jpg")');
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
	});

});

function goToLobby() {
	window.location.href = "http://localhost:8080/lobby";
}

