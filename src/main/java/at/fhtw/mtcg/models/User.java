package at.fhtw.mtcg.models;

 import com.fasterxml.jackson.annotation.JsonAlias;
 import lombok.Getter;
 import lombok.Setter;

@Getter @Setter
public class User {
    @JsonAlias({"Username"})
    private String username;
    @JsonAlias({"Password"})
    private String password;
    private byte[] hashedPassword;
    private String token;
    private int currency;
    private int score;
    private int win;
    private int draw;
    private int loss;

    @JsonAlias({"Name"})
    private String displayName;
    @JsonAlias({"Bio"})
    private String bio;
    @JsonAlias({"Image"})
    private String profileImage;
    private byte[] salt;


    public User(String Username, String Password){
        this.username=Username;
        this.password=Password;
    }
    public User(String displayName, String bio, String profileImage){
        this.displayName = displayName;
        this.bio = bio;
        this.profileImage = profileImage;
    }
    public User(String Username, byte[] hashedPassword,String token,int currency,int score, int win, int draw, int loss, String displayName, String bio, String profileImage,byte[] salt){
        this.username=Username;
        this.hashedPassword=hashedPassword;
        this.token = token;
        this.currency = currency;
        this.score = score;
        this.win = win;
        this.draw = draw;
        this.loss = loss;
        this.displayName = displayName;
        this.bio = bio;
        this.profileImage = profileImage;
        this.salt = salt;
    }
}
