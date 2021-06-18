import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameScreen extends JPanel {
	Time timer;
	Tank tank;
	JFrame window;
	Texture world;
	ArrayList<Obstacles> obstacles;
	Bot AI;
	Texture bullet;

	public GameScreen() {
		bullet = new Texture("/Textures/mermi.png", 0, 0, 10, 5);

		timer = new Time();
		window = new JFrame();
		window.add(this);
		window.setTitle("TankGame");
		window.setVisible(true);
		window.setSize(1416, 840);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		world = new Texture("/Textures/arkaplan.png", 0, 0, 1416 + 24, 864);

		tank = new Tank(48, 48, 5, this, window);

		obstacles = new ArrayList<>();
		obstacles.add(new Obstacles("/Textures/long_wall_up.png", 0, 0, 1416 + 24, 48));
		obstacles.add(new Obstacles("/Textures/long_wall_up.png", 0, 794 + 24, 1416 + 24, 48));
		obstacles.add(new Obstacles("/Textures/long_wall_side.png", 0, 48, 48, 840 - 96 + 24));
		obstacles.add(new Obstacles("/Textures/long_wall_side.png", 1368 + 24, 48, 48, 840 - 96 + 24));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 3, 48 * 4, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 3, 48 * 6, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/wall.png", 48 * 3, 48 * 3, 48, 48));
		obstacles.add(new Obstacles("/Textures/wall.png", 48 * 4, 48 * 3, 48, 48));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 3, 48 * 10, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 3, 48 * 12, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/wall.png", 48 * 3, 48 * 14, 48, 48));
		obstacles.add(new Obstacles("/Textures/wall.png", 48 * 4, 48 * 14, 48, 48));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 8, 48 * 3, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 8, 48 * 7, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 8, 48 * 9, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 8, 48 * 13, 48 * 2, 48 * 2));

		obstacles.add(new Obstacles("/Textures/tanktrap.png", 48 * 12, 48 * 2, 48, 48));
		obstacles.add(new Obstacles("/Textures/tanktrap.png", 48 * 13, 48 * 1, 48, 48));
		obstacles.add(new Obstacles("/Textures/tanktrap.png", 48 * 14, 48 * 2, 48, 48));
		obstacles.add(new Obstacles("/Textures/tanktrap.png", 48 * 15, 48 * 1, 48, 48));
		obstacles.add(new Obstacles("/Textures/tanktrap.png", 48 * 16, 48 * 2, 48, 48));
		obstacles.add(new Obstacles("/Textures/tanktrap.png", 48 * 17, 48 * 1, 48, 48));

		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 13, 48 * 5, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 15, 48 * 5, 48 * 2, 48 * 2));

		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 13, 48 * 11, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 15, 48 * 11, 48 * 2, 48 * 2));

		obstacles.add(new Obstacles("/Textures/tanktrap.png", 48 * 12, 48 * 15, 48, 48));
		obstacles.add(new Obstacles("/Textures/tanktrap.png", 48 * 13, 48 * 16, 48, 48));
		obstacles.add(new Obstacles("/Textures/tanktrap.png", 48 * 14, 48 * 15, 48, 48));
		obstacles.add(new Obstacles("/Textures/tanktrap.png", 48 * 15, 48 * 16, 48, 48));
		obstacles.add(new Obstacles("/Textures/tanktrap.png", 48 * 16, 48 * 15, 48, 48));
		obstacles.add(new Obstacles("/Textures/tanktrap.png", 48 * 17, 48 * 16, 48, 48));

		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 20, 48 * 3, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 20, 48 * 7, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 20, 48 * 9, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 20, 48 * 13, 48 * 2, 48 * 2));

		obstacles.add(new Obstacles("/Textures/wall.png", 48 * 25, 48 * 3, 48, 48));
		obstacles.add(new Obstacles("/Textures/wall.png", 48 * 26, 48 * 3, 48, 48));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 25, 48 * 4, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 25, 48 * 6, 48 * 2, 48 * 2));

		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 25, 48 * 10, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/roof.png", 48 * 25, 48 * 12, 48 * 2, 48 * 2));
		obstacles.add(new Obstacles("/Textures/wall.png", 48 * 25, 48 * 14, 48, 48));
		obstacles.add(new Obstacles("/Textures/wall.png", 48 * 26, 48 * 14, 48, 48));

		AI = new Bot(1440 - 96, 864 - 96, 5, this, window, tank);

		KeyListener Keyboard = new KeyListener() {

			public void keyPressed(KeyEvent arg0) {

				tank.keyPressed(arg0);
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

				tank.keyReleased(arg0);

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

		};
		this.addKeyListener(Keyboard);
		this.requestFocus();
		this.setFocusable(true);

		MouseListener Mouse = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

				if (timer.getTime() >= tank.tankReloadTime) {
					tank.FireTheTank = true;

					tank.fire(e);
					timer.StartTime();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		};
		this.addMouseListener(Mouse);
		MouseMotionListener MotionListener = new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				tank.CalAngle(arg0);

			}

		};
		this.addMouseMotionListener(MotionListener);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Point hotSpot = new Point(18,18);
		try {
			BufferedImage cursorImage = ImageIO.read(getClass().getResource("/Textures/target-512.png"));
			Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, hotSpot, "InvisibleCursor");        
			setCursor(invisibleCursor);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}

	public void paint(Graphics g) {

		super.paint(g);
		if (tank.getHealth() != 0 && AI.getHealth() != 0) {
			Graphics2D g2D = (Graphics2D) g;

			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			world.render(g2D, world.x, world.y, world.w, world.h);
			try {
				tank.paint(g2D, obstacles, AI, bullet);
				AI.paint(g2D, obstacles, tank, bullet);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!obstacles.isEmpty()) {
				for (int i = 0; i < obstacles.size(); ++i) {

					obstacles.get(i).render(g2D);

				}
				
			}
		} else if (tank.getHealth() == 0) {
			System.out.println("You lost.");
			System.exit(ABORT);
		} else if (AI.getHealth() == 0) {
			System.out.println("You won.");
			System.exit(ABORT);
		}

	}

}
