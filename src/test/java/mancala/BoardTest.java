package mancala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTest {
    private Board board;
    private Player playerOne;
    private Player playerTwo;

    @BeforeEach
    public void setUp() {
        board = new Board();
        playerOne = new Player("Player One");
        playerTwo = new Player("Player Two");
    }
    @Test
    public void testResetBoardPits(){
        board.resetBoard();
            for (int i = 0; i < 12; i++){
                assertEquals(4, board.getPits().get(i).getStoneCount());
                assertSame(4, board.getPits().get(i).getStoneCount());
            }
    }
    @Test
    public void testRestBoardStores(){
        board.resetBoard();
            for (int j = 0; j < 2; j++){
                assertEquals(0, board.getStores().get(j).getTotalStones());
                assertSame(0, board.getStores().get(j).getTotalStones());
            }
    }
    @Test
    public void testRegisterPlayersOne(){ 
            board.registerPlayers(playerOne, playerTwo);
            assertEquals(playerOne, board.getStores().get(0).getOwner());
            assertSame(playerOne, board.getStores().get(0).getOwner());

            assertEquals(board.getStores().get(0), playerOne.getStore());
            assertEquals(board.getStores().get(0), playerOne.getStore());
    }
    @Test
    public void testRegisterPlayersTwo(){
            board.registerPlayers(playerOne, playerTwo);
            assertEquals(playerTwo, board.getStores().get(1).getOwner());
            assertSame(playerTwo, board.getStores().get(1).getOwner());

            assertEquals(board.getStores().get(1), playerTwo.getStore());
            assertEquals(board.getStores().get(1), playerTwo.getStore());
    }
    @Test
    public void testGetNumStones() throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);

        int numStones = board.getNumStones(1);
        assertEquals(4, numStones);
    }
    @Test
    public void testGetNumStonesException() throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        assertThrows(PitNotFoundException.class, () -> board.getNumStones(13), "13 should be out of bounds");
    }
    @Test
    public void testGetNumStonesEmpty() throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        board.getPits().get(2).removeStones();
        int stones = board.getPits().get(2).getStoneCount();
        assertEquals(0, stones);
    }
    @Test
    public void testGetNumStonesMove() throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        board.distributeStones(3);
        int stones = board.getPits().get(4).getStoneCount();
        assertEquals(5, stones);

    }
    @Test
    public void testDistributeStones() throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        int stones = board.distributeStones(4);
        assertEquals(4, stones);
    }
    @Test
    public void testDistributeStonesException () throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        assertThrows(PitNotFoundException.class, () -> board.distributeStones(-2), "-2 should be out of bounds");
    }
    @Test
    public void testDistributeStonesLowBoundary () throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        assertThrows(PitNotFoundException.class, () -> board.distributeStones(0), "0 should be out of bounds");
    }
    @Test
    public void testDistributeStonesHighBoundary () throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        assertThrows(PitNotFoundException.class, () -> board.distributeStones(13), "13 should be out of bounds");
    }
    @Test
    public void testCaptureStones() throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        int captured = board.captureStones(1);
        assertEquals(4, captured);
    }
    @Test
    public void testCaptureStonesException() throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        assertThrows(PitNotFoundException.class, () -> board.captureStones(52), "52 should be out of bounds");
    }
    @Test
    public void testCaptureStonesLowBoundary() throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        assertThrows(PitNotFoundException.class, () -> board.captureStones(0), "0 should be out of bounds");
    }
    @Test
    public void testCaptureStonesHighBoundary() throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        assertThrows(PitNotFoundException.class, () -> board.captureStones(13), "13 should be out of bounds");
    }
    @Test
    public void testCaptureStonesAction() throws InvalidMoveException{
        int stones = 0;
        board.registerPlayers(playerOne, playerTwo);
        stones = board.moveStones(3, playerOne);
        stones = board.moveStones(4, playerOne);
        stones = board.moveStones(8, playerTwo);
        stones = board.moveStones(5, playerOne);
        stones = board.moveStones(9, playerTwo);
        stones = board.moveStones(2, playerOne);
        stones = board.moveStones(8, playerTwo);


        assertEquals(1, stones);
    }
    @Test
    public void testIsSideOneEmpty() throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        assertFalse(board.isSideEmpty(1));
    }
    @Test
    public void testIsSideTwoEmpty() throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        assertFalse(board.isSideEmpty(7));
    }
    @Test
    public void testIsSideFull() throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        board.setUpPits();
        board.initializeBoard();
            for (int  i = 0; i < 6; i++){
                board.getPits().get(i).removeStones();
            }
        assertTrue(board.isSideEmpty(1));
    }
    @Test
    public void testIsSideEmptyException() throws PitNotFoundException{
        board.registerPlayers(playerOne, playerTwo);
        assertThrows(PitNotFoundException.class, () -> board.isSideEmpty(0), "0 should be out of bounds");
    }
}
