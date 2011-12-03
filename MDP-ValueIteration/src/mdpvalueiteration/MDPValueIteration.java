package mdpvalueiteration;

import java.util.ArrayList;
import java.util.HashMap;

import javax.vecmath.Tuple2d;

import com.sun.tools.javac.util.Pair;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;


public class MDPValueIteration extends PApplet {

	private GridModel _gridModel;
	PFont font; 
	
	public void setup() {
		
		font = loadFont("Menlo-Regular-10.vlw");
		
		
		smooth();
		int squareSize = 100;
		_gridModel = new GridModel(4, 3, squareSize, this);
		
		  int[][] board = {
					  {0, 0, 0, 0},
					  {-100, 0, 0, 100},
					};
	        

        for(int x = 0; x < board.length; ++x){
        	for(int y = 0; y < board[x].length; ++y) {
        		GridSquare square = _gridModel.getSquareAtGridPosition(y, x);
        		square.value = board[x][y];
        			
        		if(board[x][y] != 0) {
        			square._color = 25;
        			square.isAbsorbing = true;
        		}
        	}
        }
        
        
//        GridSquare blockedSquare = _gridModel.getSquareAtGridPosition(1, 1);
//        blockedSquare._color = 0;
//        blockedSquare.setPermutable( false );
       
              
        
		
        delay(1000);
        size( board[0].length * squareSize, board.length * squareSize );
	}

	@Override
	public void mouseClicked() {
		super.mouseClicked();
		
		// The algorithm has 'converged' when the values stop changing
        Boolean hasConverged = false;
       
		    for (GridSquare square : _gridModel.get_gridSquareList()) {
		    	if( !square.isPermutable() ) continue;
		    	if( square.isAbsorbing ) continue;
		    			    	
		    	float oldValue = square.value;
		    	calculateMaxValueForSquare( square, 1, -4 );

		    	
		    	// If we considered it true before and this square is different - mark as false
		    	if(hasConverged && oldValue != square.value)
		    		hasConverged = false;
			}        
	}

	public void draw() {
		background(255);
		
		rectMode(CORNERS);
		for (GridSquare square : _gridModel.get_gridSquareList()) {
			square.draw();
			
			if( square._policyDirection != null )
			drawVector(square._policyDirection, square._center.x, square._center.y+10, 10);
		}
		
		
		
		textFont(font); 
		textAlign(CENTER);
		fill(255, 0, 255);
		for (GridSquare square : _gridModel.get_gridSquareList()) {
			text( square.value, square._center.x, square._center.y );
		}
	}
	
	// Renders a vector object 'v' as an arrow at a location xy
	public void drawVector(PVector v, float x, float y, float scayl) {
	  pushMatrix();
	  float arrowsize = 4;
	  // Translate to location to render vector
	  translate(x,y);
	  stroke(255, 0, 255);
	  // Vector heading to get direction (pointing up is a heading of 0)
	  rotate(v.heading2D());
	  // Scale it to be bigger or smaller if necessary
	  float len = v.mag()*scayl;
	  // Draw three lines to make an arrow
	  line(0,0,len,0);
	  line(len,0,len-arrowsize,+arrowsize/2);
	  line(len,0,len-arrowsize,-arrowsize/2);
	  popMatrix();
	}
	
	public void calculateMaxValueForSquare( GridSquare aSquare, float gamma, float r ) {
		
		PVector[] directions = {NORTH, EAST, SOUTH, WEST};
		
		float highestValue = java.lang.Float.NEGATIVE_INFINITY;
		PVector bestDirection = null;
		for (int i = 0; i < directions.length; i++) {
			PVector direction = directions[i];
			
			float squareValue = 0.0f;
			squareValue += calculateValueForMove( aSquare, direction, 0.8f );
			squareValue += calculateValueForMove( aSquare, rotateVector(direction, 1.57079633f * 2 ), 0.2f );
			//squareValue += calculateValueForMove( aSquare, rotateVector(direction, 1.57079633f ), 0.1f );
			
			float totalValue = gamma * (squareValue + r);
			
			if(totalValue > highestValue) {
				
				highestValue = totalValue;
				bestDirection = direction;
				
				aSquare._policyDirection = bestDirection;
			}	
		}
		
		aSquare.value = highestValue;
	}
	
	public PVector rotateVector( PVector aVector, float angle ) {
		PVector v = new PVector( aVector.x, aVector.y );
		float s = sin(angle);
		float c = cos(angle);
 
		float nx = c * v.x - s * v.y;
		float ny = s * v.x + c * v.y;
 
		v.x = round(nx);
		v.y = round(ny);
		
		return v;
	}
	public static PVector NORTH = new PVector(0, -1);
	public static PVector EAST = new PVector(1, 0);
	public static PVector SOUTH = new PVector(0, 1);
	public static PVector WEST = new PVector(-1, 0);
	public float calculateValueForMove( GridSquare aSquare, PVector moveAction, float probability ) {
		int goalX = (int)moveAction.x;
		int goalY = (int)moveAction.y;
		GridSquare square = _gridModel.getSquareAtGridPosition((int)aSquare._gridPosition.x + goalX, (int)aSquare._gridPosition.y + goalY);
		
		if( square == null || !square.isPermutable() ) return aSquare.value * probability;
		
		return square.value * probability;
	}
}
