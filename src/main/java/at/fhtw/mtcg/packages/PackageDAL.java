package at.fhtw.mtcg.packages;

import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.Card;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class PackageDAL {
    public int addPackages(List<Card> Deck) throws IOException {
       try {
            if (Deck.isEmpty()) {
                return 0;
            }
            ConnectionFactory connectionFactory = new ConnectionFactory();
            Connection connection = connectionFactory.getConnection();

            int packageId = 0;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select MAX(packageid) as maxLevel from cards");
            if (rs.next() && !rs.wasNull()) {
                packageId = rs.getInt("maxLevel") + 1;
            }
            for (Card card : Deck) {
                int changedRows;
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cards(cardid,cardname,damage,packageid) VALUES (?,?,?,?)");
                preparedStatement.setString(1, card.getId());
                preparedStatement.setString(2, card.getName());
                preparedStatement.setFloat(3, card.getDamage());
                preparedStatement.setInt(4, packageId);
                changedRows = preparedStatement.executeUpdate();
                if (changedRows != 1) {
                    connection.rollback();
                    connection.close();
                    return 0;
                }
            }
            connection.commit();
            connection.close();
            return packageId;
        }catch(SQLException sqlException){
            return 0;
        }
    }
}