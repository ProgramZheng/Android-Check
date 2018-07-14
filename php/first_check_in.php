<?php
include_once("db.php");
$member_id=(!empty($_POST['member_id']))?$_POST['member_id']:$_GET['member_id'];
/*當日日期*/
$now_day=date("Y-m-d");

/*判定當日有無打卡紀錄 START*/
$sql="SELECT `work` FROM `check_history` WHERE `member_id`='".$member_id."'AND `work`=2 AND `add_date` like '".$now_day."%'";
$result = mysql_query($sql);
$check_work=mysql_num_rows($result);
/*判定當日有無打卡紀錄 END*/

echo $check_work;

