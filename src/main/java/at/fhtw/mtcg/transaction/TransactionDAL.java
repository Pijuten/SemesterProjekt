package at.fhtw.mtcg.transaction;
import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAL {
    public TransactionDAL() {
    }

    public boolean AcquirePackage(User user) throws IOException, SQLException {
        if(user.getCurrency()<10)
            return false;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement preparedStatementChangePackageOwnership = connection.prepareStatement("UPDATE cards set username=? where packageid=(SELECT min(packageid) from cards where username is null)");
        preparedStatementChangePackageOwnership.setString(1,user.getUsername());
        preparedStatementChangePackageOwnership.executeUpdate();
        PreparedStatement preparedStatementSubtractMoney = connection.prepareStatement("UPDATE userdata set currency=currency-10 where username=?");
        preparedStatementSubtractMoney.setString(1,user.getUsername());
        preparedStatementSubtractMoney.executeUpdate();
        connection.commit();
        connection.close();
        return true;
    }
}
