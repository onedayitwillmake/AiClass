package oneday;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


import oneday.graph.Graph;
import oneday.graph.GraphNode;
import processing.core.PApplet;
import processing.core.PFont;
import search.Problem;
import search.UniformCostSearch;
import utils.StaticDrawingHelpers;


public class NodeGraph extends PApplet {

	private Graph _graph;
	private UniformCostSearch uniformCostSearch;
	
	public void setup() {
		
		INSTANCE = this;
		StaticDrawingHelpers.setAppRef( (PApplet) this );
		FONT = loadFont("Menlo-Regular-10.vlw");

		
		size(800, 600);
		frameRate(60);
		background(0);
		noStroke();
		smooth();
		
		_graph = new Graph();
		
		createGraph();
	}
	
	/**
	 * I don't know yet a nice way to abstractly represent this specific data structure
	 * For right now I don't care, this bit of the code can be interchanged per problem for now (mid term)
	 * But I look forward to constructing the graphics in more beautiful ways later :)
	 */
	public void createGraph() {
		int size = 50;
		LinkedList<Integer> heuristics = new LinkedList<Integer>( Arrays.asList(15, 11, 2, 8, 3, 9, 7, 6, 5, 20, 10, 0) );

		// Head
		GraphNode head = _graph.addNode( _graph.createNode(size, (float)heuristics.pop() )  );
		head.setPositionXY(width*0.5f, height*0.5f);
		
		
		float decendentCount = 5;
		GraphNode specialLink = null; // The special link that goes straight to the goal
		GraphNode goalNode = null;
		
		for( int i = 0; i < decendentCount; ++i ) {
			GraphNode decendent = _graph.addNode( _graph.createNode(size,  (float)heuristics.pop() ) );
			float spread = 0.5f;
			decendent.setPositionXY( width*0.5 + (float)(i+0.5f)/decendentCount * (width*spread) - (0.5*width*spread), head.getPosition().y + (size*1.5) );
			head.addArc(decendent, 1);
			
			if( i == 0 ) {
				GraphNode grandDecendent = _graph.addNode( _graph.createNode(size,  (float)heuristics.pop() ) );
				grandDecendent.setPositionXY( decendent._position.x - size*0.5, decendent.getPosition().y + (size*1.5) );
				decendent.addArc(grandDecendent, 1);
			}
			
			if( i == 1 ) {
				for( int j = 0; j < 2; ++j ) {
					GraphNode grandDecendent = _graph.addNode( _graph.createNode(size,  (float)heuristics.pop() ) );
					grandDecendent.setPositionXY( decendent._position.x - (size*0.6 * (j==0?1:-1) ), decendent.getPosition().y + (size*1.6) );
					decendent.addArc(grandDecendent, 1);
				}
			}
			
			
			if( i == 3 ) {
				for( int j = 0; j < 2; ++j ) {
					GraphNode grandDecendent = _graph.addNode( _graph.createNode(size,  (float)heuristics.pop() ) );
					grandDecendent.setPositionXY( decendent._position.x - (size*0.6 * (j==0?1:-1) ), decendent.getPosition().y + (size*1.6) );
					decendent.addArc(grandDecendent, 1);
					
					specialLink = grandDecendent;

				}
			}
			
			if( i == 4 ) {
				GraphNode grandDecendent = _graph.addNode( _graph.createNode(size,  (float)heuristics.pop() ) );
				grandDecendent.setPositionXY( decendent._position.x + size, decendent.getPosition().y + (size*1.6) );
				decendent.addArc(grandDecendent, 1);
				
				// Add that one link that goes to the goal
				specialLink.addArc(grandDecendent, 1);
				
				goalNode = grandDecendent;
			}
		}
		
		Problem problem = new Problem( _graph, goalNode );
		uniformCostSearch = new UniformCostSearch( head, problem, 10 );
		
	}
	
	public void draw() {
		background(0);
		_graph.draw();
	}
	
	@Override
	public void mouseClicked() {
		
		uniformCostSearch.advance();
		super.mouseClicked();
	}

	public static NodeGraph INSTANCE;
	public static PFont FONT;

}

