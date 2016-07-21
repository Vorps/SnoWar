package me.vorps.snowar.databases;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class Crypteur {

    private String typeBdd;
    private String ip;
    private String user;
    private String pass;


    public Crypteur(){
        typeBdd = "mysql";
        ip = "localhost";
        user = "root";
        pass = "";
    }

    public String getTypeBdd(){
        return typeBdd;
    }

    public String getIp(){
        return ip;
    }

    public String getUser(){
        return user;
    }

    public String getPass(){
        return pass;
    }

}
