package oneday.graph;

import java.awt.Stroke;
import java.util.ArrayList;
import java.util.LinkedList;

import oneday.NodeGraph;

import processing.core.PApplet;
import processing.core.PVector;

public class GraphNode {
	private NodeGraph appRef;
	
	
	public int color;
	public int _size;
	public PVector _position;
	
	public LinkedList<GraphArc> _arcs;
	public float _heuristic; // This will be a function later but for now its just a value
	
	public GraphNode( int size, float heuristic ) {
		appRef = NodeGraph.INSTANCE;
		
		
		_heuristic = heuristic;
		_size = size;
		_position = new PVector();
		_arcs = new LinkedList<GraphArc>();
	}
	
	/**
	 * Adds an arc between this node and its target
	 * @param aTargetNode
	 * @param aCost
	 */
	public void addArc(GraphNode aTargetNode, float aCost) {
		_arcs.add( new GraphArc(aTargetNode, aCost) );
	}
	
	public boolean removeArc( GraphNode aTargetNode ) {
		GraphArc arc = getArc( aTargetNode );
		if( arc == null ) return false; // Not found
		
		return _arcs.remove( arc );
	}


	/**
	 * Returns an arc that points to a target node
	 * @param aTargetNode
	 */
	private GraphArc getArc(GraphNode aTargetNode) {
		for( GraphArc arc : _arcs ) {
			if( arc._node == aTargetNode ) {
				return arc;
			}
		}
		
		return null;
	}


	public void draw() {
		appRef.fill( 255 );
		appRef.noStroke();
		appRef.ellipse( _position.x, _position.y, _size, _size );
		
		appRef.textFont(NodeGraph.FONT); 
		appRef.textAlign(PApplet.CENTER);
		appRef.fill(255, 0, 255);
		appRef.text(this._heuristic, _position.x, _position.y + _size*0.25f-10 );
		
		appRef.stroke( 255 );
		for( GraphArc arc : _arcs ) {
			appRef.line(_position.x, _position.y, arc._node._position.x, arc._node._position.y);
		}
	}
	
	
	///// ACCESSORS
	public PVector getPosition() { return _position; };
	public float getHeuristic() { return _heuristic; };
	
	// Overload all kinds of ways of setting the position to avoid doing inline
	public void setPositionXY( float x, int y ) { _position.set(x, y, 0);}
	public void setPositionXY( int x, float y ) { _position.set(x, y, 0);}
	public void setPositionXY( int x, int y ) { _position.set(x, y, 0);}
	public void setPositionXY( float x, float y ) { _position.set(x, y, 0);}
	public void setPositionXY( double x, float y ) { _position.set((float)x, y, 0);}
	public void setPositionXY( double x, double y ) { _position.set((float)x, (float)y, 0);}
	public void setPositionXY( float x, double y ) { _position.set(x, (float)y, 0);}
	public void setPosition( PVector aPosition ) { _position = aPosition; }
}
