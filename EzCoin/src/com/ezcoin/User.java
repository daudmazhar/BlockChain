package com.ezcoin;

public class User extends Person{
    public Keys keys;
    int status;

    User(float f, String n) {
        super(f, n);
        keys = new Keys();
        status = 0;
    }
}
