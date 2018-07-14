<?php
include_once("db.php");
/*App傳入資料*/
$id=(!empty($_POST['id']))?$_POST['id']:$_GET['id'];
$old_password=(!empty($_POST['old_password']))?$_POST['old_password']:$_GET['old_password'];
$new_password=(!empty($_POST['new_password']))?$_POST['new_password']:$_GET['new_password'];
$new_password_check=(!empty($_POST['new_password_check']))?$_POST['new_password_check']:$_GET['new_password_check'];
/*判斷舊密碼是否存在*/
$sql="SELECT * FROM `member` WHERE `id`='".$id."' AND `password`='".$old_password."'";
$result = mysql_query($sql);
$check_old_password=mysql_num_rows($result);

if($check_old_password==1){
	if(preg_match("/(".$new_password.")/",$new_password_check)){
		$sql="UPDATE `member` SET `password`='".$new_password."' WHERE `id`='".$id."'";
		mysql_query($sql);
		echo "修改密碼成功";
	}
	else{
		echo "新密碼與確認新密碼不同";
	}
}
else{
	echo "舊密碼錯誤，請確認密碼";
}