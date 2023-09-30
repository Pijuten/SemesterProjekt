package at.fhtw.mtcg.transaction;
import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.User;
import lombok.Cleanup;

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
        @Cleanup
        Connection connection = connectionFactory.getConnection();
        @Cleanup
        PreparedStatement preparedStatementChangePackageOwnership = connection.prepareStatement("UPDATE cards set username=? where packageid=(SELECT min(packageid) from cards where username is null)");
        preparedStatementChangePackageOwnership.setString(1,user.getUsername());
        preparedStatementChangePackageOwnership.executeUpdate();
        @Cleanup
        PreparedStatement preparedStatementSubtractMoney = connection.prepareStatement("UPDATE userdata set currency=currency-10 where username=?");
        preparedStatementSubtractMoney.setString(1,user.getUsername());
        preparedStatementSubtractMoney.executeUpdate();
        connection.commit();
        return true;
    }
}
