package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import oneday.graph.Graph;
import oneday.graph.GraphArc;
import oneday.graph.GraphNode;

public class Problem {
	private GraphNode _goal;
	private Graph _graph;
	public Problem( Graph aGraph, GraphNode aGoal ){
		_graph = aGraph;
		_goal = aGoal;
	}
	
	public Boolean goalTest( GraphNode aNode ) {
		return aNode == _goal;
	}

	public ArrayList<State> getActions(State aState ) {
		ArrayList<State> actions = new ArrayList<State>();
		for( GraphArc arc : aState.getNode().getArcs() ) {
			actions.add( new State( arc._node, aState, 0 ) );
		}
		return actions;
	}
	
}
