package passivetemporaldifferenceagent;

import java.util.Iterator;
import java.util.Map;

import passivetemporaldifferenceagent.GridModel;
import passivetemporaldifferenceagent.GridSquare;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;


public class PassiveTemporalDifferenceAgent extends PApplet {

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
				{0,0,0,0,0,0,0,0},
				{1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,0,1},
				{1,1,1,1,1,1,1,1},
				};

		setupGridWorld(board);
		setupPolicy(board);
		
		GridSquare goalSquare = _worldModel.getSquareAtGridPosition(7, 1);
		goalSquare._color = 128;
		goalSquare.reward = 100.0;
		goalSquare.isAbsorbing = true;
		
		_agent = new TDAgent(_policyMap, _worldModel);
		_agent.setStartState( _worldModel.getSquareAtGridPosition(0, 1) );
		_agent.setState( _agent.getStartState() );
		_agent.setGoal( goalSquare );
		
		
	
		frameRate(60);
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
	      			
	      			square._color = 255;
	      			square.isRoad = true;
	      		}
	      	}
	      }
	}
	
	public void setupPolicy( int[][] board ) {
		_policyMap = new Policy(board[0].length, board.length, squareSize, this);
	}
	
	public void draw() {
		
		if( !_agent.canAdvance ) {
			_agent.runAgain();
		}
		
		for(int i = 0; i < 1000; ++i )
			_agent.advance();
		
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
}
