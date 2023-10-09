package at.fhtw.mtcg.session;

import at.fhtw.db.ConnectionFactoryH2Impl;
import at.fhtw.mtcg.models.User;
import at.fhtw.mtcg.user.DuplicateUserException;
import at.fhtw.mtcg.user.UserDAL;
import at.fhtw.mtcg.user.UserDalEmptyFieldException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SessionDALTest {

    static private ConnectionFactoryH2Impl connectionFactoryH2;
    @BeforeAll
    static public void setup() throws UserDalEmptyFieldException, DuplicateUserException, IOException {
        connectionFactoryH2 = new ConnectionFactoryH2Impl();
        User user = new User("admin", "admin");
        UserDAL userDAL = new UserDAL(connectionFactoryH2);
        userDAL.adduser(user);
    }
    @Test
    void loginUserCorrectPassword() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        User user = new User("admin","admin");
        SessionDAL sessionDAL = new SessionDAL(connectionFactoryH2);
        sessionDAL.loginUser(user);
        assertEquals("admin-mtcgToken",user.getToken());
    }
    @Test
    void loginUserWrongPassword() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        User user = new User("admin","wrongPassword");
        SessionDAL sessionDAL = new SessionDAL(connectionFactoryH2);
        sessionDAL.loginUser(user);
        assertNull(user.getToken());

    }
    @Test
    void validateTokenTest() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        User userLog = new User("admin","admin");
        String AuthToken="admin-mtcgToken";
        SessionDAL sessionDAL = new SessionDAL(connectionFactoryH2);
        sessionDAL.loginUser(userLog);
        User user = sessionDAL.validateToken(AuthToken);
        System.out.println();
        assertNotEquals(user,null);
        assertEquals(user.getUsername(),"admin");
        assertEquals(user.getToken(),"admin-mtcgToken");
    }
    @Test
    void validateWrongTokenTest() throws  SQLException{

        String AuthToken="admin-mtcgToke";
        SessionDAL sessionDAL = new SessionDAL(connectionFactoryH2);
        User user = sessionDAL.validateToken(AuthToken);
        assertNull(user);
    }
    @Test
    void validateNullTokenTest()throws  SQLException{

        String AuthToken=null;
        SessionDAL sessionDAL = new SessionDAL(connectionFactoryH2);
        User user = sessionDAL.validateToken(AuthToken);
        assertNull(user);
    }
}