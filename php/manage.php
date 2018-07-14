<?php
include_once("db.php");
/*此id為主管的id,員工的upid*/
$id=(!empty($_POST['id']))?$_POST['id']:$_GET['id'];

$output = array();
$sql="SELECT * FROM `member` WHERE `upid`='".$id."' AND `permissions`=0";
$result = mysql_query($sql);
$output['member'] = array();
if($result){
	while ($row=mysql_fetch_assoc($result)) {
		array_push($output['member'],$row);
	}
}
/*判斷是否有此會員資料*/
if($output['member']!=false){
	$output['status']=true;
}
/*輸出資料為JSON*/
echo json_encode($output,JSON_UNESCAPED_UNICODE);

