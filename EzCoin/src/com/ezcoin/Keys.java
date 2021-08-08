package com.ezcoin;

public class Keys {
    private int publicKey;
    private int privateKey;
    public static int toAssign = 0;

    //Each user will be given a unique key pair automatically.
    Keys(){
        privateKey = ++toAssign; publicKey = toAssign * -1;
    }
    public void setKeys(int pbK, int prK){ publicKey = pbK; privateKey = prK; }
    public int getPublicKey(){ return publicKey; }
    public int getPrivateKey(){ return privateKey; }
}
