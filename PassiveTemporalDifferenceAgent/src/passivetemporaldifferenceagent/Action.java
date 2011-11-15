package passivetemporaldifferenceagent;
import processing.core.PApplet;
import processing.core.PVector;

public class Action {
	PVector _direction; 
	
	private static PApplet appRef;
	
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
