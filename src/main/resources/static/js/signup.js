$("document").ready(function() {
	/* validation */
	$("#register-form").validate({
		rules: {
			username: {
				required: true,
				minlength: 4,
				maxlength: 15
			},
			password: {
				required: true,
				minlength: 6,
				maxlength: 25
			},
			cpassword: {
				required: true,
				equalTo: "#password"
			},
			email: {
				required: true,
				email: true
			},
		},
		messages: {
			username: "please enter user name",
			password: {
				required: "please provide a password",
				minlength: "password must have at least 6 characters"
			},
			email: "please enter a valid email address",
			cpassword: {
				required: "please retype your password",
				equalTo: "password doesn't match"
			}
		},
		submitHandler: submitForm
	});
	/* end validation */

	/* form submission */
	function submitForm() {
		var data = $("#register-form").serialize();

		$.ajax({
			type: "POST",
			url: "php/register.php",
			data: data,
			beforeSend: function() {
				$("#error").fadeOut();
				$("#btn-submit").html("<span></span> &nbsp; sending ...");
			},
			success: function(data) {
				if(data==1) {
					$("#error").fadeIn(1000, function() {
						$("#error").html("<div class='alert alert-danger'> <span></span> &nbsp; Sorry email already taken</div>");
						$("#btn-submit").html("<span></span> &nbsp; Create Account");
					});
				}
				else if(data=="registered") {
					$("#btn-submit").html("<img src='btn-ajax-loader.gif' /> &nbsp; Signing Up ...");
					setTimeout("$('.form-signin').fadeOut(500, function() { $('.signin-form').load('index.html'); });", 5000);
				}
				else {
					$("#error").fadeIn(1000, function() {
						$("#error").html("<div class='alert alert-danger'> <span></span> &nbsp; "+data+" </div>");
						$("#btn-submit").html("<span></span> &nbsp; Create Account");
					});
				}
			}
		});
		return false;
	}
	/* end form submission */
});