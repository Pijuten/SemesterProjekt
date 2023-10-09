package at.fhtw.mtcg.session;

import at.fhtw.mtcg.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenGeneratorTest {
    @Test
    public void GenerateToken(){
        User user = new User("test","test");
        TokenGenerator tokenGenerator = new TokenGenerator(user);
        assertEquals(tokenGenerator.getToken(),"test-mtcgToken");
    }
    @Test
    public void GenerateEmptyToken(){
        User user = new User("","test");
        TokenGenerator tokenGenerator = new TokenGenerator(user);
        assertNull(tokenGenerator.getToken());
    }
}