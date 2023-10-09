package at.fhtw.mtcg.card;

import at.fhtw.db.ConnectionFactoryH2Impl;
import at.fhtw.db.ConnectionFactoryImpl;
import at.fhtw.mtcg.models.Card;
import at.fhtw.mtcg.models.User;
import at.fhtw.mtcg.session.SessionDAL;
import at.fhtw.mtcg.user.DuplicateUserException;
import at.fhtw.mtcg.user.UserDAL;
import at.fhtw.mtcg.user.UserDalEmptyFieldException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardDALTest {
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
    public void GetCardsTest() throws IOException, SQLException {
        User user = new User("admin","admin");
        CardDAL cardDAL = new CardDAL(connectionFactoryH2);
        List<Card> cards = cardDAL.GetCards(user);
        assertTrue(cards.isEmpty());
    }

}