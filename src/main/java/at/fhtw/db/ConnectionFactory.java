package at.fhtw.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.postgresql.Driver;


public class ConnectionFactory {

    public ConnectionFactory() throws IOException {
        try(InputStream inputStream = new FileInputStream("src/main/java/resources/db.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
            this.Url = properties.getProperty("db.url");
            this.User = properties.getProperty("db.user");
            this.Password = properties.getProperty("db.password");
        }catch (IOException e){
            throw new IOException("Error reading properties File",e);
        }
    }
    private String Url;
    private String User;
    private String Password;

    public Connection getConnection(){
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(Url, User, Password);
        }catch (SQLException e){
            throw  new RuntimeException("Error connecting to the database", e);
        }
    }
}
