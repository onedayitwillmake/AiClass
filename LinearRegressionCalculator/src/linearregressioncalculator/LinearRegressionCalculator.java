package linearregressioncalculator;

import processing.core.PApplet;


public class LinearRegressionCalculator extends PApplet {
	
	private ValueTableOneDimension table;
	public void setup() {
		table = new ValueTableOneDimension();
//		table.addPair(3, 0);
//		table.addPair(6, -3);
//		table.addPair(4, -1);
//		table.addPair(5, -2);
		
		
		table.addPair(2, 2);
		table.addPair(4, 5);
		table.addPair(6, 5);
		table.addPair(8, 8);
		
		
		println( table.toString() );
		println( table.calculateW0() );
		println( table.calculateW1() );
	}

	public void draw() {
	}
}
