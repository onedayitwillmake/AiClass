package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import com.sun.tools.javac.util.Pair;

import oneday.NodeGraph;
import oneday.graph.GraphNode;
import processing.core.PApplet;
import utils.PriorityQueuePairComparator;
import utils.StaticDrawingHelpers;

public class UniformCostSearch {
	private boolean solutionFound;
	
	private State _node;
	private PriorityQueue<State> _frontier;
	private ArrayList<GraphNode> _explored;
	
	private Problem _problem;
	private int _stepCost;
	
	public int debugExpandCount = 0;
	private NodeGraph appRef;

	
	
	public UniformCostSearch( GraphNode startNode, Problem aProblem, int stepCost ) {
		appRef = NodeGraph.INSTANCE;
		
		_stepCost = stepCost;
		_problem = aProblem;
		_node = new State( startNode, null, 0);
		_explored = new ArrayList<GraphNode>();
		_frontier = new PriorityQueue< State >( 10, new PriorityQueuePairComparator() );
		_frontier.add( _node );
	}
	
	public Boolean advance() {
		if( solutionFound ) return true;
		
		if( _frontier.isEmpty() ) {
			PApplet.println("Frontier is empty! Aborting...");
			return false;
		}
		
		_node = _frontier.poll();
		_node.getNode().debugExpandedAt = ++debugExpandCount;
		PApplet.println( "Expanding node with totalcost of: "  + _node.getCost() + " And Heuristic of " + _node.getNode().getHeuristic() );
		
		if( _problem.goalTest(_node.getNode() ) ) {
			onSolutionFound( _node );
		}
		_explored.add( _node.getNode() );
		
		ArrayList<State> actions = _problem.getActions( _node );
		for( State child : actions ) {
			int cost = getCumulativeCost( child );
			child.setCost( cost );
		}
		
		
		
		for( State child : actions ) {
			State frontierCloneState = isInFrontier(child);
			
			

			if( !isInExploredSet(child) && frontierCloneState == null) {
				child.getNode().color *= 0.9;
				
				_frontier.add( child );
			} else if ( frontierCloneState != null && frontierCloneState.getCost() > child.getCost() ) {
				child.getNode().color *= 0.9;
				
				_frontier.remove( frontierCloneState );
				_frontier.add( child );
			}
		}
		
		return true;
	}
	
	private State isInFrontier(State child) {
		State state = child;
		
		for( State frontierNode : _frontier ) {
			if( frontierNode.getNode() == child.getNode() )
				return state;
		}
		
		return null;
	}

	
	private Boolean isInExploredSet( State aSearchNode ) {
		
		if( _explored.contains( aSearchNode.getNode() ) ) 
			return true;
	
		
		return false;
	}
	
	
	private int getCumulativeCost(State anAction) {
		int totalCost = (int)anAction.getNode().getHeuristic();
		totalCost += _stepCost;
		
		State state = anAction;
		while( state != null ) {
			totalCost += state.getCost();
			state = state.getParent();
		}
		
		return totalCost;
	}
	

	public void onSolutionFound(State aState) {
		PApplet.println("SolutionFound!");

		State state = aState;
		while (state != null) {
			PApplet.println(state.getNode().getHeuristic());
			state = state.getParent();
		}
		
//		solutionFound = true;
	}
}
