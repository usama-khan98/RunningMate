<?php
 include 'DBConfig.php';
if($_SERVER['REQUEST_METHOD'] =='POST'){

    $loggedInUser = $_POST['getUserEmail'];

    $fetchNotificationQuery = "select * from tblnotifications where notify_to = '$loggedInUser' and notify_status=0";

    $fetchQueryCheck = mysqli_query($conn,$fetchNotificationQuery);

    if($rowCount = mysqli_num_rows($fetchQueryCheck)>0){

  
 
        while($data = mysqli_fetch_array($fetchQueryCheck))
        {
            $tmp[] = $data;
        }
        
            $jsonArray['notifications'] =$tmp;
  //          $totalNum['count'] = sizeof($tmp);

//            array_push($jsonArray['notifications'],$totalNum);
            $encode = json_encode($jsonArray);
            echo $encode;
}
    else{
    echo 'no';
    }

}
else{
    echo 'Method Not Found';
}

?>