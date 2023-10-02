package at.fhtw.mtcg.user;

public class DuplicateUserException extends Exception{
    public DuplicateUserException(String message){
        super(message);
    }
}
