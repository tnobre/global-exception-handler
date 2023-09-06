package com.tn.app.validation.patterns;

public class Patterns {

    public static final String EMAIL = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public static final String VALID_NAME = "^[a-zA-Z\\u00C0-\\u00FF\\s\\-]+";
    public static final String ZIP = "\\d{5}";
    public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\u0021-\\u002F]|.*[\\u003A-\\u0040]|.*[\\u005B-\\u0060]|.*[\\u007B-\\u007E]|.*[\\u00A1-\\u00FF]).{8,50}$";
    
}