<?php
include 'DBConfig.php';
if($_SERVER['REQUEST_METHOD']=='POST')
    {

        $upload_path = 'uploads/userImage/'; 

        $userName = $_POST['userName'];
        $userEmail = $_POST['useEmail'];
        $userPassword = md5($_POST['usePassword']);
        $userAge = (int) $_POST['userAge'];
        $userGender = $_POST['userGender'];
        $userImage = $_POST['userImage'];
        $userDeviceToken = $_POST['userDeviceToken'];
        
        $upload_url = $upload_path.time().".jpg";
        $upload_path = $upload_path.time().".jpg";
        $validateEmail = "select * from tblloginauth where authEmail = '$userEmail' ";

        $validateQuery = mysqli_query($conn,$validateEmail);

        if(mysqli_num_rows($validateQuery)>0)
        {
            echo 'exists';
        }
        else{

            if(file_put_contents($upload_path,base64_decode($userImage)))
            {
                 $userQuery = "insert into tblusers values ('','$userName','$userEmail','$userPassword'
                 ,'$userAge','$userGender','$upload_url','$userDeviceToken')";
                 
                 $authQuery = "insert into tblloginauth values ('','$userEmail','$userPassword','')";

                 $checkUserQuery = mysqli_query($conn,$userQuery);
                 $checkAuthQuery = mysqli_query($conn,$authQuery);

                 if($checkUserQuery && $checkAuthQuery){
                        echo 'done';
                 }
                 else
                 {
                     echo 'failed';
                 }
            }
         else
            {
                echo 'error';
            }
       }
}
    else{
        echo 'Method Not Found';
    }
    ?>