package at.fhtw.mtcg.packages;

import at.fhtw.db.ConnectionFactoryH2Impl;
import at.fhtw.db.ConnectionFactoryImpl;
import at.fhtw.mtcg.models.Card;
import at.fhtw.mtcg.models.User;
import at.fhtw.mtcg.user.DuplicateUserException;
import at.fhtw.mtcg.user.UserDAL;
import at.fhtw.mtcg.user.UserDalEmptyFieldException;
import org.junit.jupiter.api.BeforeAll;
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

    static private ConnectionFactoryH2Impl connectionFactoryH2;
    @BeforeAll
    static public void setup() throws IOException, UserDalEmptyFieldException, DuplicateUserException {
        connectionFactoryH2 = new ConnectionFactoryH2Impl();
        User user = new User("admin", "admin");
        UserDAL userDAL = new UserDAL(connectionFactoryH2);
        userDAL.adduser(user);
    }
    @Test
    void AddPackageTest() throws SQLException, IOException {
        PackageDAL packageDALTest = new PackageDAL(connectionFactoryH2);
        Card card1 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c04","WaterGoblin",10.0f);
        Card card2 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c02","WaterGoblin",10.0f);
        Card card3 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c03","GrassGoblin",10.0f);
        Card card4 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c09","FireGoblin",10.0f);
        List<Card> Deck = new ArrayList<>();
        Deck.add(card1);
        Deck.add(card2);
        Deck.add(card3);
        Deck.add(card4);
        int result = packageDALTest.addPackages(Deck);
        assertTrue(result>0);
        ConnectionFactoryImpl connectionFactoryImpl = new ConnectionFactoryImpl();
        Connection connection = connectionFactoryH2.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE from cards WHERE packageid="+result);
        connection.commit();
        connection.close();

    }
    @Test
    void addPackageSameIdTest() throws IOException {
        PackageDAL packageDALTest = new PackageDAL(connectionFactoryH2);
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
        PackageDAL packageDALTest = new PackageDAL(connectionFactoryH2);
        List<Card> Deck = new ArrayList<>();
        int result = packageDALTest.addPackages(Deck);
        assertEquals(0, result);
    }
}