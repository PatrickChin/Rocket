package patrick.geom;

public class Geom {
	
	public static final double twoPI = Math.PI * 2;
	
	public static double normalizeAngle(double theta) {
		if ((theta < -twoPI) || (theta > twoPI)) {
			theta %= twoPI;
		}
		if (theta < 0) {
			theta += twoPI;
		}
		return theta;
	}

}
