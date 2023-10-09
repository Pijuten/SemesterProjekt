package at.fhtw.db;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionFactoryImplTest {
    @Test
    void TestIfConnectionOpen() throws SQLException, IOException {
        ConnectionFactoryImpl connectionFactoryImpl = new ConnectionFactoryImpl();
        Connection connection = connectionFactoryImpl.getConnection();
        assertFalse(connection.isClosed());
        connection.close();
    }

}