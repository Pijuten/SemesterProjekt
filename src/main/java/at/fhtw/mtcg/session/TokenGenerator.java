package at.fhtw.mtcg.session;

import at.fhtw.mtcg.models.User;

public class TokenGenerator {
    public TokenGenerator(User user){
        this.user=user;
        this.token = GenerateToken();
    }
    private String GenerateToken(){
        String username = user.getUsername();
        return username+"-mtcgToken";
    }
    private User user;
    private String token;

    public String getToken() {
        return token;
    }
}
