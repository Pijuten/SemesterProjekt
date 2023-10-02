package at.fhtw.mtcg.user;

import at.fhtw.mtcg.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;


class UserDALTest {

    @Test
    void addUser() throws IOException {
        User user = new User("admin", "admin");
        UserDAL userDAL = new UserDAL();
        assertThrows(DuplicateUserException.class, () -> userDAL.adduser(user));
    }

    @Test
    void addEmptyUsernameUser() throws IOException {

        User user = new User("", "admin");
        UserDAL userDAL = new UserDAL();
        assertThrows(UserDalEmptyFieldException.class, () -> userDAL.adduser(user));
    }

    @Test
    void addEmptyPasswordUser() throws IOException {

        User user = new User("admin", "");
        UserDAL userDAL = new UserDAL();
        assertThrows(UserDalEmptyFieldException.class, () -> userDAL.adduser(user));
    }

    @Test
    void ChangeAndRetrieveUserInfo() throws SQLException, IOException {
        User user = new User("Kienboeck", "me playin...", ":-)");
        user.setUsername("admin");
        UserDAL userDAL = new UserDAL();
        boolean wasSuccessful = userDAL.editUserInfo(user);
        User userInfo = userDAL.getUserInfo(user.getUsername());
        assertTrue(wasSuccessful);
        assertEquals("Kienboeck", userInfo.getDisplayName());
        assertEquals("me playin...", userInfo.getBio());
        assertEquals(":-)", userInfo.getProfileImage());
    }

    @Test
    void ChangeNonExistentUser() throws SQLException, IOException {
        User user = new User("Kienboeck", "me playin...", ":-)");
        user.setUsername("nicht_echter_user");
        UserDAL userDAL = new UserDAL();
        boolean wasSuccessful = userDAL.editUserInfo(user);
        assertFalse(wasSuccessful);
    }
}