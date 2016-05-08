<?php
require 'database.php';

if(isset($_POST['new_message_check'])) {
	open_my_db();

	$instance_hash = "";
	$last_message_hash = "";

	//TODO Have in mind that JSON message can be broken.
	$json = json_decode($_POST['new_message_check'], true);
	foreach ($json as $key => $value) {
		//TODO Move keys as constants in separate PHP file.
		if($key == 'instance_hash') {
			$instance_hash = mysql_real_escape_string( $value );
		}
		if($key == 'last_message_hash') {
			$last_message_hash = mysql_real_escape_string( $value );
		}
	}

	//TODO SELECT count(*) FROM `consultants` WHERE instance_hash='';
	/*
	SELECT `message_hash` FROM `correspondence` WHERE `message_hash` IN (
		SELECT `message_hash` FROM `correspondence` WHERE parent_hash='' AND `registered` > (SELECT `registered` FROM `correspondence` WHERE `message_hash` = '')
	UNION
		SELECT `c1`.`message_hash` FROM `correspondence` as `c1`, `correspondence` as `c2`  WHERE `c1`.`parent_hash`<>'' AND `c1`.`parent_hash`=`c2`.`message_hash` AND `c2`.`instance_hash`='' AND `c1`.`registered` > (SELECT `registered` FROM `correspondence` WHERE `message_hash` = '')
	) ORDER BY `registered` ASC LIMIT 1;
	*/

	$response = '{';
	$response .= "\n";
	
	//TODO Call stored procedure.
	$result = query_my_db( "" );
	if($result != false){
		$response .= '"found":"true"';
		$response .= "\n";
		$response .= '"message_hash":"' . trim($result[0][0],"\r\n") . '"';
	} else {
		$response .= '"found":"false"';
		$response .= "\n";
		$response .= '"message_hash":""';
	}
	$response .= "\n";
	$response .= "}";

	echo( $response );

        close_my_db();
}

?>
