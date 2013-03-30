package patrick.object.core;

public class Line2 {

	private Vec2 start, end, line;

	public Line2(Vec2 start, Vec2 end) {
		set(start, end);
	}

	public void set(Vec2 start, Vec2 end) {
		this.start = start.copy();
		this.end = end.copy();
		computeLine();
	}

	public void setStart(Vec2 v) {
		start = v;
		computeLine();
	}

	public void setEnd(Vec2 v) {
		end = v;
		computeLine();
	}

	private void computeLine() {
		line = end.copy();
		line.sub(start);
	}
	
	private void computeEnd() {
		end = start.copy();
		end.add(line);
	}
	
	private void computeStart() {
		start = end.copy();
		start.sub(line);
	}

	public Vec2 getStart() {
		return start.copy();
	}

	public Vec2 getEnd() {
		return end.copy();
	}
	
	public Vec2 getLine() {
		return line.copy();
	}
	
	public void rotate(double theta) {
		line.rotate(theta);
		computeEnd();
	}
	
	public void rotateAbout(double theta, Vec2 origin) {
		double cos = Math.cos(theta), sin = Math.sin(theta);
		start.rotateAbout(cos, sin, origin);
		end.rotateAbout(cos, sin, origin);
		computeLine();
	}
	
	public void rotateAboutMidpoint(double theta) {
		Vec2 mid = getMidpoint();
		rotateAbout(theta, mid);
	}
	
	public void rotateAboutEnd(double theta) {
		line.rotate(theta);
		computeStart();
	}
	
	public static Vec2 getMidpoint(Vec2 v1, Vec2 v2) {
		Vec2 midpoint = new Vec2();
		midpoint.x = (v1.x + v2.x) / 2;
		midpoint.y = (v1.y + v2.y) / 2;
		return midpoint;
	}

	public Vec2 getMidpoint() {
		Vec2 mid = line.midpoint();
		mid.add(start);
		return mid;
	}
	
	public double getLengthSq() {
		return line.lengthSq();
	}
	
	public double getLength() {
		return line.length();
	}

	public double getGradient() {
		return line.gradient();
	}

	public double getAngle() {
		return line.angle();
	}

}
