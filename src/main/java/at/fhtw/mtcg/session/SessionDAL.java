package at.fhtw.mtcg.session;

import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAL {
    public User loginUser(User user) throws IOException, SQLException {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            Connection connection = connectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM userData WHERE username=? AND password=?");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TokenGenerator tokenGenerator = new TokenGenerator(user);
                user.setToken(tokenGenerator.getToken());
            } else {
                user.setToken(null);
            }
            connection.close();
        return user;

    }
}
