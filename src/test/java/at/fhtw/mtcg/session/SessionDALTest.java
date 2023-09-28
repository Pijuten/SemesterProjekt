package at.fhtw.mtcg.session;

import at.fhtw.mtcg.models.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
}