<?php
include_once("db.php");
include_once("setting.php");
$output = array();
$id=(!empty($_POST['id']))?$_POST['id']:$_GET['id'];

/*計算當月總時數*/
$sql="SELECT * FROM `check_history` WHERE `member_id`='".$id."' AND `work`=2";
$result=mysql_query($sql);
$history=array();
$output['month_time']=0;
if($result){
	while($row=mysql_fetch_assoc($result)){
		array_push($history,$row);
	}
}
foreach ($history as $key => $value) {
	$output['month_time']+=(int)$value['work_time'];
}

/*取得此員工時薪*/
$sql="SELECT * FROM `member` WHERE `id`='".$id."'";
$result=mysql_query($sql);
$time_money=(int)mysql_fetch_assoc($result)['time_money'];
/*當月總時薪*/
$output['time_money']=(int)($time_money*$output['month_time']);
/*勞保*/
$output['labor_money']=(int)$labor_money;
/*健保*/
$output['health_money']=(int)$health_money;
/*代扣所得稅*/
$output['tax']=(int)($output['time_money']*0.04);
/*總額*/
$output['total']=$output['time_money']-($output['labor_money']+$output['health_money']+$output['tax']);

echo json_encode($output);

