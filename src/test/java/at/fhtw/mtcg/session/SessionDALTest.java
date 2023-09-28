package at.fhtw.mtcg.session;

import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SessionDALTest {

    @Test
    void loginUserCorrectPassword() throws SQLException, IOException {
        User user = new User("admin","admin");
        SessionDAL sessionDAL = new SessionDAL();
        sessionDAL.loginUser(user);
        assertEquals("admin-mtcgToken",user.getToken());
    }
    @Test
    void loginUserWrongPassword() throws SQLException, IOException{
        User user = new User("admin","wrongPassword");
        SessionDAL sessionDAL = new SessionDAL();
        sessionDAL.loginUser(user);
        assertNull(user.getToken());

    }
    @Test
    void loginUserTokenDb() throws SQLException, IOException {
        User user = new User("admin","admin");
        SessionDAL sessionDAL = new SessionDAL();
        sessionDAL.loginUser(user);
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT token from userdata WHERE username=?");
        preparedStatement.setString(1,user.getUsername());
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        assertEquals(rs.getString(1),"admin-mtcgToken");
    }
}