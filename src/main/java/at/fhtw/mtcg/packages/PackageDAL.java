package at.fhtw.mtcg.packages;

import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.Card;
import lombok.Cleanup;
import org.postgresql.core.v3.ConnectionFactoryImpl;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class PackageDAL {

    public PackageDAL(ConnectionFactory connectionFactory) throws IOException {
        this.connectionFactory = connectionFactory;
    }
    private final ConnectionFactory connectionFactory;
    public int addPackages(List<Card> Deck) throws IOException {
       try {
            if (Deck.isEmpty()) {
                return 0;
            }
            @Cleanup
            Connection connection = connectionFactory.getConnection();

            int packageId = 0;
            @Cleanup
            Statement stmt = connection.createStatement();
            @Cleanup
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
                    return 0;
                }
            }
            connection.commit();
            return packageId;
        }catch(SQLException sqlException){
            return 0;
        }
    }
}
