package at.fhtw.db;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionFactoryTest  {
    @Test
    void TestIfConnectionOpen() throws SQLException, IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        assertFalse(connection.isClosed());
        connection.close();
    }

}