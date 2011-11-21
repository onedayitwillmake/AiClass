package utils;

import processing.core.PApplet;
import processing.core.PVector;

public class StaticDrawingHelpers {
	private static PApplet appRef = null;
	
	public static void setAppRef( PApplet anAppRef ) {
		StaticDrawingHelpers.appRef = anAppRef;
	}
	
	/**
	 * Renders a vector object 'v' as an arrow at a location xy, at a 'scale' 
	 * @param v
	 * @param x
	 * @param y
	 * @param scale
	 */
	public static void drawVector(PVector v, float x, float y, float scale) {
		float arrowsize = 4;
		appRef.pushMatrix();

		// Translate to location to render vector
		appRef.translate(x, y);
		appRef.stroke(255, 0, 255);
		appRef.rotate(v.heading2D()); 		// Vector heading to get direction (pointing up is a heading of 0)

		// Scale it to be bigger or smaller if necessary
		float len = v.mag() * scale;

		// Draw three lines to make an arrow
		appRef.line(0, 0, len, 0);
		appRef.line(len, 0, len - arrowsize, +arrowsize / 2);
		appRef.line(len, 0, len - arrowsize, -arrowsize / 2);
		appRef.popMatrix();
	}
}
