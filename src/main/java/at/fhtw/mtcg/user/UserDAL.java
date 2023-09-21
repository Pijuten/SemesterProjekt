package at.fhtw.mtcg.user;

import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAL {
    public UserDAL(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    private ConnectionFactory connectionFactory;

    public boolean adduser(User user){
        Connection connection = connectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO userdata(username,password) VALUES (?,?)");
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
