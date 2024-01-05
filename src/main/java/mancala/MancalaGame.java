package mancala;

import java.util.ArrayList;

public class MancalaGame {
    private Board board;
    private Player currentPlayer = new Player();
    private ArrayList<Player> players;

    public MancalaGame(){
        board = new Board();
        players = new ArrayList<Player>();
    }
    public void setPlayers(Player onePlayer, Player twoPlayer){
        board.registerPlayers(onePlayer, twoPlayer);
        players.add(onePlayer);
        players.add(twoPlayer);
        currentPlayer = onePlayer;
    }
    public ArrayList<Player> getPlayers(){
        return players;
    }
    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }
    void setBoard(Board theBoard){  
        theBoard.setUpPits();
        theBoard.setUpStores();
        board = theBoard;
    }
    public Board getBoard(){
        return board;
    }
    public int getNumStones(int pitNum)throws PitNotFoundException{
        int stones = 0;

        if (pitNum <= 0 || pitNum >= 13){
            throw new PitNotFoundException("Pit is out of bound");
        }
        stones = board.getNumStones(pitNum);
        return stones;
    }
    public int move(int startPit)throws InvalidMoveException{

        int move = 0;
        if (board.getPits().get(startPit - 1).getStoneCount() == 0){ //changed this 
            throw new InvalidMoveException("Pit is empty");
        }
        if ((startPit > (12/2)) && (currentPlayer.getStore() == board.getStores().get(0))){
            throw new InvalidMoveException("Only move stones from your own pits");
        }
        if ((startPit <= (12/2)) && (currentPlayer.getStore() == board.getStores().get(1))){
            throw new InvalidMoveException("Only move stones from your own pits");
        }
        board.moveStones(startPit, currentPlayer);
        if ((startPit >= 1) && (startPit <= 6)){
            for (int i = 0; i < 6; i++){
                move += board.getPits().get(i).getStoneCount();
            }
        }else{
            for (int i = 6; i < 11; i++){
                move += board.getPits().get(i).getStoneCount();
            }
        }
        return move;
    }
    public int getStoreCount(Player player)throws NoSuchPlayerException{
        if (player.getStore().equals(null)){
            throw new NoSuchPlayerException("Player does not exist");
        }
        return player.getStoreCount();
    }
    public Player getWinner()throws GameNotOverException{

        int winner = -1;

        if (!(isGameOver())){
            throw new GameNotOverException("Game is not over");
        }
        try {
            if ((board.isSideEmpty(1)) || (board.isSideEmpty(7))){
                if (players.get(0).getStoreCount() > players.get(1).getStoreCount()){
                    winner = 0;
                }else if (players.get(1).getStoreCount() > players.get(0).getStoreCount()){
                    winner = 1;
                }
            }else if (board.isSideEmpty(7)){
                winner = 1;
            }
        } catch (PitNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return players.get(winner);
    }
    public boolean isGameOver(){
        try{
            if (!(board.isSideEmpty(1)) && !(board.isSideEmpty(7))){
                return false;
            }
        } catch (PitNotFoundException e){
            System.out.println(e.getMessage());
        }
        return true;
    }
    public void startNewGame(){
        board.resetBoard();
    }
    @Override
    public String toString(){
        String toGame = board.toString();
        return toGame;
    }
}
