package passivetemporaldifferenceagent;
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
	public int				 _drawColor;
	public Boolean			isAbsorbing;
	public PVector			_policyDirection;
	
	// Reference to Processing
	private PApplet			app;
	
	// Specific to this type of learning
	public Double utility = 0.0;
	public Double reward = 0.0;
	public Boolean isRoad = false;
	public GridSquare prev;
	
	
	public GridSquare(float xpos, float ypos, int size, PApplet appRef) {
		_position = new PVector(xpos, ypos);
		_gridPosition = new PVector( (float)Math.floor(xpos/size), (float)Math.floor(ypos/size));
		_size = size;
		_center = new PVector(_position.x + _size/2, _position.y + _size/2);
		_color = 190;
		_drawColor = _color;
		isAbsorbing = false;
		_isPermutable = true;
		app = appRef;
	}	
	
	public void drawUsingColor( int aColor ) {
		app.fill( aColor );
		app.stroke( 255 );
		app.rect(_position.x, _position.y, _position.x+_size, _position.y + _size);
	}
	
	public void draw() {
		_drawColor -= (_drawColor - _color) * 0.05;
		drawUsingColor( _drawColor );
	}
	
	public void touch() {
		_drawColor = (int)((float)_color * 0.0001);
	}

	public void setPermutable(boolean isPermutable) { _isPermutable = isPermutable; }
	public boolean isPermutable() { return _isPermutable; }}
