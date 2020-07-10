<?php
include 'DBConfig.php';
if($_SERVER['REQUEST_METHOD']=='POST'){

    $userEmail = $_POST['userEmail'];
    $userPassword = md5($_POST['userPassword']);

    $validateUser = "select * from tblloginauth where authEmail = '$userEmail' and authPassword = '$userPassword' ";

    $checkQuery = mysqli_query($conn,$validateUser);

    if(mysqli_num_rows($checkQuery)>0)
    {
            echo 'ok';
    }
    else{
        echo 'failed';
    }


}
else{
    echo 'Method Not Found';

}
    
?>