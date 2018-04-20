<?php
// process.php

$errors         = array();      // array to hold validation errors
$data           = array();      // array to pass back data

// validate the variables ======================================================
    // if any of these variables don't exist, add an error to our $errors array

    if (empty($_POST['email']))
        $errors['email'] = 'Email is required.';
    
    if (empty($_POST['username']))
        $errors['email'] = 'Username is required.';

    if (empty($_POST['password']))
        $errors['email'] = 'Password is required.';

// return a response ===========================================================

    // if there are any errors in our errors array, return a success boolean of false
    if ( ! empty($errors)) {

        // if there are items in our errors array, return those errors
        $data['success'] = false;
        $data['errors']  = $errors;
    } else {

        // if there are no errors process our form, then return a message

        // DO ALL YOUR FORM PROCESSING HERE
        // THIS CAN BE WHATEVER YOU WANT TO DO (LOGIN, SAVE, UPDATE, WHATEVER)
        $db = pg_connect("host=localhost port=5432 dbname=new_monopoly user=isaacperalez password=\"\"");
        $query = "INSERT INTO accounts VALUES ('1','$_POST[email]','false','$_POST[password]','$_POST[username]')";
        $result = pg_query($query); 

        // show a message of success and provide a true success variable
        $data['success'] = true;
        $data['message'] = 'Success!';
    }

    // // return all our data to an AJAX call
    // echo json_encode($data);
?>
