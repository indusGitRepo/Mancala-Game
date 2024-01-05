package mancala;

public class Player {

    private String playerName;
    private Store currentStore;

    public Player(){
        playerName = "";
    }
    public Player(String name){
        playerName = name;
    }

    public String getName(){
        return playerName;
    }
    public void setName(String name){
        playerName = name;
    }
    public Store getStore(){
        return currentStore;
    }
    public int getStoreCount(){
        return currentStore.getTotalStones();
    }
    void setStore(Store store){
        currentStore = store;
    }

    @Override
    public String toString(){
        return "Player: " + playerName + "\n" + currentStore.toString();
    }
}
