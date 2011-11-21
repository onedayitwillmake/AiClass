package utils;


//StringLengthComparator.java
import java.util.Comparator;

import com.sun.tools.javac.util.Pair;

import oneday.graph.GraphNode;
import search.*;

public class PriorityQueuePairComparator implements Comparator< State > {
    public int compare( State a, State b) {
    	if( a.getCost() > b.getCost()) return 1;
    	if( a.getCost() < b.getCost()) return -1;
    	else return 0;
    }
}
