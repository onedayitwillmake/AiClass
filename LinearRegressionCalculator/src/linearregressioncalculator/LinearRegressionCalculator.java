package linearregressioncalculator;

import processing.core.PApplet;


public class LinearRegressionCalculator extends PApplet {
	
	private ValueTableOneDimension table;
	public void setup() {
		table = new ValueTableOneDimension();
		
		// Unit 5.25
//		table.addPair(3, 0);
//		table.addPair(6, -3);
//		table.addPair(4, -1);
//		table.addPair(5, -2);
//		
//		
		// Unit 5.25 quiz
//		table.addPair(2, 2);
//		table.addPair(4, 5);
//		table.addPair(6, 5);
//		table.addPair(8, 8);
//		
		// Homework 3.2
//		table.addPair(0, 3);
//		table.addPair(1, 6);
//		table.addPair(2, 7);
//		table.addPair(3, 8);
//		table.addPair(4, 11);

		
		// Midterm
		table.addPair(1, 2);
		table.addPair(3, 5.2);
		table.addPair(4, 6.8);
		table.addPair(5, 8.4);
		table.addPair(9, 14.8);
		
		println( table.toString() );
		println( "W0: " + table.calculateW0() );
		println( "W1: " + table.calculateW1() );
	}

	public void draw() {
	}
}
