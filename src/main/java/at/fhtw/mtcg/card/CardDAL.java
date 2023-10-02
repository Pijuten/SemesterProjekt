package at.fhtw.mtcg.card;

import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.Card;
import at.fhtw.mtcg.models.User;
import lombok.Cleanup;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDAL {

    public CardDAL() throws IOException {
        this.connectionFactory = new ConnectionFactory();
    }
    private final ConnectionFactory connectionFactory;
    List<Card> GetCards(User user) throws SQLException {
        @Cleanup
        Connection connection = connectionFactory.getConnection();
        @Cleanup
        PreparedStatement ps = connection.prepareStatement("SELECT cardid, cardname, damage from cards where username=?");
        ps.setString(1, user.getUsername());
        @Cleanup
        ResultSet rs = ps.executeQuery();
        List<Card> cards = new ArrayList<>();
        if (!rs.isBeforeFirst()) {
            return null;
        } else {
            while(rs.next()) {
                Card cards1 = new Card(
                        rs.getString("cardid"),
                        rs.getString("cardname"),
                        rs.getFloat("damage")
                        );
                cards.add(cards1);
            }
            return cards;
        }
    }
}
