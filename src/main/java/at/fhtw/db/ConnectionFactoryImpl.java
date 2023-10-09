package at.fhtw.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.postgresql.Driver;


public class ConnectionFactoryImpl implements ConnectionFactory {

    public ConnectionFactoryImpl() throws IOException {
        try(InputStream inputStream = new FileInputStream("src/main/resources/db.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
            this.Url = properties.getProperty("db.url");
            this.User = properties.getProperty("db.user");
            this.Password = properties.getProperty("db.password");
        }catch (IOException e){
            throw new IOException("Error reading properties File",e);
        }
    }
    private final String Url;
    private final String User;
    private final String Password;

    public Connection getConnection(){
        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(Url, User, Password);
            connection.setAutoCommit(false);
            return connection;
        }catch (SQLException e){
            throw  new RuntimeException("Error connecting to the database", e);
        }
    }
}
