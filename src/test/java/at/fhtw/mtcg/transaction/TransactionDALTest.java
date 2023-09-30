package at.fhtw.mtcg.transaction;


import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.Card;
import at.fhtw.mtcg.models.User;
import at.fhtw.mtcg.packages.PackageDAL;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionDALTest {
    @Test
    void changeAndRetrieveUserData() throws IOException, SQLException {
        User user = new User("admin","admin");
        user.setCurrency(40);
        PackageDAL packageDALTest = new PackageDAL();
        TransactionDAL transactionDAL = new TransactionDAL();
        Card card1 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c04","WaterGoblin",10.0f);
        Card card2 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c01","WaterGoblin",10.0f);
        Card card3 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c02","GrassGoblin",10.0f);
        Card card4 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c03","FireGoblin",10.0f);
        List<Card> Deck = new ArrayList<>();
        Deck.add(card1);
        Deck.add(card2);
        Deck.add(card3);
        Deck.add(card4);
        int result = packageDALTest.addPackages(Deck);

            assertTrue(transactionDAL.AcquirePackage(user));

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE from cards WHERE packageid="+result);
        connection.commit();
        connection.close();
    }
}