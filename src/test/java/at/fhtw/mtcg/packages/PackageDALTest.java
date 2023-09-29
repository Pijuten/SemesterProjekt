package at.fhtw.mtcg.packages;

import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.Card;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PackageDALTest {
    @Test
    void AddPackageTest() throws SQLException, IOException {
        PackageDAL packageDALTest = new PackageDAL();
        Card card1 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c04","WaterGoblin",10.0f);
        Card card2 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c04","WaterGoblin",10.0f);
        Card card3 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c04","GrassGoblin",10.0f);
        Card card4 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c04","FireGoblin",10.0f);
        List<Card> Deck = new ArrayList<>();
        Deck.add(card1);
        Deck.add(card2);
        Deck.add(card3);
        Deck.add(card4);
        int result = packageDALTest.addPackages(Deck);
        assertTrue(result>0);
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE from cards WHERE packageid="+result);
        connection.commit();
        connection.close();
    }
    @Test
    void addPackageSameIdTest() throws IOException {
        PackageDAL packageDALTest = new PackageDAL();
        Card card1 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c04","WaterGoblin",10.0f);
        Card card2 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c04","WaterGoblin",10.0f);
        Card card3 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c04","GrassGoblin",10.0f);
        Card card4 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c04","FireGoblin",10.0f);
        List<Card> Deck = new ArrayList<>();
        Deck.add(card1);
        Deck.add(card2);
        Deck.add(card3);
        Deck.add(card4);
        int result = packageDALTest.addPackages(Deck);
        assertEquals(0, result);
    }
    @Test
    void addPackageEmptyTest() throws IOException {
        PackageDAL packageDALTest = new PackageDAL();
        List<Card> Deck = new ArrayList<>();
        int result = packageDALTest.addPackages(Deck);
        assertEquals(0, result);
    }
}