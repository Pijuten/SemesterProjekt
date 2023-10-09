package at.fhtw.mtcg.session;

import at.fhtw.mtcg.models.User;
import lombok.Getter;

public class TokenGenerator {
    public TokenGenerator(User user){
        this.user=user;
        this.token = GenerateToken();
    }
    private String GenerateToken(){
        String username = user.getUsername();
        if(username==null || username.isEmpty())
            return null;
        return username+"-mtcgToken";
    }
    private User user;
    @Getter
    private String token;

}
