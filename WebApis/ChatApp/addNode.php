<?php
include 'DBConfig.php';
if($_SERVER['REQUEST_METHOD'] == 'POST')
{

    $by = $_POST['getBy'];
    $to = $_POST['getTo'];

    $nodeName = $_POST['nodeName'];



    $addNodeQuery = "insert into tblnodes values (null,'$nodeName','$by','$to')";

    $checkAddNode = mysqli_query($conn,$addNodeQuery);

    if($checkAddNode){
        echo 'ok';
    }
    else{
        echo 'failed';
    }

}
else
{
    echo 'Method Not Found';
}


?>