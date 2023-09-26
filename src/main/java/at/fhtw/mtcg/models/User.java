package at.fhtw.mtcg.models;

 import com.fasterxml.jackson.annotation.JsonAlias;
public class User {
    @JsonAlias({"Username"})
    private String username;
    @JsonAlias({"Password"})
    private String password;

    public User(String Username, String Password){
        this.username=Username;
        this.password=Password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
