package at.fhtw.mtcg.session;

import at.fhtw.db.ConnectionFactory;
import at.fhtw.mtcg.models.User;

import java.io.Console;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAL {
    public User loginUser(User user) throws IOException, SQLException {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            Connection connection = connectionFactory.getConnection();
            PreparedStatement psLogin = connection.prepareStatement("SELECT * FROM userData WHERE username=? AND password=?");
            psLogin.setString(1, user.getUsername());
            psLogin.setString(2, user.getPassword());
            ResultSet rs = psLogin.executeQuery();
            if (rs.next()) {
                TokenGenerator tokenGenerator = new TokenGenerator(user);
                user.setToken(tokenGenerator.getToken());

                PreparedStatement psSetToken = connection.prepareStatement("UPDATE userdata SET token=? WHERE username=?");
                psSetToken.setString(1,user.getToken());
                psSetToken.setString(2, user.getUsername());
                int i = psSetToken.executeUpdate();
                connection.commit();
            } else {
                user.setToken(null);
            }
            connection.close();
        return user;

    }
}
