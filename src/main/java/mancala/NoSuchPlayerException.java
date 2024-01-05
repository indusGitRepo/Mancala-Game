package mancala;

public class NoSuchPlayerException extends Exception {
    public NoSuchPlayerException(){
        super("Player not found.");
    }
    public NoSuchPlayerException(String message){
        super(message);
    }
}
