package kmeansclustering;

import java.util.ArrayList;
import java.util.Set;

import processing.core.PApplet;
import processing.core.PVector;

public class Cluster {
	private PApplet appRef;
	
	private PVector _position;
	public int _color = 128;
	public float _size = 10;
	
	public ArrayList<DataPoint> _dataPoints;

	
	public Cluster( float x, float y) {
		appRef = KMeansClustering.getInstance();

		_dataPoints = new ArrayList<DataPoint>();
		_position = new PVector(x, y);
	}
	
	public void draw() {
		
		
		
		appRef.fill( _color );
		appRef.noStroke();
		appRef.triangle(_position.x - _size, _position.y, _position.x, _position.y - _size, _position.x + _size, _position.y);
		appRef.triangle(_position.x - _size, _position.y, _position.x, _position.y + _size, _position.x + _size, _position.y);
	}
	
	
	public void reComputeCenter() {
		// TODO Auto-generated method stub
		
		PVector centroid = new PVector();
		for( DataPoint point : _dataPoints ) {
			centroid.add( point.getPosition() );
		}
		
		centroid.x /= _dataPoints.size();
		centroid.y /= _dataPoints.size();
		_position.set(centroid);
	}

	///// ACCESSORS
	public void setPosition(PVector position) { _position = position; }
	public void setPositionXY( float x, float y ) { _position.set(x, y, 0); }
	public PVector getPosition() { return _position; }

	public void addDataPoint(DataPoint dataPoint) {
		// TODO Auto-generated method stub
		dataPoint._color = _color;
		_dataPoints.add( dataPoint );
	}
}
