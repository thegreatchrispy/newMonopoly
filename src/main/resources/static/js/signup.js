$(function() {
	/* validation */
	$("form[name ='register']").validate({
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
				required: true
// 			    remote: {
// 			    	url: '/checkEmail',
// 			        data: { email: $('#email').val()},
// 			        dataFilter: function(data) {
// //			        	var json = JSON.parse(data);
// 			            console.log(data);
// 			        }
// 			    }
			},
		},

		messages: {
			username: "please enter user name",
			password: {
				required: "please provide a password",
				minlength: "password must have at least 6 characters"
			},
			email: {
				required: "please enter a valid email address",
				// remote: "Email address already in use. Please enter a different email."
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
		$("form[name ='register']").trigger("reset");
		$("#success-msg").css("display", "block");
	}
	/* end form submission */
});