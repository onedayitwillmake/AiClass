package exploratoryagent;
import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import processing.core.PVector;

public class TDAgent {
	
	Policy _pi;
	GridModel _world;
	
	// Table of Utilities
	public Map<GridSquare, Double> U = new HashMap<GridSquare, Double>();
	// Table of frequencies 
	public Map<GridSquare, Double> N = new HashMap<GridSquare, Double>();

	private GridSquare	_startState;	 // Current grid square
	private GridSquare	_state;	 // Current grid square
	private Action		_action; // As given according to policy
	private Double		_reward;
	
	// learning rate
	// gamma
	private double learningRate = 1.0;
	private double gamma = 1.0;
	
	public Boolean canAdvance = true;
	public TDAgent( Policy pi, GridModel world ) {
		_pi = pi;
		_world = world;
		
		// This is given for our problem
		setStartState( _world.getSquareAtGridPosition( 0, 2) );
		setState( getStartState() );
	}
	
	public void advance() {
		if( !canAdvance )
			return;
		
		_action = _pi.getActionAtGridPosition( getState()._gridPosition );
		GridSquare sPrime = _world.getSquareAtGridPosition( PVector.add(getState()._gridPosition, _action.getAction() ) );
		
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
			
		}
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
}
