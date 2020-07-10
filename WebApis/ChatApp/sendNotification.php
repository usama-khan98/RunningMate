<?php
include 'DBConfig.php';
if($_SERVER['REQUEST_METHOD']=='POST'){

    $postBy=$_POST['postBy'];
    $startAge = $_POST['startAge'];
    $endAge = $_POST['endAge'];
    $genderPref= $_POST['genderPref'];


    $selectUsers = "select userEmail from tblusers where userAge between '$startAge' and '$endAge' and userGender='$genderPref'
     and userEmail != '$postBy'";

    $checkSelectedUser = mysqli_query($conn,$selectUsers);
    if($row = mysqli_num_rows($checkSelectedUser)>0)
    {
        while($fetch = mysqli_fetch_array($checkSelectedUser))
        {

     $notificationQuery = "insert into tblnotifications values (null,'$postBy','$fetch[0]','Chat App','New Message From $postBy ','0')";
    $checkNotificationQuery = mysqli_query($conn,$notificationQuery);
           

} if($checkNotificationQuery)
{
    echo 'done';
}
else{
    echo 'error';
}


    }
    else{
        echo 'no';
    }
}
else
{
   echo 'Method Not Found';
}
?>