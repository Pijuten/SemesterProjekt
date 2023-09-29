package at.fhtw.mtcg.models;

 import com.fasterxml.jackson.annotation.JsonAlias;
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
    private String displayName;
    private String bio;
    private String profileImage;

    private byte[] salt;


    public User(String Username, String Password){
        this.username=Username;
        this.password=Password;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
