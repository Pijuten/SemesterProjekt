package at.fhtw.mtcg.session;

import at.fhtw.mtcg.models.User;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHashTest {
    @Test
    void HashPasswordTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = new User("admin","admin");
        String tmpPassword = user.getPassword();
        PasswordHash passwordHash = new PasswordHash();
        passwordHash.getHashedPassword(user);
        assertNotEquals(user.getPassword(),tmpPassword);
    }
}