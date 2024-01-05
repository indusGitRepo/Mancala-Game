package mancala;

public class Store {

    private int stones;
    private Player currentPlayer;

    public Store(){
        stones = 0;
    }

    public void addStones(int amount){
        stones = stones + amount;
    }
    public int emptyStore(){
        int removeStones = stones;
        stones = 0;
        return removeStones;
    }
    public int getTotalStones(){
        return stones;
    }
    void setOwner(Player player){
        currentPlayer = player;
    }
    public Player getOwner(){
        return currentPlayer;
    }

    @Override
    public String toString(){
        return ("Store owned by " + currentPlayer.getName() + " with " + currentPlayer.getStoreCount() + " stones.");
    }
}
