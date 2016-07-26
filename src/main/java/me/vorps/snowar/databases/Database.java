package me.vorps.snowar.databases;

import me.vorps.snowar.Exceptions.SqlException;

import java.sql.SQLException;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public enum Database {
    SNOWAR("bd_snowar");

    private DatabaseManager database;
    private String nameDataBase;

    public DatabaseManager getDatabase(){
        return this.database;
    }

    Database(String nameDataBase){
        this.nameDataBase = nameDataBase;
        try {
            this.database = new DatabaseManager(nameDataBase);
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    /**
     * Test connection DataBase
     */
    public void tryConnectionDatabase(){
        try {
            this.database.sendRequest("SHOW tables");
        } catch (Exception e) {
            try {
                this.database.getConnection().close();
                this.database = new DatabaseManager(this.nameDataBase);
            } catch (SqlException | SQLException err){
                err.printStackTrace();
            }
        }
    }
}