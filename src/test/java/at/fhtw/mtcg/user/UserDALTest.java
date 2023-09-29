package at.fhtw.mtcg.user;

import at.fhtw.mtcg.models.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;


class UserDALTest {
    @Test
    void addUser() throws IOException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        User user = new User("admin","admin");
        UserDAL userDAL = new UserDAL();
        userDAL.adduser(user);
    }
}