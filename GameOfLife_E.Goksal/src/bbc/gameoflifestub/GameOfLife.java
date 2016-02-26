/*
 *  Project name: GameOfLife/GameOfLife.java
 *  Author & email: Erdal Goksal <e.goksal@me.com>
 *  Date & time: Feb 22, 2016, 6:51:50 PM
 */
package bbc.gameoflifestub;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Erdal Goksal <e.goksal@me.com>
 */
public class GameOfLife {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scenarios s = new Scenarios();  //named Scenarios as s and called it in line 47
        Set<Cell> initial = new HashSet<Cell>();

        // Cell coordinates MUST NOT be negative!
        // Scenario 6:
        initial.add(new Cell(1, 0));
        initial.add(new Cell(1, 1));
        initial.add(new Cell(1, 2));
        

        // Example:
//        initial.add(new Cell(0, 0));
//        initial.add(new Cell(0, 2));
//        initial.add(new Cell(1, 0));
//        initial.add(new Cell(2, 0));
//        initial.add(new Cell(2, 1));
//        initial.add(new Cell(2, 2));
        Life gameState = new Life(initial); 

        System.out.print("Initial state:");
        s.printGameState(gameState);
        System.out.print("\n");

        int turns = 5;
        for (int i = 1; i <= turns; i++) { //FOR loop (i increments)
            System.out.print("Turn " + i + ":");
            s.iterate(gameState); //engine and inside here is where the magic happens
        }
        System.out.println("\n---\n   Done!");
    }
}
