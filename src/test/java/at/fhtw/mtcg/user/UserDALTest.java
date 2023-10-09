package at.fhtw.mtcg.user;

import at.fhtw.db.ConnectionFactoryH2Impl;
import at.fhtw.db.ConnectionFactoryImpl;
import at.fhtw.mtcg.models.User;
import at.fhtw.mtcg.session.SessionDAL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;


class UserDALTest {
    private static ConnectionFactoryH2Impl connectionFactoryH2;

    @BeforeAll
    public static void setup() throws IOException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException, UserDalEmptyFieldException, DuplicateUserException {
        connectionFactoryH2 = new ConnectionFactoryH2Impl();
        User user = new User("admin", "admin");
        UserDAL userDAL = new UserDAL(connectionFactoryH2);
        userDAL.adduser(user);
        SessionDAL sessionDAL = new SessionDAL(connectionFactoryH2);
        user = sessionDAL.loginUser(user);
        assertEquals("admin",user.getUsername());
        assertEquals("admin",user.getPassword());
    }
    @Test
    void addDuplicateUser() throws IOException, UserDalEmptyFieldException, DuplicateUserException {
        User user = new User("admin", "admin");
        UserDAL userDAL = new UserDAL(connectionFactoryH2);
        assertThrows(DuplicateUserException.class,()->userDAL.adduser(user));
    }
    @Test
    void ChangeAndRetrieveUserInfo() throws SQLException, IOException, UserDalEmptyFieldException, DuplicateUserException {
        User userEdit = new User("Kienboeck", "me playin...", ":-)");
        userEdit.setUsername("admin");
        UserDAL userDAL = new UserDAL(connectionFactoryH2);
        userDAL.editUserInfo(userEdit);
        User userInfo = userDAL.getUserInfo(userEdit.getUsername());
        assertEquals("Kienboeck", userInfo.getDisplayName());
        assertEquals("me playin...", userInfo.getBio());
        assertEquals(":-)", userInfo.getProfileImage());
    }

    @Test
    void ChangeNonExistentUser() throws SQLException, IOException {
        User user = new User("Kienboeck", "me playin...", ":-)");
        user.setUsername("nicht_echter_user");
        UserDAL userDAL = new UserDAL(connectionFactoryH2);
        userDAL.editUserInfo(user);
        User userInfo = userDAL.getUserInfo(user.getUsername());
        assertNull(userInfo);
    }

}