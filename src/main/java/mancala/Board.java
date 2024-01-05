package mancala;

import java.util.ArrayList;

public class Board {

    private ArrayList<Pit> pits;
    private ArrayList<Store> stores;

    public Board(){
        setUpPits();
        setUpStores();
        initializeBoard();
    }

    void setUpPits(){ 
        pits = new ArrayList<Pit>();
        for (int i = 0; i < 12; i++){
            pits.add(new Pit());
        }
    }
    public ArrayList<Pit> getPits(){
        return pits;
    }
    public ArrayList<Store> getStores(){
        return stores;
    }
    void setUpStores(){  
        stores = new ArrayList<Store>();
        for (int j = 0; j < 2; j++){
            stores.add(new Store());
        }
    }
    public void initializeBoard(){ 
        for (int i = 0; i < 12; i++){
            for (int j = 0; j < 4; j++){
                pits.get(i).addStone(); 
            }
        }
        for (int i = 0; i < 2; i++){
            stores.get(i).emptyStore();
        }
    }
    public void resetBoard(){ 
        for (int i = 0; i < 12; i++){
            pits.get(i).removeStones();
        }
        for (int j = 0; j < 2; j++){
            stores.get(j).emptyStore();
        }
        initializeBoard();
    }
    public void registerPlayers(Player one, Player two){ 
        stores.get(0).setOwner(one);
        stores.get(1).setOwner(two);

        one.setStore(stores.get(0));
        two.setStore(stores.get(1));
    }
    public int moveStones(int startPit, Player player)throws InvalidMoveException{ 
        int storeStones = player.getStoreCount();
        int finalStoreCount = 0;
        if (pits.get(startPit - 1).getStoneCount() == 0){
            throw new InvalidMoveException("Pit is empty");
        }
        if ((startPit > (12/2)) && (player.getStore() == stores.get(0))){
            throw new InvalidMoveException("Only move stones from your own pits");
        }
        if ((startPit <= (12/2)) && (player.getStore() == stores.get(1))){
            throw new InvalidMoveException("Only move stones from your own pits");
        }
        try {
            distributeStones(startPit);
            finalStoreCount = player.getStoreCount();
        } catch (PitNotFoundException e) {
            System.out.println(e.getMessage());
        }
        storeStones = finalStoreCount - storeStones;
        return storeStones;
    }
    public int distributeStones(int startingPoint)throws PitNotFoundException{ 
        int capturedStones = 0;
        if (startingPoint <= 0 || startingPoint >= 13){ 
            throw new PitNotFoundException("Pit is out of bound");
        }
        int currentIndex = startingPoint - 1;
        int stonesMove = pits.get(currentIndex).removeStones();
        int result = stonesMove;

        while (stonesMove > 0){
            if (currentIndex == 11){
                if ((startingPoint >= 7) && (startingPoint <= 12)){
                    stores.get(1).addStones(1);
                    stonesMove--;
                    currentIndex++;
                }
                currentIndex = 0;
            }else if (currentIndex == 5){
                if ((startingPoint >= 1)){
                    stores.get(0).addStones(1);
                    stonesMove--;
                    currentIndex++;
                }
            }else{
                currentIndex++;
            }
            if (stonesMove > 0){
                pits.get(currentIndex).addStone();
                stonesMove--;
            }
        }
        if (pits.get(currentIndex).getStoneCount() == 1){
            try{
                capturedStones = captureStones(currentIndex+1);
                result = result + capturedStones;
            } catch(PitNotFoundException e){
                System.out.println(e.getMessage());
            }
        }
        return result;
    }
    public int captureStones(int stoppingPoint)throws PitNotFoundException{  
        if (stoppingPoint <= 0 || stoppingPoint >= 13){
            throw new PitNotFoundException("Pit is out of bound");
        }
        int oppositeIndex = getOppositeIndex(stoppingPoint) - 1;
        int stonesCaptured = pits.get(oppositeIndex).removeStones();
        if ((stoppingPoint >= 1) && (stoppingPoint <= 6)){
            stores.get(0).addStones(stonesCaptured);
        }else {
            stores.get(1).addStones(stonesCaptured);
        }
   
        return stonesCaptured;
    }
    public int getNumStones(int pitNum)throws PitNotFoundException{ 
        if (pitNum <= 0 || pitNum >= 13){ 
            throw new PitNotFoundException("Pit is out of bound");
        }
        return pits.get(pitNum - 1).getStoneCount();
    }
    int getOppositeIndex(int pitNum){
        int oppositeIndex = 13 - pitNum;
        return oppositeIndex;
    }
    public boolean isSideEmpty(int pitNum)throws PitNotFoundException{ 
        int counter;
        if (pitNum <= 0 || pitNum >= 13){
            throw new PitNotFoundException("Pit is out of bound");
        }else if ((pitNum <= 6) && (pitNum >= 1)){
            counter = 0;
            for (int i = 0; i < 6; i++){
                if (pits.get(i).getStoneCount() == 0){
                    counter++;
                    if (counter == 6){
                        return true;
                    }
                }
            }
        }else if ((pitNum <= 12) && (pitNum >= 7)){
            counter = 0;
            for (int j = 6; j < 12; j++){
                if (pits.get(j).getStoneCount() == 0){
                    counter++;
                    if (counter == 6){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    @Override
    public String toString(){ 
        String toGame = "Store 1 info:\n" + stores.get(0).toString() + "\nStore 2 info:\n" + stores.get(1).toString();
        for (int i = 0; i < 12; i++){
            toGame += "\nPit " + (i+1) + ": " + pits.get(i).toString();
        }
        return toGame;
    }
}
