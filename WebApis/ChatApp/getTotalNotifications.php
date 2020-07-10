<?php
include 'DBConfig.php';
if($_SERVER['REQUEST_METHOD'] =='POST'){

    $loggedInUser = $_POST['getUserEmail'];

    $fetchNotificationQuery = "select notify_by from tblnotifications where notify_to = '$loggedInUser' and notify_status=0";
        
    $fetchQueryCheck = mysqli_query($conn,$fetchNotificationQuery);


    if($rowCount = mysqli_num_rows($fetchQueryCheck)>0){
            while($getUser = mysqli_fetch_array($fetchQueryCheck))
            {
                $tmp[] = $getUser;
            }


            $jsonArray['notificationBy'] = $tmp;
            $jsonEncode = json_encode($jsonArray);
            echo $jsonEncode;
    }
        else{
    echo 'no';
    }

}
else{
    echo 'Method Not Found';
}



?>