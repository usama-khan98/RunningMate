<?php
include 'DBConfig.php';
if($_SERVER['REQUEST_METHOD']=='POST'){

    $userEmail = $_POST['userEmail'];

    $postQuery=  "select * from tblpost where postBy = '$userEmail'";
    $checkPostQuery =mysqli_query($conn,$postQuery);

    if($checkPostQuery){
        if($row = mysqli_num_rows($checkPostQuery)>0)
        {
            while($record = mysqli_fetch_array($checkPostQuery))
            {
                $tmp[] = $record;
            }            
            $jsonArray['posts'] = $tmp;
            $jsonEncode = json_encode($jsonArray);
            echo $jsonEncode;
        }
        else{
            echo 'no';
        }

    }
}
else{
    echo 'Method Not Found';
}
?>