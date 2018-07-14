<?php
include_once("db.php");
/*取得系統設定*/
$sql="SELECT * FROM `setting`";
$result = mysql_query($sql);
$setting=mysql_fetch_assoc($result);

$title=$setting['title'];
$labor_money=$setting['labor_money'];
$health_money=$setting['health_money'];

