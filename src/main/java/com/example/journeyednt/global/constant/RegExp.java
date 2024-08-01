package com.example.journeyednt.global.constant;

public class RegExp {
    public static final String PASSWORD_REGEXP =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!~$%^&-+=()])(?=\\S+$).{8,16}$";
    public static final String ACCOUNT_ID_REGEXP = "^[A-Za-z0-9]{5,20}$";
}
