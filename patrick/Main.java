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

import patrick.object.core.Vec2;

public class Main extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = -1024197000844393309L;
	
	public static final int WIDTH = 640, HEIGHT = 480;
	public static final String NAME = "Patrick's Java Application";
	
	/**
	 * Default: 15 ~ 66.7 FPS
	 */
	private static final int MS_PER_FRAME = 15;
	private static final int NS_PER_UPDATE = 1000 * 1000 * 10;
	public static float SCALE = 4;
	
	public boolean running = true;
	public boolean updating = true;
	private final JFrame frame;
	
	public Main() {
		Dimension dim = new Dimension(WIDTH, HEIGHT);
		setSize(dim);
		addKeyListener(this);

		frame = new JFrame();
		frame.setName(NAME);
		frame.setTitle(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void update(double dt) {
		
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
		
		long t1 = 0, t2 = 0, dt = 0;
		while (running) {
			dt = System.nanoTime() - t1;
			if (updating && dt >= NS_PER_UPDATE) {
				update(dt * 1e-9);
				t1 = System.nanoTime();
			}

			if (System.currentTimeMillis() >= t2) {
				render();
				t2 = System.currentTimeMillis() + MS_PER_FRAME;
			}
		}
		frame.dispose();
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		m.run();
	}

}
