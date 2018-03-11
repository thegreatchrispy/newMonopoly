<?php

$db_host = "localhost";
$db_name = "new_monopoly";
$db_user = "postgres";
$db_pass = "Hibiki519@";

try {
	$db_con = new PDO("pgsql:host={$db_host};dbname={$db_name}", $db_user, $db_pass);
	$db_con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
}
catch(PDOException $e) {
	echo $e->getMessage();
}

?>