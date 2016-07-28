package me.vorps.snowar.databases;

import me.vorps.snowar.Exceptions.SqlException;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;


/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class DatabaseManager {
    private Connection connection;

    public Connection getConnection(){
        return connection;
    }
    /**
     * Connect DataBase
     * @param nameDatabase String
     * @throws SqlException
     */
    public DatabaseManager(String nameDatabase) throws SqlException{
        Encryptor encryptor = new Encryptor(Paths.get( System.getProperty("user.dir")).toString()+"/plugins/SnoWar/.pass");
        try {
            Class.forName("com."+encryptor.getTypeBdd()+".jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:"+encryptor.getTypeBdd()+"://"+encryptor.getIp()+"/"+nameDatabase, encryptor.getUser(), encryptor.getPass());
        } catch(ClassNotFoundException e){
            throw new SqlException("Driver not found Please install driver Mysql", new Throwable("Error, server no connected to database"));
        } catch (SQLException e){
            throw new SqlException("Error, Server not connect to database", new Throwable("Error, server no connected to database"));
        }
    }

    private Object[][] getColumnName(String table) throws SQLException {
        ResultSet result =  this.connection.getMetaData().getColumns(this.connection.getCatalog(), null, table, "%");
        ArrayList<Object[]> tmp = new ArrayList<>();
        while (result.next()) {
            tmp.add(new Object[]{result.getObject(4), result.getObject(5)});
        }
        Object[][] infoColumn = new Object[tmp.size()][2];
        int i = 0;
        for(Object[] objects : tmp){
            infoColumn[i][0] = objects[0];
            infoColumn[i][1] = objects[1];
            i++;
        }
        return infoColumn;
    }

    /**
     * Insert table
     * @param table
     * @param values
     * @throws SqlException
     */
    public void insertTable(String table, Object... values) throws SqlException{
        try {
            Object[][] objects = getColumnName(table);
            String msgName = "";
            String msgValue = "";
            for(Object[] name : objects){
                msgName+= name[0]+",";
                msgValue+="?,";
            }
            PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO "+table+" ("+msgName.substring(0, msgName.length()-1)+") VALUES("+msgValue.substring(0, msgValue.length()-1)+")");
            for(int i = 0; i < objects.length; i++){
                if(values[i] == null){
                    preparedStatement.setNull(i+1, (int) objects[i][1]);
                } else {
                    preparedStatement.setObject(i+1, values[i], (int) objects[i][1]);
                }
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new SqlException("Error, Data not send", new Throwable("Error, server no connected to database"));
        }

    }

    /**
     * Delete value
     * @param table
     * @throws SqlException
     */
    public void delete(String table) throws SqlException{
        try {
            Statement state = this.connection.createStatement();
            state.executeUpdate("DELETE FROM "+table);
            state.close();
        } catch(SQLException err) {
            throw new SqlException("Error, Data not send", new Throwable("Error, server no connected to database"));
        }
    }

    /**
     * Delete value with condition
     * @param table
     * @param condition
     * @throws SqlException
     */
    public void delete(String table, String condition) throws SqlException{
        try {
            Statement state = this.connection.createStatement();
            state.executeUpdate("DELETE FROM "+table+" WHERE "+condition);
            state.close();
        } catch(SQLException err) {
            throw new SqlException("Error, Data not send", new Throwable("Error, server no connected to database"));
        }
    }

    public static class Values{

        private String nameColumn;
        private Object value;

        public Values(String nameColumn, Object value){
            this.nameColumn = nameColumn;
            this.value = value;
        }

        public String getNameColumn(){
            return this.nameColumn;
        }

        public Object getValue(){
            return this.value;
        }
    }

    private int infoColumnType(String table, String column) throws SQLException{
        ResultSet result = this.connection.getMetaData().getColumns(this.connection.getCatalog(), null, table, "%");
        while (result.next()) {
            if(result.getObject(4).equals(column)){
                return (int) result.getObject(5);
            }
        }
        return 0;
    }

    /**
     * Update Table with condition
     * @param table
     * @param condition
     * @param values
     * @throws SqlException
     */
    public void updateTable(String table, String condition, Values... values) throws SqlException{
        try {
            String msg = "";
            for(Values values1 : values){
                msg += values1.nameColumn+" = ?,";
            }
            String cond = "";
            if(condition != null){
                cond = " WHERE "+condition;
            }
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE "+table+" SET "+msg.substring(0, msg.length()-1)+cond);
            int i = 0;
            for(Values values1 : values){
                int type = infoColumnType(table, values1.nameColumn);
                if(values1.value == null){
                    preparedStatement.setNull(i+1, type);
                } else {
                    preparedStatement.setObject(i+1, values1.value, type);
                }
                i++;
            }
            preparedStatement.executeUpdate();
        } catch(SQLException err) {
            throw new SqlException("Error, Data not send", new Throwable("Error, server no connected to database"));
        }
    }

    /**
     * Update Table
     * @param table
     * @param values
     * @throws SqlException
     */
    public void updateTable(String table, Values... values) throws SqlException{
        updateTable(table, null, values);
    }


    /**
     * Return data
     * @param table String
     * @return ResultSet
     * @throws SqlException
     */
    public ResultSet getDataTmp(String table) throws SqlException {
        ResultSet results;
        try {
            results = this.connection.createStatement().executeQuery("SELECT * FROM "+table);
        } catch (SQLException e){
            throw new SqlException("Error, impossible to recover the data", new Throwable("Error, server no connected to database"));
        }
        return results;
    }
    /**
     * Return data
     * @param table String
     * @param condition String
     * @return ResultSet
     * @throws SqlException
     */
    public ResultSet getData(String table, String condition) throws SqlException {
        ResultSet results;
        try {
            results = this.connection.createStatement().executeQuery("SELECT * FROM "+table+" WHERE "+condition);
        } catch (SQLException e){
            throw new SqlException("Error, impossible to recover the data", new Throwable("Error, server no connected to database"));
        }
        return results;
    }

    /**
     * Return data
     * @param table String
     * @param column String
     * @return ResultSet
     * @throws SqlException
     */
    public ResultSet getDataColumn(String table, String column) throws SqlException {
        ResultSet results;
        try {
            results = this.connection.createStatement().executeQuery("SELECT "+column+" FROM "+table);
        } catch (SQLException e){
            throw new SqlException("Error, impossible to recover the data", new Throwable("Error, server no connected to database"));
        }
        return results;
    }

    /**
     * Return data
     * @param table String
     * @param column String
     * @param condition String
     * @return ResultSet
     * @throws SqlException
     */
    public ResultSet getData(String table, String column, String condition) throws SqlException {
        ResultSet results;
        try {
            results = this.connection.createStatement().executeQuery("SELECT "+column+" FROM "+table+" WHERE "+condition);
        } catch (SQLException e){
            throw new SqlException("Error, impossible to recover the data", new Throwable("Error, server no connected to database"));
        }
        return results;
    }

    /**
     * Send data to DataBase
     * @param request String
     * @throws SqlException
     */
    public void sendRequest(String request) throws SqlException {
        try
        {
            Statement state = this.connection.createStatement();
            state.executeUpdate(request);
            state.close();
        } catch(SQLException err) {
            throw new SqlException("Error, Data not send", new Throwable("Error, server no connected to database"));
        }
    }

    public boolean next(ResultSet resultSet) throws SqlException{
        try {
            return resultSet.next();
        } catch (SQLException e){
            throw new SqlException("Error, Data not send", new Throwable("Error, server no connected to database"));
        }
    }

    /**
     * Return data column Type String
     * @param result result
     * @param nameColumn String
     * @return String
     * @throws SqlException
     */
    public String getString(ResultSet result, String nameColumn) throws SqlException {
        String value = "";
        try {
            value = result.getString(nameColumn);
        } catch (SQLException e){
            //
        } catch (NullPointerException e){
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    /**
     * Return data column Type String
     * @param result result
     * @param index int
     * @return String
     * @throws SqlException
     */
    public String getString(ResultSet result, int index) throws SqlException {
        String value = "";
        try {
            value = result.getString(index);
        } catch (SQLException e){
            //
        } catch (NullPointerException e){
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    /**
     * Return data column Type int
     * @param result ResultSet
     * @param index int
     * @return int
     * @throws SqlException
     */
    public int getInt(ResultSet result, int index) throws SqlException {
        int value = 0;
        try {
            value = result.getInt(index);
        } catch (SQLException e){
            //
        } catch (NullPointerException e) {
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    /**
     * Return data column Type int
     * @param result ResultSet
     * @param nameColumn String
     * @return int
     * @throws SqlException
     */
    public int getInt(ResultSet result, String nameColumn) throws SqlException {
        int value = 0;
        try {
            value = result.getInt(nameColumn);
        } catch (SQLException e){
            //
        } catch (NullPointerException e) {
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    /**
     * Return data column Type float
     * @param result ResultSet
     * @param index int
     * @return float
     * @throws SqlException
     */
    public float getFloat(ResultSet result, int index) throws SqlException {
        float value = 0;
        try {
            value = result.getInt(index);
        } catch (SQLException e){
            //
        } catch (NullPointerException e) {
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    /**
     * Return data column Type float
     * @param result ResultSet
     * @param nameColumn String
     * @return float
     * @throws SqlException
     */
    public float getFloat(ResultSet result, String nameColumn) throws SqlException {
        float value = 0;
        try {
            value = result.getInt(nameColumn);
        } catch (SQLException e){
            //
        } catch (NullPointerException e) {
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    /**
     * Return data column Type long
     * @param result ResultSet
     * @param index int
     * @return long
     * @throws SqlException
     */
    public long getLong(ResultSet result, int index) throws SqlException {
        long value = 0;
        try {
            value = result.getLong(index);
        } catch (SQLException e){
            //
        } catch (NullPointerException e) {
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    /**
     * Return data column Type long
     * @param result ResultSet
     * @param nameColumn String
     * @return long
     * @throws SqlException
     */
    public long getLong(ResultSet result, String nameColumn) throws SqlException {
        long value = 0;
        try {
            value = result.getLong(nameColumn);
        } catch (SQLException e){
            //
        } catch (NullPointerException e) {
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    /**
     * Return data column Type double
     * @param result ResultSet
     * @param index int
     * @return double
     * @throws SqlException
     */
    public double getDouble(ResultSet result, int index) throws SqlException {
        double value = 0.0;
        try {
            value = result.getDouble(index);
        } catch (SQLException e){
            //
        } catch (NullPointerException e){
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    /**
     * Return data column Type double
     * @param result ResultSet
     * @param nameColumn String
     * @return double
     * @throws SqlException
     */
    public double getDouble(ResultSet result, String nameColumn) throws SqlException {
        double value = 0.0;
        try {
            value = result.getDouble(nameColumn);
        } catch (SQLException e){
            //
        } catch (NullPointerException e){
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    /**
     * Return data column Type boolean
     * @param result ResultSet
     * @param index int
     * @return boolean
     * @throws SqlException
     */
    public boolean getBoolean(ResultSet result, int index) throws SqlException {
        boolean value = false;
        try {
            value = result.getBoolean(index);
        } catch (SQLException e){
            //
        } catch (NullPointerException e){
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    /**
     * Return data column Type boolean
     * @param result ResultSet
     * @param nameColumn String
     * @return boolean
     * @throws SqlException
     */
    public boolean getBoolean(ResultSet result, String nameColumn) throws SqlException {
        boolean value = false;
        try {
            value = result.getBoolean(nameColumn);
        } catch (SQLException e){
            //
        } catch (NullPointerException e){
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    /**
     * Return data column Type Timestamp
     * @param result ResultSet
     * @param index int
     * @return Timestamp
     * @throws SqlException
     */
    public Timestamp getTimestamp(ResultSet result, int index) throws SqlException {
        Timestamp value = null;
        try {
            value = result.getTimestamp(index);
        } catch (SQLException e){
            //
        } catch (NullPointerException e){
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    /**
     * Return data column Type Timestamp
     * @param result ResultSet
     * @param nameColumn String
     * @return Timestamp
     * @throws SqlException
     */
    public Timestamp getTimestamp(ResultSet result, String nameColumn) throws SqlException {
        Timestamp value = null;
        try {
            value = result.getTimestamp(nameColumn);
        } catch (SQLException e){
            //
        } catch (NullPointerException e){
            throw new SqlException("Error, Column", new Throwable("Column does not exist"));
        }
        return value;
    }

    public void closeDataBase(){
        try {
            this.connection.close();
        } catch (SQLException e){
            //
        }
    }
}
