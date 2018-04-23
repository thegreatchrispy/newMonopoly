$(function() {
	/* validation */
	$("form[name ='register']").validate({
		rules: {
			username: {
				required: true,
				minlength: 4,
				maxlength: 15,
				remote: {
					url: "http://localhost:8080/checkUsername?" + $("form[name ='register']").find('input[username]').serialize()
				}
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
				email: true,
				remote: {
					url: "http://localhost:8080/checkEmail?" + $("form[name ='register']").find('input[email]').serialize()
				}
			},
		},

		messages: {
			username: {
				required: "please enter user name",
				remote: "Username already taken. Please enter a different username."
			},
			password: {
				required: "please provide a password",
				minlength: "password must have at least 6 characters"
			},
			email: {
				required: "please enter a valid email address",
				remote: "Email address already in use. Please enter a different email."
			},
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
		var data = $("form[name ='register']").find('input[name!=cpassword]').serialize();

		console.log(data);
		var urlString = "http://localhost:8080/registration?" + data;

		$.ajax({
			url: `${urlString}`,
			method: "POST",
			dataType: "json",
			contentType: "text/plain;charset=UTF-8",
			success: function() {
				alert("account created");
			}
		});

		$("#success-msg").css("display", "block");
		window.setTimeout(function(){
			// Move to a new location or you can do something else
			window.location.href = "http://localhost:8080/login";
	
		}, 2000);
	}
	/* end form submission */
});