package at.fhtw.mtcg.user;

import at.fhtw.mtcg.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;


class UserDALTest {
    @Test
    void addUser() throws IOException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        User user = new User("admin","admin");
        UserDAL userDAL = new UserDAL();
        userDAL.adduser(user);
        System.out.println(Arrays.toString(user.getHashedPassword()));
        System.out.println(Arrays.toString(user.getSalt()));
    }
    @Test
    void ChangeAndRetrieveUserInfo() throws SQLException, IOException {
        User user = new User("Kienboeck","me playin...",":-)");
        user.setUsername("admin");
        UserDAL userDAL = new UserDAL();
        boolean wasSuccessful = userDAL.editUserInfo(user);
        User userInfo = userDAL.getUserInfo(user.getUsername());
        assertTrue(wasSuccessful);
        assertEquals("Kienboeck",userInfo.getDisplayName());
        assertEquals("me playin...",userInfo.getBio());
        assertEquals(":-)",userInfo.getProfileImage());
    }
    @Test
    void ChangeNonExistentUser() throws SQLException, IOException {
        User user = new User("Kienboeck","me playin...",":-)");
        user.setUsername("nicht_echter_user");
        UserDAL userDAL = new UserDAL();
        boolean wasSuccessful = userDAL.editUserInfo(user);
        assertFalse(wasSuccessful);
    }
}