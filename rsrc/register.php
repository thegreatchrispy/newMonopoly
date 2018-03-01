<?php

require_once "dbconfig.php";

if($_POST) {
	$email = $_POST["email"];
	$username = $_POST["username"];
	$password = $_POST["password"];
	$isadmin = $_POST["isAdmin"];

	try {
		$stmt = $db_con->prepare("SELECT * FROM players WHERE email=:email");
		$stmt->execute(array(":email"=>$email));
		$count = $stmt->rowCount();

		if($count==0) {
			$stmt = $db_con->prepare("INSERT INTO players(username,password,email,isAdmin) VALUES(:uname, :pass, :email, :admin)");
			$stmt->bindParam(":uname",$username);
			$stmt->bindParam(":pass",$password);
			$stmt->bindParam(":email",$email);
			$stmt->bindParam(":admin", false);

			if($stmt->execute()) {
				echo "registered";
			}
			else {
				echo "Query could not execute";
			}
		}
		else {
			echo "1"; // not available
		}
	}
	catch(PDOException $e) {
		echo $e->getMessage();
	}
}

?>