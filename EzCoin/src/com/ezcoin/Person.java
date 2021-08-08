package com.ezcoin;

public class Person {
    float wallet;
    String name;

    Person(float f, String n){
        wallet = f; name = n;
    }
    public void changeBalance(float amount){ wallet += amount; }
    public void setBalance(float amount){ wallet = amount; }
    public void setName (String name){ this.name = name; }

}
