package bbc.gameoflifestub;

import java.util.Set;

public class Life {

    private Set<Cell> liveCells;

    public Life(Set<Cell> initialLiveCells) {
        this.liveCells = initialLiveCells;
    }

    // Read-only access to the game state
    public Set<Cell> getLiveCells() {
        return this.liveCells;
    }

    public boolean cellShouldSurvive(int numNeighbours) {
    	if (numNeighbours < 2 || numNeighbours > 3) {
			return false;
		} else {
			return true;
		}
    }
}
