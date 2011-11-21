package search;

import oneday.graph.GraphNode;

public class State {
	private int _cost;
	private GraphNode _node;
	private State _parent;
	
	public State( GraphNode node, State parent, int cost ) {
		_node = node;
		_parent = parent;
		_cost = cost;
	}

	public State getParent() { return _parent; }
	public GraphNode getNode() { return _node; }
	public int getCost() { return _cost; }
	public void setCost( int aCost ) { _cost = aCost; };
}
