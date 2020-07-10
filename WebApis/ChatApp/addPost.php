<?php
include 'DBConfig.php';
if($_SERVER['REQUEST_METHOD']=='POST'){

    $postBy = $_POST['postBy'];
    $postTitle =$_POST['postTitle'];
    $postLocation = $_POST['postLocation'];
    $postGender = $_POST['postGender'];
    $postAge =$_POST['postAge'];
    $postDate = date("Y-m-d h:i:sa");

    $postQuery = "insert into tblpost values (null,'$postBy','$postTitle','$postLocation','$postGender','$postAge','$postDate')";

    $checkPostQuery = mysqli_query($conn,$postQuery);

    if($checkPostQuery)
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