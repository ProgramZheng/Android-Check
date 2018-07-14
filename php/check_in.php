<?php
include_once("db.php");
$member_id=(!empty($_POST['member_id']))?$_POST['member_id']:$_GET['member_id'];
$flag=(!empty($_POST['flag']))?$_POST['flag']:$_GET['flag'];
$output=array();
/*當日日期*/
$now_day=date("Y-m-d");
/*當時時間*/
$now_time=date("Y-m-d H:i:s");

/*判定為何種模式(GPS,Wi-Fi)*/
switch ($flag) {
	case "0":
		$check_status="error";
	break;
	case "1":
		$check_status="GPS";
	break;
	case '2':
		$check_status="Wi-Fi";
	break;
}

//先搜尋有沒有打過卡，在搜尋打過上班卡沒
/*判定當日有無打卡紀錄 START*/
$sql="SELECT `work` FROM `check_history` WHERE `member_id`='".$member_id."' AND `add_date` like '".$now_day."%'";
$result = mysql_query($sql);
$output['check_work']=(int)mysql_fetch_assoc($result);
/*判定當日有無打卡紀錄 END*/

/*判斷打卡 START*/
if($output['check_work']==1){
	/*取出打卡紀錄計算時數*/
	$sql="SELECT * FROM `check_history` WHERE `member_id`='".$member_id."' AND `add_date` like '".$now_day."%'";
	$result = mysql_query($sql);
	$on_work = mysql_fetch_assoc($result);
	$work = $on_work['work'];
	if($work!=2){
		/*strtotime單位為秒*/
		$work_on_time = strtotime($on_work['work_on_time']);
		$work_off_time = strtotime($now_time);
		$today_work_time=(int)floor(($work_off_time-$work_on_time)/(60*60));
		/*更改原本上班卡資料為下班卡*/
		$sql="UPDATE `check_history` SET `status_off`='".$check_status."' , `work`=2 , `work_off_time`='".$now_time."' , `work_time`='".$today_work_time."', `upd_date`='".$now_time."' WHERE `member_id`='".$member_id."' AND `add_date` like '".$now_day."%' ";
		$result = mysql_query($sql);
	}
}
/*如果沒打過卡*/
else if($output['check_work']==0){
	$sql="INSERT INTO `check_history`(id,member_id,status_on,status_off,work,work_on_time,work_off_time,work_time,work_day,add_date,upd_date) VALUES (0,'".$member_id."','".$check_status."',0,1,'".$now_time."','1900-01-01',0,'".$now_day."','".$now_time."','".$now_time."')";
	$result = mysql_query($sql);
}
/*判斷打卡 END*/
/*JSON_UNESCAPED_UNICODE是將JSON中文編碼的亂碼改為正常*/
echo json_encode($output,JSON_UNESCAPED_UNICODE);
