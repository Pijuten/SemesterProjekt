package at.fhtw.mtcg.transaction;
import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.User;
import lombok.Cleanup;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAL {
    public TransactionDAL(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    private final ConnectionFactory connectionFactory;
    public void AcquirePackage(User user) throws SQLException {
        if(user.getCurrency()<10)
            return;
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
    }
}
