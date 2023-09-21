package at.fhtw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class ConnectionFactory {
    private String Url = "jdbc:postgresql://172.17.0.2:5432/postgres";
    private String User = "swen1user";
    private String Password = "swen1psw";

    public Connection getConnection(){
        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(Url, User, Password);
            return connection;
        }catch (SQLException e){
            throw  new RuntimeException("Error connecting to the database", e);
        }
    }
}
