package passivetemporaldifferenceagent;
import processing.core.PApplet;
import processing.core.PVector;

public class State {
	private GridSquare square;
	private GridModel worldState;

	public State( GridSquare aSquare, GridModel aWorldState ) {
		square = aSquare;
		worldState = aWorldState;
	}
	
	public boolean isEqual(State stateToCompare) {
		return square == stateToCompare.getSquare();
	}
	
	public GridSquare getSquare() { return square; }
	public GridModel getWorldState() { return worldState; }
}
