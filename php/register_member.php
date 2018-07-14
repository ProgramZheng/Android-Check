<?php
include_once("db.php");
include_once("setting.php");
/*App傳入資料*/
/*此id為主管id*/
$id=(!empty($_POST['id']))?$_POST['id']:$_GET['id'];
$email=(!empty($_POST['email']))?$_POST['email']:$_GET['email'];
$name=(!empty($_POST['name']))?$_POST['name']:$_GET['name'];
$time_money=(!empty($_POST['time_money']))?$_POST['time_money']:$_GET['time_money'];

/*撈取目前登入主管資料*/
$sql="SELECT * FROM `member` WHERE `id`='".$id."'";
$result = mysql_query($sql);
$member=mysql_fetch_assoc($result);

/*主管部門id*/
$department_id=$member['department_id'];

/*此部門總共有幾筆會員資料*/
$sql="SELECT count(*) as count FROM `member` WHERE `department_id`='".$department_id."'";
$result = mysql_query($sql);
$department_count=mysql_fetch_assoc($result);
$department_count=$department_count['count'];

/*撈取部門資料*/
$sql="SELECT * FROM `department` WHERE `id`='".$department_id."'";
$result = mysql_query($sql);
$department=mysql_fetch_assoc($result);
/*部門名稱*/
$department_name=$department['name'];
/*產生固定會員流水號(部門代號+000+此部門總筆數)*/
$designation=$department['designation'];
$username=$designation.str_pad($department_count,4,"0",STR_PAD_LEFT);
/*產生亂數密碼*/
$password=substr(sha1(uniqid()),0,6);

/*新增員工資料*/
$sql="INSERT INTO `member`(id,upid,email,username,password,permissions,department_id,name,time_money,add_date,upd_date) VALUES (0,".$id.",
'".$email."','".$username."','".$password."',0,".$department_id.",'".$name."',".(int)$time_money.",'".date("Y-m-d H:i:s")."','".date("Y-m-d H:i:s")."')";
$result = mysql_query($sql);

/*寄送註冊信件*/
$header = 'MIME-Version: 1.0' . "\r\n" . 'Content-type: text/html; charset=UTF-8' . "\r\n"; 
$subject=$title.'註冊完成通知信';
$content=$department_name.'的'.$name.'您好:<br/>';
$content.='您在'.$title.'已完成員工帳號註冊<br/>';
$content.='以下是你帳號密碼資訊，請妥善保管並<font color="red">盡快更改密碼</font>，勿聽信他人給予以下資料。<br/>';
$content.='<table border="3"><tr><th>部門';
$content.='<td align="center">'.$department_name.'</td>';
$content.='</th></tr><tr><th>帳號';
$content.='<td align="center">'.$username.'</td>';
$content.='</th></tr><tr><th>密碼';
$content.='<td align="center">'.$password.'</td>';
$content.='</th></tr></table>';
$content.='Form:'.$title.'';
mail($email, $subject, $content, $header);
echo "員工：".$name."註冊完成";