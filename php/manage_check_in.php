<?php
include_once("db.php");
$output=array();
$select_member_id=(!empty($_POST['select_member_id']))?$_POST['select_member_id']:$_GET['select_member_id'];
$select_month=(!empty($_POST['select_month']))?$_POST['select_month']:"%m";
/*此會員打卡紀錄總筆數*/
$sql="SELECT count(*) AS number FROM `check_history` WHERE `member_id`='".$select_member_id."' ORDER BY `add_date` DESC";
$result = mysql_query($sql);
$member_check_number=(int)mysql_fetch_assoc($result)['number'];
	$sql="SELECT * FROM `check_history` WHERE `member_id`='".$select_member_id."' AND date_format(`add_date`,'%Y-%m')=date_format(now(),'%Y-".$select_month."') ORDER BY `add_date` DESC";
	$result = mysql_query($sql);
	$output['now_month_data']=array();
	if($result){
		while ($row=mysql_fetch_assoc($result)) {
			array_push($output['now_month_data'],$row);
		}
	}
/*輸出資料為JSON*/
echo json_encode($output);