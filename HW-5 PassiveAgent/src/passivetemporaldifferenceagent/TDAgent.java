package passivetemporaldifferenceagent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import processing.core.PVector;
import strategies.comparators.ManhattanDistanceComparator;

public class TDAgent {
	
	Policy _pi;
	GridModel _world;
	
	// Table of Utilities
	public Map<GridSquare, Double> U = new HashMap<GridSquare, Double>();
	// Table of frequencies 
	public Map<GridSquare, Double> N = new HashMap<GridSquare, Double>();

	private GridSquare	_goalSquare;
	private GridSquare	_startState;	 // Current grid square
	private GridSquare	_state;	 // Current grid square
	private Action		_action; // As given according to policy
	private Double		_reward;
	
	// learning rate
	// gamma
	private double learningRate = 1.0;
	private double gamma = 1.0;
	
	public Boolean canAdvance = true;
	private PApplet appRef;
	public TDAgent( Policy pi, GridModel world ) {
		appRef = PassiveTemporalDifferenceAgent.getInstance();
		
		_pi = pi;
		_world = world;
	}
	
	public void advance() {
		if( !canAdvance )
			return;
		
		GridSquare idealSquare = getIdealSquare();
		PVector idealMove = PVector.sub( idealSquare._gridPosition, _state._gridPosition );
		
		

		
		float chance = appRef.random(1);
		
		// 80% chance of going in intended direction
		// 10% chance of moving 90 degrees left 
		// 10% chance of moving 90 degrees right
		if( chance < 0.8 ) idealMove = idealMove;
		else if (chance < 0.9 ) idealMove = rotateVector(idealMove, 90 * PApplet.PI / 180 );
		else idealMove = rotateVector(idealMove, -90 * PApplet.PI / 180 );
		
//		_action = _pi.getActionAtGridPosition( _world, getState()._gridPosition );
		GridSquare sPrime = _world.getSquareAtGridPosition( PVector.add(getState()._gridPosition, idealMove ) );
		
		// Invalid location - so stay where we are
		if( sPrime == null ) 
			sPrime = _state;
		
		// If SPrime is new then U[sPrime] = sPrime.reward
		if ( !U.containsKey(sPrime) ) {
			U.put(sPrime, sPrime.reward);

		}
		
		// If s is not null, then
		if ( getState() != null ) {
			
			// Incriment N or set it to 1
			if( N.containsKey( getState() ) ) N.put( getState(), N.get( getState() ) + 1);
			else N.put( getState(), 1.0);
			
	
			// learningRate dividied (Number of times we've visited before + 1)
			Double alpha = learningRate /  ( N.get(getState()) + 1); // 1 over N + 1
			
			// Current utility of this state - 
			// because java is annoying sometimes we have to do it via this conditional incase it doesn't exist in our hash
			Double currentUtility;
			if( U.containsKey(_state) ) currentUtility = U.get(_state);
			else currentUtility = 0.0;
			
			
						
			// Reward fall off
			Double newUtility = currentUtility + alpha * ( getState().reward + gamma * ( U.get( sPrime ) - currentUtility) );
			U.put(_state, newUtility );
		}
		
		
		// Terminal state - kill it
		if( sPrime.isAbsorbing ) {
			canAdvance = false;
		} else {
			_state = sPrime;
			_state.touch();
		}
	}

	private GridSquare getIdealSquare() {
		
		 ArrayList<GridSquare> possibleDirections = new ArrayList<GridSquare>();
		 
		 possibleDirections.add( _world.getSquareAtGridPosition( PVector.add(getState()._gridPosition, Action.NORTH ) ) );
		 possibleDirections.add( _world.getSquareAtGridPosition( PVector.add(getState()._gridPosition, Action.EAST ) ) );
		 possibleDirections.add( _world.getSquareAtGridPosition( PVector.add(getState()._gridPosition, Action.SOUTH ) ) );
		 possibleDirections.add( _world.getSquareAtGridPosition( PVector.add(getState()._gridPosition, Action.WEST ) ) );
		
		
		ManhattanDistanceComparator manhattanDistanceComparator = new ManhattanDistanceComparator( _goalSquare );
		Collections.sort( possibleDirections, manhattanDistanceComparator );
		
		return possibleDirections.get( possibleDirections.size() - 1 );
	}
	
	
	

	/**
	 * Sets the agent to the start position again and runs the policy
	 */
	public void runAgain() {
		setState( getStartState() );
		canAdvance = true;
	}
	
	
	public GridSquare setState(GridSquare _state) {
		this._state = _state;
		return _state;
	}
	public GridSquare getState() { return _state; }
	public void setStartState(GridSquare _startState) { this._startState = _startState; }
	public GridSquare getStartState() { return _startState; }

	public void setGoal(GridSquare goalSquare) {
		_goalSquare = goalSquare;
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
