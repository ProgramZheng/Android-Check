<?php
include_once("db.php");
$output=array();
$id=(!empty($_POST['id']))?$_POST['id']:$_GET['id'];
/*撈取目前會員資料*/
$sql="SELECT * FROM `member` WHERE `id`='".$id."'";
$result = mysql_query($sql);
$output['member']=mysql_fetch_assoc($result);

if($output['member']!=false){
	$output['status']=true;
}
/*撈取部門資料*/
$department_id=$output['member']["department_id"];
$sql=$sql="SELECT * FROM `department` WHERE `id`='".$department_id."'";
$result = mysql_query($sql);
$output['department']=mysql_fetch_assoc($result);

/*此會員打卡紀錄總筆數*/
$sql="SELECT count(*) AS number FROM `check_history` WHERE `member_id`='".$id."' ORDER BY `add_date` DESC";
$result = mysql_query($sql);
$member_check_number=(int)mysql_fetch_assoc($result)['number'];
	$sql="SELECT * FROM `check_history` WHERE `member_id`='".$id."' AND date_format(`add_date`,'%Y-%m')=date_format(now(),'%Y-%m') ORDER BY `add_date` DESC";
	$result = mysql_query($sql);
	$output['now_month_data']=array();
	if($result){
		while ($row=mysql_fetch_assoc($result)) {
			array_push($output['now_month_data'],$row);
		}
	}

/*輸出資料為JSON*/
echo json_encode($output);