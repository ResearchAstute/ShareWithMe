<?php
require 'database.php';

if(isset($_POST['replay'])) {
	open_my_db();

	$parent_hash = "";
	$message_hash = "";
	$instance_hash = "";
	$message = "";

	//TODO Have in mind that JSON message can be broken.
	$json = json_decode($_POST['replay'], true);
	foreach ($json as $key => $value) {
		//TODO Move keys as constants in separate PHP file.
		if($key == 'parent_hash') {
			$parent_hash = mysql_real_escape_string( $value );
		}
		if($key == 'message_hash') {
			$message_hash = mysql_real_escape_string( $value );
		}
		if($key == 'instance_hash') {
			$instance_hash = mysql_real_escape_string( $value );
		}
		if($key == 'message') {
			$message = mysql_real_escape_string( $value );
		}
	}

	//TODO Replace SQL with stored procedure call.
	query_my_db( "INSERT INTO correspondence (parent_hash, message_hash, instance_hash, message) VALUES ('".$parent_hash."', '".$message_hash."', '".$instance_hash."', '".$message."');" );

        close_my_db();

	//TODO Inform all moderators.
} 

?>
