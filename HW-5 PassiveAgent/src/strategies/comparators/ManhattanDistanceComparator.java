/**
 * Used to sort a set of polygonal points based on the barycenter
 * http://gamedev.stackexchange.com/questions/13229/sorting-array-of-points-in-clockwise-order
 */
package strategies.comparators;

import java.util.Comparator;

import passivetemporaldifferenceagent.*;

import processing.core.PApplet;
import processing.core.PVector;

public class ManhattanDistanceComparator implements Comparator<GridSquare>  {
	private GridSquare _goal; 
	public ManhattanDistanceComparator(GridSquare goal) {
		_goal = goal;
	}
	
	public int compare(GridSquare stateA, GridSquare stateB) {
		
		// Promote not-null 
		if( stateB == null && stateA != null ) return 1;
		if( stateA == null && stateB != null ) return -1;
		if( stateA == null && stateB == null ) return 0;
		
		// Promote roads
		if( stateA.isRoad && !stateB.isRoad ) return 1;
		if( stateB.isRoad && !stateA.isRoad ) return -1;
		
		
		// Promote based on distance
		PVector subA = PVector.sub( stateA._center, _goal._center );
		float distASq = subA.dot( subA );
		
		PVector subB = PVector.sub( stateB._center, _goal._center );
		float distBSq = subB.dot( subB );
		

		if( distASq < distBSq ) return 1;
		else if ( distBSq < distASq ) return -1;
		
		// Two are equally close - promote North
		if( stateA._gridPosition.y < stateB._gridPosition.y ) return 1;
		if( stateB._gridPosition.y < stateA._gridPosition.y ) return -1;
		
		PApplet.println( "STILL THE SAME!" );
		return 0;
		
	}
	
}