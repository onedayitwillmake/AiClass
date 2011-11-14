package mdpvalueiteration;
import java.util.ArrayList;


import processing.core.PApplet;
import processing.core.PImage;

/**
 * Model that contains a 2 column array of GridSquares
 * 
 * @author onedayitwillmake
 * 
 */
public class GridModel {
	public GridSquare[][] _gridSquares;
	private ArrayList<GridSquare> _gridSquareList;

	private int _squareSize;
	private int _gridColumnCount;
	private int _gridRowCount;

	// Reference to Processing
	private PApplet app;

	public GridModel(int aColumnCount, int aRowCount, int squareSize, PApplet appRef) {
		app = appRef;
		
		_squareSize = squareSize;
		_gridColumnCount = aColumnCount;
		_gridRowCount = aRowCount;

		_gridSquares = new GridSquare[_gridColumnCount][_gridRowCount];
		_gridSquareList = new ArrayList<GridSquare>();
		setupSquares();
	}

	/**
	 * Creates a square for each row/column Assumes
	 * _gridColumnCount/_gridRowCount properties are set
	 */
	public void setupSquares() {
		for (int i = 0; i < _gridColumnCount; ++i) {
			for (int j = 0; j < _gridRowCount; ++j) {
				_gridSquares[i][j] = new GridSquare(i * _squareSize, j * _squareSize, i, j, (int) _squareSize, app);
				_gridSquareList.add( _gridSquares[i][j] );
			}
		}
	}

	/**
	 * Returns the square located at pixel position
	 * @param xpos
	 * @param ypos
	 * @return
	 */
	public GridSquare getSquareAtPixelPosition(int xpos, int ypos) {
		int column = PApplet.floor(xpos / _squareSize);
		int row = PApplet.floor(ypos / _squareSize);
		
		return getSquareAtGridPosition(column, row);
	}
	
	/**
	 * Returns the square located at row, column
	 * @param xpos
	 * @param ypos
	 * @return
	 */
	public GridSquare getSquareAtGridPosition(int column, int row) {
		// Out of bounds
		if (row < 0 || column < 0 || column >= _gridColumnCount || row >= _gridRowCount)
			return null;

		return _gridSquares[column][row];
	}

	public ArrayList<GridSquare> get_gridSquareList() {
		return _gridSquareList;
	}

}