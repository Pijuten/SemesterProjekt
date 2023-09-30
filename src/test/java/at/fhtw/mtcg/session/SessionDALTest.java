package at.fhtw.mtcg.session;

import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SessionDALTest {

    @Test
    void loginUserCorrectPassword() throws SQLException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        User user = new User("admin","admin");
        SessionDAL sessionDAL = new SessionDAL();
        sessionDAL.loginUser(user);
        assertEquals("admin-mtcgToken",user.getToken());
    }
    @Test
    void loginUserWrongPassword() throws SQLException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        User user = new User("admin","wrongPassword");
        SessionDAL sessionDAL = new SessionDAL();
        sessionDAL.loginUser(user);
        assertNull(user.getToken());

    }
    @Test
    void loginUserTokenDb() throws SQLException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
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
    @Test
    void validateTokenTest() throws SQLException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        User userLog = new User("admin","admin");
        String AuthToken="admin-mtcgToken";
        SessionDAL sessionDAL = new SessionDAL();
        sessionDAL.loginUser(userLog);
        User user = sessionDAL.validateToken(AuthToken);
        System.out.println();
        assertNotEquals(user,null);
        assertEquals(user.getUsername(),"admin");
    }
    @Test
    void validateWrongTokenTest() throws  SQLException, IOException{

        String AuthToken="admin-mtcgToke";
        SessionDAL sessionDAL = new SessionDAL();
        User user = sessionDAL.validateToken(AuthToken);
        assertNull(user);
    }
    @Test
    void validateNullTokenTest()throws  SQLException, IOException{

        String AuthToken=null;
        SessionDAL sessionDAL = new SessionDAL();
        User user = sessionDAL.validateToken(AuthToken);
        assertNull(user);
    }
}