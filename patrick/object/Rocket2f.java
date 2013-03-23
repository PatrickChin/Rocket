package patrick.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import patrick.geom.Geom;
import patrick.object.core.Vec2;

public class Rocket2f implements KeyListener {

	public static final Vec2 gravity = new Vec2(0, -9.8f);

	public final float mass;
	public final float power;
	private double Drag_const = .4f;
	public static final double g = 9.81;

	public boolean engines = false;
	private int turnAcc = 0;
	private int turnVelo = 0;

	public Vec2 loc;
	public Vec2 velo;
	public Vec2 dir;

	private final Vec2[] shape;

	public Rocket2f(float mass, float enginePower) {
		this.mass = mass;
		power = enginePower;

		loc = new Vec2();
		velo = new Vec2();
		dir = new Vec2(0, 1);

		shape = new Vec2[] { new Vec2(0, 3), new Vec2(2, -1), new Vec2(0, 0),
				new Vec2(-2, -1) };
	}

	public void setLoc(float x, float y) {
		loc.set(x, y);
	}

	public void rotate(double theta) {
		turnVelo += theta;
		Geom.normaliseAngle(turnVelo);
		double sin = Math.sin(theta), cos = Math.cos(theta);
		for (Vec2 v : shape) {
			v.rotate(sin, cos);
		}
		dir.rotate(sin, cos);
	}

	private static final float vSmall = .5f;

	public void update(double delta) {

		if (turnAcc != 0 || turnVelo != 0) {
			rotate(turnVelo * delta + 0.5d * turnAcc * delta * delta);
			turnVelo += turnAcc;
		}

		if (engines) {
			
			double Pt_m = power * delta / mass;
			
			double u = velo.i; 
			int sign = dir.i < 0 ? -1 : 1;
			velo.i = sign * Math.sqrt(Math.abs(u * u + sign * 2 * Pt_m * dir.i));
			loc.i += sign * (velo.i * velo.i * velo.i - u * u * u) * mass / (3 * power) * dir.i;
			
			u = velo.j; 
			sign = dir.j < 0 ? -1 : 1;
			velo.j = sign * Math.sqrt(Math.abs(u * u + sign * 2 * Pt_m * dir.j));
			loc.j += sign * (velo.j * velo.j * velo.j - u * u * u) * mass / (3 * power) * dir.j;
			
		} else {
			if (velo.i > vSmall || velo.i < -vSmall) {
				double i = Math.log(Math.abs(velo.i) * Drag_const * delta + 1) / Drag_const;
				loc.i += velo.i > 0 ? i : -i;
				velo.i /= 1 + Math.abs(velo.i) * Drag_const * delta;
			} else {
				velo.i = 0;
			}

			if (velo.j > vSmall || velo.j < -vSmall) {
				double j = Math.log(Math.abs(velo.j) * Drag_const * delta + 1) / Drag_const;
				loc.j += velo.j > 0 ? j : -j;
				velo.j /= 1 + Math.abs(velo.j) * Drag_const * delta;
			} else {
				velo.j = 0;
			}
		}
	}

	public void draw(Graphics g, float scale) {
		g.setColor(Color.WHITE);
		g.drawString("loc: " + loc.toString(), 10, 20);
		g.drawString("velo: " + velo.toString(), 10, 40);
		g.drawString("dir: " + dir.toString(), 10, 60);

		g.setColor(Color.GREEN);
		scale = -scale;
		int x = (int) (scale * loc.i);
		int y = (int) (scale * loc.j);
		for (int i = 0, j = shape.length - 1; i < shape.length; i++) {
			g.drawLine(x + (int) (scale * shape[j].i), y
					+ (int) (scale * shape[j].j), x
					+ (int) (scale * shape[i].i), y
					+ (int) (scale * shape[i].j));
			j = i;
		}
	}

	public Vec2[] getShape() {
		return shape;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			engines = true;
			break;
		case KeyEvent.VK_LEFT:
			turnVelo = -5;
			break;
		case KeyEvent.VK_RIGHT:
			turnVelo = 5;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			engines = false;
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
			turnVelo = 0;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
