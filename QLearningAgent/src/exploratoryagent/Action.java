package exploratoryagent;
import processing.core.PApplet;
import processing.core.PVector;

public class Action {
	PVector _direction; 
	
	public static PVector NORTH = new PVector(0, -1);
	public static PVector EAST = new PVector(1, 0);
	public static PVector SOUTH = new PVector(0, 1);
	public static PVector WEST = new PVector(-1, 0);
	
	private PApplet appRef;
	public Action( PVector direction, Double probability ) {
		
		appRef = ExploratoryAgent.getInstance();
		
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
	
	
	public PVector rotateVector( PVector aVector, float angle ) {
		PVector v = new PVector( aVector.x, aVector.y );
		float s = PApplet.sin(angle);
		float c = PApplet.cos(angle);
 
		float nx = c * v.x - s * v.y;
		float ny = s * v.x + c * v.y;
 
		v.x = PApplet.round(nx);
		v.y = PApplet.round(ny);
		
		return v;
	}
}
