package patrick.object.core;

import patrick.geom.Geom;

public class Vec2 {

	public double i, j;

	public Vec2() {
		i = 0;
		j = 0;
	}

	public Vec2(double i, double j) {
		this.i = i;
		this.j = j;
	}

	public Vec2(Vec2 v) {
		i = v.i;
		j = v.j;
	}

	public void add(Vec2... vs) {
		for (Vec2 v : vs) {
			i += v.i;
			j += v.j;
		}
	}

	public void sub(Vec2... vs) {
		for (Vec2 v : vs) {
			i -= v.i;
			j -= v.j;
		}
	}

	public void scale(double scalar) {
		i *= scalar;
		j *= scalar;
	}
	
	public void negate() {
		i = -i;
		j = -j;
	}
	
	public void reciprocate() {
		i = 1 / i;
		j = 1 / j;
	}

	public void square() {
		i *= i;
		j *= j;
	}

	public void rotate(double a) {
		double cos = Math.cos(a), sin = Math.sin(a);
		rotate(cos, sin);
	}

	public void rotate(double sin, double cos) {
		double i_ = i * cos - j * sin;
		j = (double) (i * sin + j * cos);
		i = (double) i_;
	}

	public void cross(Vec2 v) {
		double i_ = (i * v.i) - (j * v.j);
		j = (i * v.j) + (j * v.i);
		i = i_;
	}

	public double dot(Vec2 v) {
		return i * v.i + j * v.j;
	}

	public double angle() {
		return Math.atan2(j, i);
	}

	public double lengthSq() {
		return (i * i) + (j * j);
	}

	public double length() {
		return Math.sqrt((i * i) + (j * j));
	}

	public double gradient() {
		return j / i;
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
		double mags = Math.sqrt((i * i + j * j) * (v.i * v.i + v.j * v.j));
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
			angle += Math.PI;
		}
		angle = Geom.normaliseAngle(angle);
		i = length * Math.cos(angle);
		j = length * Math.sin(angle);
	}
	
	public void set(double length, double sin, double cos) {
		i = length * cos;
		j = length * sin;
	}

	public void setAngle(double angle) {
		set(length(), angle);
	}
	
	public void setAngle(double sin, double cos) {
		set(length(), sin , cos);
	}
	
	public void addAngle(double angle) {
		set(length(), angle() + angle);
	}

	public void setLength(double len) {
		i *= len;
		j *= len;
		normalise();
	}

	public void normalise() {
		double l = length();
		i /= l;
		j /= l;
	}

	public Vec2 copy() {
		return new Vec2(i, j);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vec2) {
			Vec2 v = (Vec2) o;
			return v.i == i && v.j == j;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%s[i=%.3f,j=%.3f]", ""
		/* Vector.class.getSimpleName() */, i, j);
	}

	public String toString2() {
		return String.format("%s[length=%.3d,angle=%.3d\u00B0]",
				Vec2.class.getSimpleName(), length(),
				Math.toDegrees(angle()));
	}

	public void print() {
		System.out.println(toString());
	}

}
