package patrick.object.core;

import patrick.geom.Geom;

public class Vector2f {

	public float i, j;

	public Vector2f() {
		i = 0;
		j = 0;
	}

	public Vector2f(float i, float j) {
		this.i = i;
		this.j = j;
	}

	public Vector2f(Vector2f v) {
		i = v.i;
		j = v.j;
	}

	public void add(Vector2f... vs) {
		for (Vector2f v : vs) {
			i += v.i;
			j += v.j;
		}
	}

	public void sub(Vector2f... vs) {
		for (Vector2f v : vs) {
			i -= v.i;
			j -= v.j;
		}
	}

	public void scale(double scalar) {
		i *= scalar;
		j *= scalar;
	}

	@Deprecated
	public Vector2f scaled(double scalar) {
		Vector2f vec = this.copy();
		vec.scale(scalar);
		return vec;
	}

	public void negate() {
		i = -i;
		j = -j;
	}

	@Deprecated
	public Vector2f negated() {
		return new Vector2f(-i, -j);
	}
	
	public void reciprocate() {
		i = 1 / i;
		j = 1 / j;
	}

	public void square() {
		i *= i;
		j *= j;
	}

	@Deprecated
	public Vector2f squared() {
		return new Vector2f(i * i, j * j);
	}

	public void rotate(double a) {
		double cos = Math.cos(a), sin = Math.sin(a);
		rotate(cos, sin);
	}

	public void rotate(double sin, double cos) {
		double i_ = i * cos - j * sin;
		j = (float) (i * sin + j * cos);
		i = (float) i_;
	}

	@Deprecated
	public Vector2f rotated(double a) {
		Vector2f v = copy();
		v.rotate(a);
		return v;
	}

	@Deprecated
	public Vector2f rotated(double sin, double cos) {
		Vector2f v = copy();
		v.rotate(sin, cos);
		return v;
	}

	public void cross(Vector2f v) {
		float i_ = (i * v.i) - (j * v.j);
		j = (i * v.j) + (j * v.i);
		i = i_;
	}

	@Deprecated
	public Vector2f crossed(Vector2f v) {
		Vector2f v1 = copy();
		v1.cross(v);
		return v1;
	}

	public double dot(Vector2f v) {
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

	public double acuteAngleTo(Vector2f v) {
		double a = Math.abs(angle() - v.angle());
		if (a <= Math.PI)
			return a;
		return Math.PI * 2 - a;
	}

	public double angleTo(Vector2f v) {
		double dot = dot(v);
		if (dot == 0)
			return Math.PI / 2;
		double mags = Math.sqrt((i * i + j * j) * (v.i * v.i + v.j * v.j));
		return Math.acos(dot(v) / mags);
	}

	public double component(double angle) {
		return (length() * Math.cos(angle() - angle));
	}

	public boolean isOrthogonal(Vector2f v) {
		return dot(v) == 0;
	}

	public void set(double length, double angle) {
		if (length < 0) {
			length = -length;
			angle += Math.PI;
		}
		angle = Geom.normaliseAngle(angle);
		i = (float) (length * Math.cos(angle));
		j = (float) (length * Math.sin(angle));
	}
	
	public void set(double length, double sin, double cos) {
		i = (float) (length * cos);
		j = (float) (length * sin);
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

	public Vector2f copy() {
		return new Vector2f(i, j);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vector2f) {
			Vector2f v = (Vector2f) o;
			return v.i == i && v.j == j;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%s[i=%.2f,j=%.2f]", ""
		/* Vector.class.getSimpleName() */, i, j);
	}

	public String toString2() {
		return String.format("%s[length=%.2f,angle=%.2f\u00B0]",
				Vector2f.class.getSimpleName(), length(),
				Math.toDegrees(angle()));
	}

	public void print() {
		System.out.println(toString());
	}

}
