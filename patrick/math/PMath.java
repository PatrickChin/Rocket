package patrick.math;

public class PMath {
	
	public static double atanh(double d) {
		return Math.log((1 + d) / (1 - d)) * 0.5d;
	}

}
