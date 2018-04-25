$(function() {
	/* validation */
	$("form[name ='create_game']").validate({
		rules: {
			playerName: {
				required: true,
				minlength: 4,
				maxlength: 15
			}
		},

		messages: {
			playerName: "please enter user name",
		},
		submitHandler: submitForm
	});
	/* end validation */

	/* form submission */
	function submitForm() {
		var data = $("form[name ='player']").serialize();

		console.log(data);
		var urlString = "http://localhost:8080/creategame?players=[ {" + data + ",'turnOrder':0,'money':1500,'currentPosition':0,'doublesCount':0,'jailCard':false,'inJail':false,'jailTime':0,'ownedProperties':[],'monopolyGroup':[0,0,0,0,0,0,0,0],'monopolyProperties':[],'tokenNumber':0},{'name':'player1','turnOrder':0,'money':1500,'currentPosition':0,'doublesCount':0,'jailCard':false,'inJail':false,'jailTime':0,'ownedProperties':[],'monopolyGroup':[0,0,0,0,0,0,0,0],'monopolyProperties':[],'tokenNumber':0}]";

		$.ajax({
			url: `${urlString}`,
			method: "POST",
			dataType: "json",
			contentType: "text/plain;charset=UTF-8",
			success: function() {
				alert("game created");
			}
		});
		$("form[name ='player']").trigger("reset");
		$("#success-msg").css("display", "block");
	}
	/* end form submission */
});