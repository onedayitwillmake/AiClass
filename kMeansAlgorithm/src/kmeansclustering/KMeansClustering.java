package kmeansclustering;

import processing.core.PApplet;
import processing.core.PImage;


public class KMeansClustering extends PApplet {

	private KMeans kMeans;
	public PImage b;

	public void setup() {
		INSTANCE = this;
		
		b = loadImage("data/image.png");

		
		size(b.width, b.height);
		frameRate(5);
		background(0);
		
		int kValue = 2;
		kMeans = new KMeans( kValue );
		for( int i = 0; i < kValue; ++i ) {
			Cluster aCluster = kMeans.addCluster( kMeans.createCluster( random(width), random(height) ) );
			aCluster._color = i == 0 ? 128 : 255;
		}
		
//		int numDataPoints = 100;
//		for( int i = 0; i < numDataPoints; ++i ) {
//			kMeans.addDataPoint( kMeans.createDataPoint( random(width), random(height), 5 ) );
//		}
		
	}

	public Boolean makingPoints = true;
	@Override
	public void mouseClicked() {
		// TODO Auto-generated method stub
		if( !makingPoints ) {
			kMeans.step();
		} else {
			kMeans.addDataPoint( kMeans.createDataPoint( mouseX, mouseY, 5 ) );
		}
		super.mouseClicked();
	}

	@Override
	public void keyPressed() {
		makingPoints = true;
		// TODO Auto-generated method stub
		super.keyPressed();
	}

	@Override
	public void keyReleased() {
		makingPoints = false;
		// TODO Auto-generated method stub
		super.keyReleased();
	}

	public void draw() {
		background(0);
		image(b,0,0);

		kMeans.draw();
	}

	private static PApplet INSTANCE;
	public static PApplet getInstance() {
		return INSTANCE;
	}
}
