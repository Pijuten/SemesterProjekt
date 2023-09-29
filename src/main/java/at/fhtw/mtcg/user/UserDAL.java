package at.fhtw.mtcg.user;

import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.User;
import at.fhtw.mtcg.session.PasswordHash;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAL {

    public boolean adduser(User user) throws IOException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
            PasswordHash passwordHash = new PasswordHash();
            passwordHash.getHashedPassword(user);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO userdata(username,password,salt) VALUES (?,?,?)");
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setBytes(2,user.getHashedPassword());
            preparedStatement.setBytes(3,user.getSalt());
            int i = preparedStatement.executeUpdate();
            if(i==1){
                connection.commit();
                connection.close();
                return true;
            }else{
                connection.rollback();
                connection.close();
                return false;
            }
    }
}
