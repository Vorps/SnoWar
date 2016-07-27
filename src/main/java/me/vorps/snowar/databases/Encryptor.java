package me.vorps.snowar.databases;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class Encryptor {

    private String path;
    private int key;
    private @Getter String typeBdd;
    private @Getter String ip;
    private @Getter String user;
    private @Getter String pass;

    public Encryptor(String path, int key){
        this.path = path;
        this.key = key;
        this.init();
    }

    private ArrayList<String> decrypt() {
        ArrayList<String> data = File.systemRead(path);
        String var1 = "";
        String var2 = "";
        ArrayList<String> var3 = new ArrayList<>();
        for(String dataValue : data){
            for(int i = 0; i < dataValue.length(); i++){
                if (dataValue.charAt(i) == '@') {
                    var2+=Character.toString ((char) (Integer.parseInt(var1)/this.key));
                    var1 = "";
                } else {
                    if(dataValue.charAt(i) != 'n'){
                        var1+=dataValue.charAt(i);
                    } else {
                        var3.add(var2);
                        var2="";
                    }
                }
            }
        }
        return var3;
    }

    private HashMap<String, String> decryptDico(){
        HashMap<String, String> data = new HashMap<>();
        String var2;
        String var3;
        for(String var1 : decrypt()){
            var2 = "";
            var3 = "";
            for(int i = 0; i < var1.length(); i++){
                if(var1.charAt(i) == '='){
                    var3=var2;
                    var2="";
                } else {
                    var2+=var1.charAt(i);
                }
            }
            data.put(var3, var2);
        }
        return data;
    }

    private void init(){
        HashMap<String, String> data = decryptDico();
        this.typeBdd = data.get("bdd");
        this.ip = data.get("ip");
        this.user = data.get("user");
        this.pass = data.get("pass");
    }

}
