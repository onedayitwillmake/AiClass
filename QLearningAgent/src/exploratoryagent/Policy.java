package exploratoryagent;
import java.util.ArrayList;


import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Model that contains a 2 column array of Actions
 * 
 * @author onedayitwillmake
 * 
 */
public class Policy {
	public Action[][] _gridActions;
	private ArrayList<Action> _gridActionList;

	private int _squareSize;
	private int _gridColumnCount;
	private int _gridRowCount;
	
	// Reference to Processing
	private PApplet app;
	

	public Policy(int aColumnCount, int aRowCount, int squareSize, PApplet appRef) {
		app = appRef;
		
		_squareSize = squareSize;
		_gridColumnCount = aColumnCount;
		_gridRowCount = aRowCount;

		_gridActions = new Action[_gridColumnCount][_gridRowCount];
		_gridActionList = new ArrayList<Action>();
		setupActions();
	}

	/**
	 * Creates a square for each row/column Assumes
	 * _gridColumnCount/_gridRowCount properties are set
	 */
	public void setupActions() {
		for (int i = 0; i < _gridColumnCount; ++i) {
			for (int j = 0; j < _gridRowCount; ++j) {
				_gridActions[i][j] = new Action( null, 1.0 );
				_gridActionList.add( _gridActions[i][j] );
			}
		}
	}

	/**
	 * Returns the square located at pixel position
	 * @param xpos
	 * @param ypos
	 * @return
	 */
	public Action getActionAtPixelPosition(int xpos, int ypos) {
		int column = PApplet.floor(xpos / _squareSize);
		int row = PApplet.floor(ypos / _squareSize);
		
		return getActionAtGridPosition(column, row);
	}
	
	/**
	 * Returns the square located at row, column
	 * @param xpos
	 * @param ypos
	 * @return
	 */
	public Action getActionAtGridPosition(int column, int row) {
		// Out of bounds
		if (row < 0 || column < 0 || column >= _gridColumnCount || row >= _gridRowCount)
			return null;

		return _gridActions[column][row];
	}
	
	public Action getActionAtGridPosition(PVector asVector) {
		return this.getActionAtGridPosition((int)asVector.x, (int)asVector.y);
	}

	public ArrayList<Action> get_gridList() {
		return _gridActionList;
	}
	
	

}