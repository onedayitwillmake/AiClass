package oneday.graph;

import java.util.ArrayList;
import java.util.LinkedList;

import oneday.NodeGraph;

import processing.core.PApplet;

/**
 * A graph contains and manages links between GraphNodes
 * @author onedayitwillmake
 */
public class Graph {
	LinkedList<GraphNode> _nodes;
	
	private PApplet appRef;
	public Graph() {
		appRef = NodeGraph.INSTANCE;
		_nodes = new LinkedList<GraphNode>();
	}
	
	/**
	 * Creates a single graphnode
	 * @param size
	 * @return
	 */
	public GraphNode createNode( int size, float heuristic  ) {
		return new GraphNode( size, heuristic );
	}
	
	/**
	 * Adds a node to our list
	 * @param aNode
	 * @return
	 */
	public GraphNode addNode( GraphNode aNode ) {
		_nodes.add( aNode );
		return aNode;
	}
	
	/**
	 * Removes a single node
	 * @param aNode
	 */
	public void removeNode( GraphNode aNode ) {
		_nodes.remove( aNode );
	}
	
	/**
	 * Adds a unidirectional graph node between a source, and a target with a given cost
	 * Both nodes must have already been added
	 * @param aSourceNode
	 * @param aTargetNode
	 * @param cost
	 */
	public void addSingleArc( GraphNode aSourceNode, GraphNode aTargetNode, float cost ) {

		// Check that both exist
		if( !_nodes.contains( aSourceNode ) || !_nodes.contains( aTargetNode ) ) 
			throw new Error("Both nodes must be added first!");
			
		aSourceNode.addArc( aTargetNode, cost );
	}
	
	/**
	 * Adds a bidirectional arc between two nodes with a given cost
	 * @param aSourceNode
	 * @param aTargetNode
	 * @param cost
	 */
	public void addMutualArcs( GraphNode aSourceNode, GraphNode aTargetNode, float cost ) {

		// Check that both exist
		if( !_nodes.contains( aSourceNode ) || !_nodes.contains( aTargetNode ) ) 
			throw new Error("Both nodes must be added first!");
			
		aSourceNode.addArc( aTargetNode, cost );
		aTargetNode.addArc( aSourceNode, cost );
	}

	public void draw() {
		for( GraphNode aNode : _nodes ) {
			aNode.draw();
		}
	}
}
