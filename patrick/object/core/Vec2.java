package patrick.object.core;


public class Vec2 extends java.awt.geom.Point2D.Float {
	
	private static final long serialVersionUID = -5338129004851001465L;

	public Vec2() {
		x = 0;
		y = 0;
	}

	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vec2(Vec2 v) {
		x = v.x;
		y = v.y;
	}

	public void add(Vec2... vs) {
		for (Vec2 v : vs) {
			x += v.x;
			y += v.y;
		}
	}

	public void sub(Vec2... vs) {
		for (Vec2 v : vs) {
			x -= v.x;
			y -= v.y;
		}
	}

	public void scale(float scalar) {
		x *= scalar;
		y *= scalar;
	}

	public void negate() {
		x = -x;
		y = -y;
	}

	public void reciprocate() {
		x = 1 / x;
		y = 1 / y;
	}

	public void square() {
		x *= x;
		y *= y;
	}

	public void rotate(double theta) {
		double cos = Math.cos(theta), sin = Math.sin(theta);
		rotate(cos, sin);
	}

	public void rotate(double cos, double sin) {
		float i_ = (float) (x * cos - y * sin);
		y = (float) (x * sin + y * cos);
		x = i_;
	}
	
	public void rotateAbout(double theta, Vec2 origin) {
		sub(origin);
		rotate(theta);
		add(origin);
	}
	
	public void rotateAbout(double cos, double sin, Vec2 origin) {
		sub(origin);
		rotate(cos, sin);
		add(origin);
	}

	public void cross(Vec2 v) {
		float i_ = (x * v.x) - (y * v.y);
		y = (x * v.y) + (y * v.x);
		x = i_;
	}

	public float dot(Vec2 v) {
		return x * v.x + y * v.y;
	}

	public double angle() {
		return Math.atan2(y, x);
	}

	public double lengthSq() {
		return (x * x) + (y * y);
	}

	public double length() {
		return Math.sqrt((x * x) + (y * y));
	}

	public float gradient() {
		return y / x;
	}
	
	public Vec2 midpoint() {
		return new Vec2(x * 0.5f, y * 0.5f);
	}

	public double acuteAngleTo(Vec2 v) {
		double a = Math.abs(angle() - v.angle());
		if (a <= Math.PI)
			return a;
		return Math.PI * 2 - a;
	}

	public double angleTo(Vec2 v) {
		double dot = dot(v);
		if (dot == 0)
			return Math.PI / 2;
		double mags = Math.sqrt((x * x + y * y) * (v.x * v.x + v.y * v.y));
		return Math.acos(dot(v) / mags);
	}

	public double component(double angle) {
		return (length() * Math.cos(angle() - angle));
	}

	public boolean isOrthogonal(Vec2 v) {
		return dot(v) == 0;
	}

	public void set(double length, double angle) {
		if (length < 0) {
			length = -length;
			angle %= Math.PI * 2;
			angle += Math.PI;
		}
		x = (float) (length * Math.cos(angle));
		y = (float) (length * Math.sin(angle));
	}

	public void set(double length, double sin, double cos) {
		x = (float) (length * cos);
		y = (float) (length * sin);
	}

	public void setAngle(double angle) {
		set(length(), angle);
	}

	public void setAngle(double sin, double cos) {
		set(length(), sin, cos);
	}

	public final void setLength(float len) {
		x *= len;
		y *= len;
		normalise();
	}

	public void normalise() {
		double l = length();
		x /= l;
		y /= l;
	}

	public Vec2 copy() {
		return new Vec2(x, y);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vec2) {
			Vec2 v = (Vec2) o;
			return v.x == x && v.y == y;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%s[i=%.3f,j=%.3f]", ""
		/* Vector.class.getSimpleName() */, x, y);
	}

	public String toString2() {
		return String.format("%s[length=%.3f,angle=%.3f\u00B0]",
				Vec2.class.getSimpleName(), length(), Math.toDegrees(angle()));
	}

	public void print() {
		System.out.println(toString());
	}
	
	//private Cache cache = new Cache();
	//private class Cache {
	//	public boolean[] clean = new boolean[3];
	//	public double length, lengthSq, angle, gradient;
	//}
	
}
