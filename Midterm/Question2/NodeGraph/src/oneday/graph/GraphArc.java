package oneday.graph;

public class GraphArc {

	public float _cost;
	public GraphNode _node;
	
	public GraphArc( GraphNode aNode, float aCost ) {
		_cost = aCost;
		_node = aNode;
	}
}
