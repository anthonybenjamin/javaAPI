<?php
/*
  This php page for internal testing only.
  assumes that we will be receiving json or string
*/
   if (strtoupper($_SERVER['REQUEST_METHOD']) != 'POST') {
    throw new Exception('Only POST requests are allowed');
  } 
  
  // Read the input stream
  $body = file_get_contents("php://input");

  $object = json_decode($body, true);
   
  // Throw an exception if decoding failed
  if (!is_array($object)) {
    throw new Exception('Failed to decode JSON object');
  }
//re-encode for sending
  $objectJson = json_encode($object);
  
  // Display the object
  echo $objectJson;
?>