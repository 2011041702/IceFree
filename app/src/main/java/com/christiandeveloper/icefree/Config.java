package com.christiandeveloper.icefree;


class Config {
    //URL to our login.php file
    private static final String IP = "http://192.168.160.220";
    static final String LOGIN_URL = IP + ":8080/Services/login.php";
    static final String USUARIO = IP + ":8080/Services/usuario.php?email=";
    static final String PRODUCTOS = IP + ":8080/Services/productos.php";
    static final String DETALLE = IP + ":8080/Services/detalle.php?id=";
    static final String PEDIDOS = IP + ":8080/Services/pedido.php";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    static final String KEY_EMAIL = "email";
    static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    static final String EMAIL_SHARED_PREF = "email";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    static final String LOGGEDIN_SHARED_PREF = "loggedin";

    static final String TAG_JSON_ARRAY="result";
    static final String TAG_NAME = "nombre";



    static final String TAG_ID = "id";
    static final String TAG_NOMBRE = "nombre";
    static final String TAG_PRE = "precio";
    static final String TAG_IMG = "imagen";


    //static final String KEY_EMP_ID = "id";
    static final String KEY_EMP_CLI = "cliente";
    static final String KEY_EMP_PROD = "producto";
    static final String KEY_EMP_CANT = "cantidad";
    static final String KEY_EMP_PREC = "precio";
    static final String KEY_EMP_SUB = "subtotal";
}
