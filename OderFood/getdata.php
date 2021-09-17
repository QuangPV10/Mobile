<?php
 $connect = mysqli_connect("localhost","root", "", "foody");
 $query = "SELECT * FROM nhanvien";
 $data = mysqli_query($connect, $query);
 while($row = mysqli_fetch_assoc($data)){
     echo $row['manv'];
 }
?>