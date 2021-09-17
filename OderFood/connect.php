<?php
        $hostname = "localhost";
        $username = "root";
        $password ="";
        $databasename = "foody";

        $con = mysqli_connect($hostname, $username, $password, $databasename);
        if($con){
            echo "Ket noi thanh cong";
        }else{
            echo "Loi";
        }
?>