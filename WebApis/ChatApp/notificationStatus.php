<?php
 include 'DBConfig.php';
if($_SERVER['REQUEST_METHOD'] =='POST'){

    $notifyId =$_POST['notify_id'];
    $notifyStatus = $_POST['notify_status'];

    $notifyStatusQuery = "update tblnotifications set notify_status = '$notifyStatus' where notify_id = '$notifyId'";

//    $checkNotifyStatusQuery = ;

    if(mysqli_query($conn,$notifyStatusQuery)){
        echo 'ok';
    }
    else{
        echo 'error';
    }

}
else{
    echo 'Method Not Found';
}

?>