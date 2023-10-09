package at.fhtw.mtcg.session;

import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.User;
import lombok.Cleanup;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAL {
    public SessionDAL(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    private final ConnectionFactory connectionFactory;

    public User loginUser(User user) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
            @Cleanup
            Connection connection = connectionFactory.getConnection();
            getSaltPasswordByUser(user);
            PasswordHash passwordHash = new PasswordHash();
            boolean isPasswordCorrect = passwordHash.compareHash(user);
            if (isPasswordCorrect) {
                TokenGenerator tokenGenerator = new TokenGenerator(user);
                user.setToken(tokenGenerator.getToken());
                @Cleanup
                PreparedStatement psSetToken = connection.prepareStatement("UPDATE userdata SET token=? WHERE username=?");
                psSetToken.setString(1,user.getToken());
                psSetToken.setString(2, user.getUsername());
                psSetToken.executeUpdate();
                connection.commit();
            } else {
                user.setToken(null);
            }
        return user;

    }

    private void getSaltPasswordByUser(User user) throws SQLException {
        @Cleanup
        Connection connection = connectionFactory.getConnection();
        @Cleanup
        PreparedStatement preparedStatement = connection.prepareStatement("Select salt, password from userdata WHERE username=?");
        preparedStatement.setString(1,user.getUsername());
        @Cleanup
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
           user.setSalt(rs.getBytes(1));
           user.setHashedPassword(rs.getBytes(2));
        }
    }
    public User validateToken(String AuthToken) throws SQLException {
        if(AuthToken==null){
            System.out.println("No token");
            return null;
        }
        @Cleanup
        Connection connection = connectionFactory.getConnection();
        @Cleanup
        PreparedStatement psLogin = connection.prepareStatement("SELECT * FROM userData WHERE token=?");
        psLogin.setString(1, AuthToken);
        @Cleanup
        ResultSet rs = psLogin.executeQuery();
        if (rs.next()) {
            return new User(rs.getString(1),rs.getBytes(2),rs.getString(3),rs.getInt(4), rs.getInt(5), rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getBytes(12));
        }
        return null;
    }

}
