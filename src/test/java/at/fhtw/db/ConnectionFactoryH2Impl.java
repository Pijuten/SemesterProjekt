package at.fhtw.db;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.Test;
import org.postgresql.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ConnectionFactoryH2Impl implements ConnectionFactory {
    public ConnectionFactoryH2Impl() throws IOException {
        try(InputStream inputStream = new FileInputStream("src/test/resources/h2.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
            this.Url = properties.getProperty("db.url");
            this.User = properties.getProperty("db.user");
            this.Password = properties.getProperty("db.password");
        InputStream schemaInputStream = new FileInputStream("src/test/resources/h2.sql");
        RunScript.execute(getConnection(), new InputStreamReader(schemaInputStream));
        }catch (IOException e){
            throw new IOException("Error reading properties File",e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private String Url;
    private String User;
    private String Password;

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
    @Test
    void TestIfConnectionOpen() throws SQLException, IOException {
        Connection connection = getConnection();
        assertFalse(connection.isClosed());
        connection.close();
    }
}
