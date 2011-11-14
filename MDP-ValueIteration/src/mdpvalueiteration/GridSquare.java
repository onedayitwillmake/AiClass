package mdpvalueiteration;
import processing.core.PApplet;
import processing.core.PVector;

public class GridSquare {
	
	// Square Info
	public PVector			_position;
	public PVector 			_gridPosition;
	public int				_size;
	public PVector			_center;
	
	private boolean			_isPermutable;
	// Color
	public int				 _color;
	public Boolean			isAbsorbing;
	public PVector			_policyDirection;
	
	// Reference to Processing
	private PApplet			app;
	public float value;
	
	public GridSquare(float xpos, float ypos, int row, int column, int size, PApplet appRef) {
		_position = new PVector(xpos, ypos);
		_gridPosition = new PVector(row, column);
		_size = size;
		_center = new PVector(_position.x + _size/2, _position.y + _size/2);
		_color = 225;
		isAbsorbing = false;
		_isPermutable = true;
		app = appRef;
	}	
	
	public void draw() {
		app.fill( _color );
		app.stroke( 255 );
		app.rect(_position.x, _position.y, _position.x+_size, _position.y + _size);
	}

	public void setPermutable(boolean isPermutable) { _isPermutable = isPermutable; }
	public boolean isPermutable() { return _isPermutable; }}
