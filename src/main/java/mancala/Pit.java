package mancala;

public class Pit {

    private int stones;

    public Pit(){
        stones = 0;
    }
    public void addStone(){
        stones++;
    }
    public int getStoneCount(){
        return stones;
    }
    public int removeStones(){
        int removeStones = stones;
        stones = 0;
        return removeStones;
    }

    @Override
    public String toString(){
        return ("Pit has " + getStoneCount() + " stones. ");
    }
}
