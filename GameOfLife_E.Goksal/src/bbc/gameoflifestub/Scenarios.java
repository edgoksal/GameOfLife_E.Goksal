/*
 *  Project name: GameOfLife/GameOfLife.java
 *  Author & email: Erdal Goksal <e.goksal@me.com>
 *  Date & time: Feb 22, 2016, 6:51:50 PM
 */
package bbc.gameoflifestub;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Erdal Goksal <e.goksal@me.com>
 */
public class Scenarios {

    private int x = 0;  // Determines the world's size; receives a value automatically.
    private int y = 0;  // The value is based on the max X and Y, can add any cell coordinates.

    Cell[][] grid; // Printing and checking for births. Creating 2D array of Cells called 'grid'
    Set<Cell> killSet = new HashSet<Cell>();  // Holds all the cells sent to "deathrow".
    Set<Cell> birthSet = new HashSet<Cell>(); // Holds all the cells sent to killSet, contains births.

    public void iterate(Life set) { // Used to run the methods, to simplify Main.
        // FOR loop which uses an Iterator object to run through our Set. 
        // The Iterator gets casted into a Cell type called "temp" which is used inside the FOR loop.
        for (Iterator<Cell> it = set.getLiveCells().iterator(); it.hasNext();) { //FOR loop to run through Life
            Cell temp = (Cell) it.next(); // This casts Iterator to Cell.
            death(temp, set); // Custom method
        }
        birth(set); // Custom method
        set.getLiveCells().removeAll(killSet); // Removes from Life Cells which are in killSet 
        set.getLiveCells().addAll(birthSet); // Adds newborn cells to Life Cells which are in birthSet.
        killSet.clear();
        birthSet.clear(); // Flush collections after every iteration.
        printGameState(set); // Prints to console. (Methods)
    }

    public Cell[][] transform(Life set) { 
        // Method returns a 2D array of Cell objects,used only once.
        if (x == 0 && y == 0) { // X and Y are fixed!
            // +1 to make it work and + 2 to have a better look. 
            x = getMaxX(set)+2;
            y = getMaxY(set)+2;
        }
        grid = new Cell[x][y]; // The grid used to visualise Life, resets for every print
        // ensuring the grid respects our current Life Set.
        for (Iterator<Cell> it = set.getLiveCells().iterator(); it.hasNext();) {
            Cell temp = (Cell) it.next();            
            grid[temp.getX()][temp.getY()] = temp; // Places the cell object in the grid at (x,y). 
        }
        return grid;
    }

    public void printGameState(Life set) {
        // Method runs through the grid and prints.
        grid = transform(set);
        for (int i = 0; i < x; i++) {
            System.out.print("\n"); // New line at the end of each row.
            for (int j = 0; j < y; j++) {
                if (grid[i][j] != null) { // If it is NOT a null, it must be alive, so print *.
                    System.out.print("*"); // Visual representation of a live cell.
                } else {
                    System.out.print("."); // Visual representation of a dead cell.
                }
            }
        }
        System.out.print("\n"); 
    }
    
    
    public void death(Cell cell, Life set) {
        // Method contains two scenarios 1&2: Underpopulation & Overcrowding
        if (lookAround(cell.getX(), cell.getY(), set) < 2 || lookAround(cell.getX(), cell.getY(), set) > 3) {
            // Logical OR operator, if either returns true, the cell is added into the killSet.
            // Not killed until all the cells are checked to avoid removing a cell
            // which might have impact on a number of different cells.
            killSet.add(cell);
        }
    }

    public void birth(Life set) {
        // Method contains one scenario 4: Creation of Life
        // Own loop to run through the grid, it isn't placed inside iterate()'s loop, only after.
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (grid[i][j] == null) {
                    if (lookAround(i, j, set) == 3) {
                        // If a dead cell has EXACTLY 3 neighbouring live cells, it is added into the birthSet.
                        // Same as with killSet, this applies only after all cells are checked.
                        birthSet.add(new Cell(i, j));
                    }
                }
            }
        }
    }

    private int lookAround(int x, int y, Life set) {
        int liveCells = 0;
        // If current cell is at (x,y), it's adjacent cells are at:
        //  x-1,y-1 | x-1,y   | x-1,y+1
        //  x  ,y-1 | x  ,y   | x  ,y+1
        //  x+1,y-1 | x+1,y   | x+1,y+1
        // iterate over all these coordinates and look into each position:
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                // Run through Life Set:
                for (Iterator<Cell> it = set.getLiveCells().iterator(); it.hasNext();) { 
                    Cell temp = (Cell) it.next();
                    if (temp.hashCode() == dummyHashGenerator(i, j) && temp.hashCode() != dummyHashGenerator(x, y)) {
                        // NOTE: If there is a cell somewhere between (x-1,y-1) and (x+1,y+1) with the hash print equal to
                        // the hash generated by dummyHashGenerator(x,y), that means that cell is alive and adjacent to the
                        // cell being inspecting. Since the cell being inspecting is also between 
                        // (x-1,y-1) and (x+1,y+1), it won't consider it when counting live cells.
                        liveCells++;
                    }
                }
            }
        }
        // Loop done = finds out how many live cells are around any given cell's coordinates.
        // Used to determine what happens next.
        return liveCells;
    }

    public int dummyHashGenerator(int x, int y) { // Creates dummy hash prints based on (x,y). 
        return 31 * 31 * x + 31 * y;
    }

    public int getMaxX(Life set) { // Finds max X in Life.
        int max = 0;
        for (Iterator<Cell> it = set.getLiveCells().iterator(); it.hasNext();) {
            Cell temp = (Cell) it.next();
            if (temp.getX() > max) {
                max = temp.getX();
            }
        }
        return max;
    }

    public int getMaxY(Life set) { // Finds max X in Life.
        int max = 0;
        for (Iterator<Cell> it = set.getLiveCells().iterator(); it.hasNext();) {
            Cell temp = (Cell) it.next();
            if (temp.getY() > max) {
                max = temp.getY();
            }
        }
        return max;
    }
}
