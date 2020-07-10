<?php

include 'DBConfig.php';

if($_SERVER['REQUEST_METHOD']=='POST')
{
$user = $_POST['userEmail'];

$getChatsQuery = "select DISTINCT(node_name) from tblnodes where node_by = '$user' or node_to = '$user'";

$checkChatsQuery = mysqli_query($conn,$getChatsQuery);

if(mysqli_num_rows($checkChatsQuery)>0){
    while($row = mysqli_fetch_array($checkChatsQuery)){
        $tmp[] = $row;
    }
    $jsonArray['nodes'] = $tmp;
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