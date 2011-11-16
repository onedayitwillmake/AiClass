package passivetemporaldifferenceagent;
import processing.core.PApplet;
import processing.core.PVector;

public class Action {
	PVector _direction; 
	
	private static PApplet appRef;
	
	public static PVector NORTH = new PVector(0, -1);
	public static PVector EAST = new PVector(1, 0);
	public static PVector SOUTH = new PVector(0, 1);
	public static PVector WEST = new PVector(-1, 0);
	public Action( PVector direction, Double probability ) {
		
		appRef = PassiveTemporalDifferenceAgent.getInstance();
		
		if( direction != null ) {
			_direction = direction;
		} else {
//			_direction = new PVector( appRef.random(-10, 10), appRef.random(-10, 10) );
		}
		
	}
	
	public PVector getAction() {
		return _direction;
	}

	public void setActionAndProbability(PVector newDirection, double probability) {
		_direction = newDirection;
	}
}
