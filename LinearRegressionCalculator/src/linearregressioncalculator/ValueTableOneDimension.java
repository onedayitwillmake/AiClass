package linearregressioncalculator;

import java.util.ArrayList;

import processing.core.PApplet;

public class ValueTableOneDimension {
	private ArrayList<Double> Xi;
	private ArrayList<Double> Yi;
	
	private Double W0;
	private Double W1;
	
	public ValueTableOneDimension() {
		// TODO Auto-generated constructor stub
		Xi = new ArrayList<Double>();
		Yi = new ArrayList<Double>();
	}
	
	
	public Double calculateW0() {
		Double M = new Double( Xi.size() );
		Double QuaterM = 1/M;
		Double SumYi = getSum(Yi);
		
		Double W1_over_M = calculateW1() / M;
		Double SumXi = getSum(Xi);
		
		W0 = (QuaterM*SumYi) - (W1_over_M*SumXi);
		return W0;
	}
	
	public Double calculateW1() {
		assertValidTable();
		
		// Denominator
		Double M = new Double( Xi.size() );
		Double Sum_XYs = getSumXY();
		Double SumX_times_Y = getSum(Xi) * getSum(Yi);
		
		// Numerator
		Double SumX_Squared = getSumSquared( Xi );
		Double Sum_of_allX_Squared = Math.pow( getSum(Xi), 2 );
		
		Double denominator = (M * Sum_XYs) - SumX_times_Y;
		Double numerator = (M * SumX_Squared) - Sum_of_allX_Squared;
		
		W1 = denominator / numerator;
		return W1;
	}
	
	/**
	 * Throws error if table has non-equal amount of values
	 */
	private void assertValidTable() {
		if( Xi.size() != Yi.size() ) {
			throw new Error(" Cannot calculate value for incomplete table, X and Y must have same number of rows ");
		}
	}

	
	/**
	 * Add up each column X to each column Y
	 * @param row
	 * @return
	 */
	private Double getSumXY() {
		Double total = 0.0;
		int index = 0;
		for(Double x : Xi) {
			total += x * Yi.get(index);;
			++index;
		}
		return total;
	}

	/**
	 * For every value in the row add to total, return the total
	 * @param row
	 * @return
	 */
	private Double getSum(ArrayList<Double> row) {
		Double total = 0.0;
		for(Double v : row) {
			total += v;
		}
		return total;
	}
	
	/**
	 * For every value calculate the sum of that value squared
	 * Return the total result
	 * @param row
	 * @return
	 */
	private Double getSumSquared(ArrayList<Double> row) {
		Double total = 0.0;
		for(Double v : row) {
			total += v * v;
		}
		return total;
	}


	@Override
	public String toString() {
		String table = "";
		int index = 0;
		for(Double x : Xi) {
			table += "X: " + x + " \t| Y: " + Yi.get(index) + "\n";
			++index;
		}
		
		return table;
	}


	// Add pair for any number type
	public void addPair( int x, int y ) { Xi.add( new Double(x) ); Yi.add( new Double(y) ); }
	public void addPair( Float x, Float y ) { Xi.add( new Double(x) ); Yi.add( new Double(y) ); }
	public void addPair( Double x, Double y ) { Xi.add( x ); Yi.add( y ); }
	public void addPair( int x, Double y ) { Xi.add( new Double(x) ); Yi.add( new Double(y) ); }
	public void addPair( Double x, int y ) { Xi.add( new Double(x) ); Yi.add( new Double(y) ); }
}
