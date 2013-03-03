package patrick.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import patrick.geom.Geom;
import patrick.object.core.Vector2f;

public class Rocket2f implements KeyListener {

	public static final Vector2f gravity = new Vector2f(0, -9.8f);
	
	public final float mass;
	public final float power;
	public float k = 0.5f;
	public static final float g = 9.81f;
	
	public boolean engines = false;
	private int turnAcc = 0;
	private int turnVelo = 0;
	
	public Vector2f loc;
	public Vector2f velo;
	public Vector2f dir;

	private final Vector2f[] shape;

	public Rocket2f(float mass, float enginePower) {
		this.mass = mass;
		power = enginePower;

		loc = new Vector2f();
		velo = new Vector2f();
		dir = new Vector2f(0, 1);

		shape = new Vector2f[] { new Vector2f(0, 3), new Vector2f(2, -1),
				new Vector2f(0, 0), new Vector2f(-2, -1) };
	}

	public void setLoc(float x, float y) {
		loc.set(x, y);
	}

	public void rotate(double theta) {
		turnVelo += theta;
		Geom.normaliseAngle(turnVelo);
		double sin = Math.sin(theta), cos = Math.cos(theta);
		for (Vector2f v : shape) {
			v.rotate(sin, cos);
		}
		dir.rotate(sin, cos);
	}

	private static final float vSmall = 1f;
	
	public void update(double delta) {

		if (turnAcc != 0 || turnVelo != 0) {
			rotate(turnVelo * delta + 0.5d * turnAcc * delta * delta);
		}
		
		Vector2f temp;
		if (engines) {
			
			double dv = Math.sqrt(2 * power * delta / mass);
			temp = dir.copy(); // dir is the direction of the engine's force
			temp.scale(dv);
			velo.add(temp);
			
			double dx = dv * delta * 2 / 3;
			temp = velo.copy();
			temp.scale(dx);
			loc.add(temp);
			
			// TODO add drag
		} else {
			
			if (velo.i > vSmall || velo.i < -vSmall) {
//				double sqrtK = Math.sqrt(k), sqrtG = Math.sqrt(g);
//				double atanU = Math.atan(velo.i * sqrtK / sqrtG);
//				loc.i += (float) -Math.log(1 / Math.cos(atanU - delta * sqrtG * sqrtK));
//				velo.i = (float) ((sqrtG / sqrtK) * Math.tan(atanU - delta * sqrtG * sqrtK));
				float sign = velo.i > 0 ? 1 : -1;
				loc.i += sign * (float) (Math.log(1 + Math.abs(k * delta * velo.i)) / k);
				velo.i = sign * (float) (1 / ((1 / Math.abs(velo.i)) + k * delta));
			} else {
				velo.i = 0;
			}
			
			if (velo.j > vSmall || velo.j < -vSmall) {
				float sign = velo.j > 0 ? 1 : -1;
				loc.j += sign * (float) (Math.log(1 + Math.abs(k * delta * velo.j)) / k);
				velo.j = sign * (float) (1 / ((1 / Math.abs(velo.j)) + k * delta));
			} else {
				velo.j = 0;
			}
			
			
		}
	}
	
	/*public void update2(double delta) {
		
		Vector2f accTemp;
		if (engines) {
			accTemp = dir.copy();
		} else {
			accTemp = velo.copy();
			accTemp.square();
			accTemp.scale(-drag);
		}

		velo.add(accTemp.scaled(delta));
		if (velo.lengthSq() > maxVeloSq) {
			velo.scale(maxVelo);
			velo.normalise();
		}
		loc.add(velo.scaled(delta));
		loc.sub(accTemp.scaled(0.5d * delta * delta));
		
	}
*/
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
			g.drawLine(x + (int) (scale * shape[j].i),
					y + (int) (scale * shape[j].j),
					x + (int) (scale * shape[i].i),
					y + (int) (scale * shape[i].j));
			j = i;
		}
	}

	public Vector2f[] getShape() {
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
