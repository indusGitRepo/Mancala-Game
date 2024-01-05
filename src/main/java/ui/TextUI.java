package ui;

import java.util.Scanner;
import mancala.InvalidMoveException;
import mancala.MancalaGame;
import mancala.PitNotFoundException;
import mancala.Player;

public class TextUI{
    
    private MancalaGame game = new MancalaGame();
    private Scanner scanner = new Scanner(System.in);
    private Player currentPlayer = new Player();
    private Player p1 = new Player();
    private Player p2 = new Player();

    public void gameMessages(){
        if(!(game.isGameOver())){
            System.out.println("Welcome to Mancala Game!\n");
        }else{
            System.out.println("Game Over!\nWinner is " + currentPlayer.getName() + "!");
        }
    }
    public void setUpPlayers(){
        String playerOne;
        String playerTwo;

        System.out.print("Name of Player 1: ");
        playerOne = scanner.nextLine();
        p1.setName(playerOne);
        System.out.print("Name of Player 2: ");
        playerTwo = scanner.nextLine();
        p2.setName(playerTwo);

        game.setPlayers(p1, p2);
        currentPlayer = game.getCurrentPlayer();
    }
    public Player changeCurrentPlayer(Player player){
        if (player.equals(p1)){
            game.setCurrentPlayer(p2);
            currentPlayer = game.getCurrentPlayer();
        }else{
            game.setCurrentPlayer(p1);
            currentPlayer = game.getCurrentPlayer();
        }
        return currentPlayer;
    }
    public void displayBoard(){
        System.out.println(game.toString());
    }
    public boolean makeMove(int startPit){
        try {
            game.move(startPit);
        } catch (InvalidMoveException e) {
            System.out.println("Move is invalid: " + e.getMessage());
            return false;
        }
        return true;
    }
    public boolean pitNotFound(int pitNum){
        try{
            game.getNumStones(pitNum);
        } catch (PitNotFoundException e){
            System.out.println(pitNum + " " + e.getMessage());
            return true;
        }
        return false;
    }
    public static void main(String[] args){
        TextUI textUI = new TextUI();
        textUI.gameMessages();
        textUI.setUpPlayers();
        int move;
        boolean validMove;
        boolean validPit;
        textUI.displayBoard();

        do{
            System.out.println(textUI.currentPlayer.getName() + ": Enter pit (1-12) to make move from: ");
            move = textUI.scanner.nextInt();
            validPit = textUI.pitNotFound(move);
            if (validPit){
                while(validPit){
                    System.out.println(textUI.currentPlayer.getName() + ": Enter pit (1-12) to make move from: ");
                    move = textUI.scanner.nextInt();
                    validPit = textUI.pitNotFound(move);
                }
            }
            validMove = textUI.makeMove(move);
            while (!validMove){
                System.out.println(textUI.currentPlayer.getName() + ": Enter valid input: ");
                move = textUI.scanner.nextInt();
                validMove = textUI.makeMove(move);
            }  
            textUI.displayBoard();     
            textUI.currentPlayer = textUI.changeCurrentPlayer(textUI.currentPlayer);
        }while(!(textUI.game.isGameOver()));

        textUI.gameMessages();
    }
}
