package patrick;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import patrick.object.Rocket2f;

public class Main extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = -1024197000844393309L;
	public static final int WIDTH = 300, HEIGHT = 280, NS_PER_FRAME = 1000 * 1000,
			NS_PER_UPDATE = 1000 * 10;
	public static final float SCALE = 4;
	
	public boolean running = true;
	public boolean updating = true;
	public final JFrame frame;
	
	public Rocket2f r = new Rocket2f(.1f, 500);
	
	public Main() {
		Dimension dim = new Dimension(WIDTH, HEIGHT);
		setSize(dim);
		addKeyListener(this);
		addKeyListener(r);

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}	
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.clearRect(0, 0, getHeight(), getWidth());
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());		
		
		r.draw(g, SCALE);
		
		g.dispose();
		bs.show();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			running = false;
			break;
		case KeyEvent.VK_P:
			updating = !updating;
			break;
		}
	}
	
	@Override public void keyReleased(KeyEvent e) {}
	@Override public void keyTyped(KeyEvent e) {}

	@Override
	public void run() {
		r.setLoc(getWidth() * 1f / SCALE, getHeight() * 0.5f / SCALE);
		
		long t1 = 0, t2 = 0;
		while (running) {

			if (System.nanoTime() - t1 >= NS_PER_UPDATE) {
				r.update((System.nanoTime() - t1) * 1e-9);
				checkCollision();
				t1 = System.nanoTime();
			}

			if (System.nanoTime() - t2 >= NS_PER_FRAME) {
				render();
				t2 = System.nanoTime();
			}
		}
		frame.dispose();
		System.exit(0);
	}
	
	public void checkCollision() {/*
		Vector2f[] s = r.getShape();
		
		s[0].add(r.loc);
		float minx = s[0].i + r.loc.i, maxx = minx, miny = s[0].j + r.loc.j, maxy = miny;
		
		for (int i = 1; i < s.length; i++) {			
			s[i].add(r.loc);
			
			if (s[i].i < minx) minx = s[i].i;
			else if (s[i].i > maxx) maxx = s[i].i;
			
			if (s[i].j < miny) miny = s[i].j;
			else if (s[i].j > maxy) maxy = s[i].j;
		}
		
		if (maxx > 0) {
			r.loc.i = -minx;
			r.velo.i *= -1;
		}
		if (maxy > 0) {
			r.loc.j = -miny;
			r.velo.j *= -1;
		}
	*/}
	
	public static void main(String[] args) {
		Main m = new Main();
		m.run();
	}

}
