package kmeansclustering;

import java.util.ArrayList;
import java.util.LinkedList;

import processing.core.PVector;


public class KMeans {

	private int kValue;
	private LinkedList<DataPoint> _dataPoints;
	private LinkedList<Cluster> _clusters;
	
	public KMeans( int aKValue ) {
		kValue = aKValue;
		
		_dataPoints = new LinkedList<DataPoint>();
		_clusters = new LinkedList<Cluster>();
	}
	
	public void step() {
		
		for( Cluster cluster : _clusters ) {
			cluster._dataPoints.clear();
		}
		
		for( DataPoint dataPoint : _dataPoints ) {
			float closestDistance = java.lang.Float.POSITIVE_INFINITY;
			Cluster closestCluster = null;
			
			for( Cluster cluster : _clusters ) {
				//if( PVector.dis)
				float dist = dataPoint.getPosition().dist( cluster.getPosition() );
				
				if( dist < closestDistance ) {
					closestDistance = dist;
					closestCluster = cluster;
				}
			}
			
			closestCluster.addDataPoint( dataPoint );
		}
		
		for( Cluster cluster : _clusters ) {
			cluster.reComputeCenter();
		}
		
		
	}
	
	/**
	 * Creates and returns a datapoint
	 * @param x
	 * @param y
	 * @param size
	 * @return
	 */
	public DataPoint createDataPoint( float x, float y, float size ) {
		return new DataPoint(x, y, size);
	}
	/**
	 * Adds a data point
	 * @param aDataPoint
	 * @return
	 */
	public DataPoint addDataPoint( DataPoint aDataPoint ) {
		_dataPoints.add( aDataPoint );
		return aDataPoint;
	}
	/**
	 * Removes a single node
	 * @param aNode
	 */
	public void removeDataPoint( DataPoint aDataPoint ) {
		_dataPoints.remove( aDataPoint );
	}
	
	/**
	 * Creates and returns a cluster
	 * @param x
	 * @param y
	 * @param size
	 * @return
	 */
	public Cluster createCluster( float x, float y ) {
		return new Cluster(x, y);
	}
	/**
	 * Adds a data point
	 * @param aDataPoint
	 * @return
	 */
	public Cluster addCluster( Cluster aCluster ) {
		_clusters.add( aCluster );
		return aCluster;
	}
	/**
	 * Removes a single node
	 * @param aNode
	 */
	public void removeDataPoint( Cluster aCluster ) {
		_clusters.remove( aCluster );
	}
	
	
	public void draw() {
		for( DataPoint aDataPoint : _dataPoints ) {
			aDataPoint.draw();
		}
		
		for( Cluster aCluster : _clusters ) {
			aCluster.draw();
		}
	}
}
