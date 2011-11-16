package exploratoryagent;

import java.util.Iterator;
import java.util.Map;

import exploratoryagent.GridModel;
import exploratoryagent.GridSquare;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;


public class ExploratoryAgent extends PApplet {

	private GridModel _worldModel;
	private Policy _policyMap;
	private TDAgent _agent;

	
	private int squareSize  = 100;
	private PFont font;

	
	
	public void setup() {
		frameRate = 1.0f;
		INSTANCE = this;
		font = loadFont("Menlo-Regular-10.vlw");

		
		int[][] board = {
				  {0, 0, 0, 1},
				  {0, -999, 0, -1},
				  {0, 0, 0, 0},
				};

		setupGridWorld(board);
		setupPolicy(board);

		_agent = new TDAgent(_policyMap, _worldModel);

		
		delay(1000);
		size(board[0].length * squareSize, board.length * squareSize);
	}

	public void setupGridWorld( int[][] board ) {
		_worldModel = new GridModel(board[0].length, board.length, squareSize, this);
	      for(int x = 0; x < board.length; ++x){
	      	for(int y = 0; y < board[x].length; ++y) {
	      		
	      		GridSquare square = _worldModel.getSquareAtGridPosition(y, x);
	      		square.reward = (double)board[x][y];
	      			
	      		if(board[x][y] != 0) {
	      			
	      			square._color = 25;
	      			square.isAbsorbing = true;
	      		}
	      		if(board[x][y] == -999) {
	      			square._color = 25;
	      			square.setPermutable( false );
	      		}
	      	}
	      }
	}
	
	public void setupPolicy( int[][] board ) {
		
		PVector[][] policy = {
				{ new PVector(1, 0), new PVector(1, 0), new PVector(1, 0), null },
				{ new PVector(0, -1), null, null, null },
				{ new PVector(0, -1), null, null, null }, };

		_policyMap = new Policy(board[0].length, board.length, squareSize, this);
		
		for (int x = 0; x < board.length; ++x) {
			for (int y = 0; y < board[x].length; ++y) {
				if (policy[x][y] != null) {
					Action action = _policyMap.getActionAtGridPosition(y, x);
					action.setActionAndProbability(policy[x][y], 1.0);
				}
			}
		}
	}
	
	public void draw() {
		
		
		background(255);		
		rectMode(CORNERS);
		for (GridSquare square : _worldModel.get_gridSquareList()) {
			square.draw();

			Action policyDirection =  _policyMap.getActionAtGridPosition( (int)square._gridPosition.x, (int) square._gridPosition.y );
			if( policyDirection != null && policyDirection.getAction() != null )
				drawVector( policyDirection.getAction(), square._center.x, (float)(square._center.y-squareSize*0.5f)+15, 10);
		}
		
//		_agent.getState().drawUsingColor( 160 );
		
		
		textFont(font); 
		textAlign(CENTER);
		fill(255, 0, 255);
		for (Map.Entry<GridSquare, Double> entry : _agent.N.entrySet() ) {
		    	GridSquare square = entry.getKey();
				text( "N:" + entry.getValue().floatValue(), square._center.x, square._center.y );
		}
		
		for (Map.Entry<GridSquare, Double> entry : _agent.U.entrySet() ) {
	    	GridSquare square = entry.getKey();
			text( "U:" + entry.getValue().floatValue(), square._center.x, square._center.y + 10 );
		}
		
		
	}
	
	// Renders a vector object 'v' as an arrow at a location xy
	public void drawVector(PVector v, float x, float y, float scayl) {
		pushMatrix();
		float arrowsize = 4;
		// Translate to location to render vector
		translate(x, y);
		stroke(255, 0, 255);
		// Vector heading to get direction (pointing up is a heading of 0)
		rotate(v.heading2D());
		// Scale it to be bigger or smaller if necessary
		float len = v.mag() * scayl;
		// Draw three lines to make an arrow
		line(0, 0, len, 0);
		line(len, 0, len - arrowsize, +arrowsize / 2);
		line(len, 0, len - arrowsize, -arrowsize / 2);
		popMatrix();
	}
	
	
	
	@Override
	public void mouseClicked() {
		super.mouseClicked();
		
		if( !_agent.canAdvance ) {
			_agent.runAgain();
		}
		
		_agent.advance();
		
		
	}



	private static PApplet INSTANCE;
	public static PApplet getInstance() {
		return INSTANCE;
	} 
}
