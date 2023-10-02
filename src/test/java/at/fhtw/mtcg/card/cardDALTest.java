package at.fhtw.mtcg.card;

import at.fhtw.mtcg.models.Card;
import at.fhtw.mtcg.models.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardDALTest {

    @Test
    public void GetCardsTest() throws IOException, SQLException {
        User user = new User("admin","admin");
        CardDAL cardDAL = new CardDAL();
        List<Card> cards = cardDAL.GetCards(user);
        assertFalse(cards.isEmpty());
    }

}