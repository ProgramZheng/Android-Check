<?php 
include_once("db.php");
$output=array();
$username=(!empty($_POST['username']))?$_POST['username']:$_GET['username'];
$password=(!empty($_POST['password']))?$_POST['password']:$_GET['password'];
/*撈取會員資料*/
$sql="SELECT * FROM `member` WHERE `username`='".$username."' AND `password`='".$password."'";
$result = mysql_query($sql);
$output['member']=mysql_fetch_assoc($result);
/*判斷是否有此會員資料*/
if($output['member']!=false){
	$output['status']=true;
}
/*輸出資料為JSON*/
echo json_encode($output);