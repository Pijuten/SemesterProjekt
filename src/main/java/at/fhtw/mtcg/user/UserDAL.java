package at.fhtw.mtcg.user;

import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.User;
import at.fhtw.mtcg.session.PasswordHash;
import lombok.Cleanup;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAL {

    public boolean adduser(User user) throws IOException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        @Cleanup
        Connection connection = connectionFactory.getConnection();
        PasswordHash passwordHash = new PasswordHash();
        passwordHash.getHashedPassword(user);
        @Cleanup
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO userdata(username,password,salt) VALUES (?,?,?)");
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setBytes(2, user.getHashedPassword());
        preparedStatement.setBytes(3, user.getSalt());
        int i = preparedStatement.executeUpdate();
        if (i == 1) {
            connection.commit();
            return true;
        } else {
            connection.rollback();
            return false;
        }
    }

    public User getUserInfo(String username) throws IOException, SQLException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        @Cleanup
        Connection connection = connectionFactory.getConnection();
        @Cleanup
        PreparedStatement preparedStatement = connection.prepareStatement("Select displayname, bio, profileimage from userdata where username=?");
        preparedStatement.setString(1, username);
        @Cleanup
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return new User(
                    rs.getString("displayname"),
                    rs.getString("bio"),
                    rs.getString("profileimage")
            );
        }
        return null;
    }

    public boolean editUserInfo(User user) throws IOException, SQLException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        @Cleanup
        Connection connection = connectionFactory.getConnection();
        @Cleanup
        PreparedStatement preparedStatement = connection.prepareStatement("Update userdata Set displayname=?, bio=?, profileimage=? where username=?");
        preparedStatement.setString(1, user.getDisplayName());
        preparedStatement.setString(2, user.getBio());
        preparedStatement.setString(3, user.getProfileImage());
        preparedStatement.setString(4, user.getUsername());
        int isSuccess = preparedStatement.executeUpdate();
        if (isSuccess == 1) {
            connection.commit();
            return true;
        }
        connection.rollback();
        return false;
    }
}
