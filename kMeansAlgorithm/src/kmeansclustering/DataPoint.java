package kmeansclustering;

import processing.core.PApplet;
import processing.core.PVector;

public class DataPoint {

	private Cluster _cluster;
	private PVector _position;
	
	// Drawing
	public float _size;
	public int _color = 255;
	
	private PApplet appRef;
	public DataPoint( float x, float y, float size ) {
		appRef = KMeansClustering.getInstance();
		
		
		_size = size;
		setPosition( new PVector( x, y, 0) );
	}
	
	public void draw() {
		appRef.fill( _color );
		appRef.noStroke();
		appRef.ellipse( _position.x, _position.y, _size , _size );
	}

	///// ACCESSORS
	public void setPosition(PVector position) { _position = position; }
	public void setPositionXY( float x, float y ) { _position.set(x, y, 0); }
	public PVector getPosition() { return _position; }

	public void setCluster(Cluster cluster) {
		_cluster = cluster;
	}
	public Cluster getCluster() { return _cluster; }
}
