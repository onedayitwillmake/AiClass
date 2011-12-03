package oneday.graph;

import java.awt.Stroke;
import java.util.ArrayList;
import java.util.LinkedList;

import oneday.NodeGraph;

import processing.core.PApplet;
import processing.core.PVector;
import utils.StaticDrawingHelpers;

public class GraphNode {
	private NodeGraph appRef;
	
	
	public int color;
	public int _size;
	public PVector _position;
	
	private LinkedList<GraphArc> _arcs;
	private LinkedList<GraphNode> _leads; // Opposite of arcs
	
	public float _heuristic; // This will be a function later but for now its just a value
	
	public int debugExpandedAt = 0;
	public GraphNode( int size, float heuristic ) {
		appRef = NodeGraph.INSTANCE;
		
		
		_heuristic = heuristic;
		_size = size;
		color = 255;
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
		aTargetNode.addLead( this );
	}
	
	public boolean removeArc( GraphNode aTargetNode ) {
		GraphArc arc = getArc( aTargetNode );
		if( arc == null ) return false; // Not found
		
		aTargetNode.removeLead( this );
		return _arcs.remove( arc );
	}
	
	public void addLead( GraphNode aParentNode ) {
		if(_leads.contains( aParentNode ) ) return;
		_leads.add( aParentNode );
	}
	
	public void removeLead( GraphNode aParentNode ) {
		if(_leads.contains( aParentNode ) ) return;
		_leads.remove( aParentNode );
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
		appRef.stroke( 10 );
		
		PVector a = _position.get();
		a .normalize();
		
		for( GraphArc arc : _arcs ) {
			appRef.line(_position.x, _position.y, arc._node._position.x, arc._node._position.y);
			
			PVector b = arc._node._position.get();
			b.normalize();
			
			PVector v = PVector.sub(a, b);
			
			StaticDrawingHelpers.drawVector(v, _position.x, _position.y, 1);
		}
		
		appRef.fill( color );
		appRef.noStroke();
		appRef.ellipse( _position.x, _position.y, _size, _size );
		
		appRef.textFont(NodeGraph.FONT); 
		appRef.textAlign(PApplet.CENTER);
		appRef.fill(64, 0, 64);
		appRef.text(this._heuristic, _position.x, _position.y + _size*0.25f-10 );
		
		appRef.fill(255, 0, 255);
		appRef.text(this.debugExpandedAt, _position.x, _position.y + _size*0.25f+5 );
	}
	
	
	///// ACCESSORS
	public LinkedList<GraphArc> getArcs() { return _arcs; };
	public LinkedList<GraphNode> getLeads() { return _leads; };
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
