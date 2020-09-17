import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class driver extends JPanel implements ActionListener, KeyListener {

	int table_width = 615; // width of the screen "table"
	int table_height = 610; // height of the screen "table"

	// variables for a paddle

	// variable for the power up
	int pow_y = -15;
	int pow_x = 892; // so then it is off the screen, and then later we bring it back in
	int pow_s = 15;
	int pow_vy = 0;

	int pow2_y = -15;
	int pow2_x = 892;
	int pow2_s = 15;
	int pow2_vy = 2;

	int start_x = 235;

	// counter stuff
	int brickCounter = 0;
	int levelNum = 1;
	int powdivis10 = 0;
	int pow2divis10 = 0;
	int levelPad = 10;

	int b_s2 = 15; // size
	int b_y2 = 800; // ball top left corner
	int b_x2 = 892; // ball top left corner
	// game starts with velocity of zero
	int b_vx2 = 0;
	int b_vy2 = 0;

	String bricksBroken = "Bricks broken:"; // String for the bricks broken
	String lengthP = "Paddle length:"; // String for the paddle length
	String level = "Level:";
	String start = "Press Spacebar to Start";
	String bg = "back.png";

	// booleans & arrays
	int max_vals = 21;
	int[] x = new int[max_vals]; // x value for the bricks
	int[] y = new int[max_vals]; // y value for the bricks
	int[] n = new int[max_vals]; // the num of hits per brick
	boolean[] v = new boolean[max_vals]; // visible stuff for the bricks
	boolean right = false; // smooth right key
	boolean left = false; // smooth left key
	boolean move = false;

	ball ball = new ball();
	paddle paddle = new paddle();

	public void paint(Graphics g) {
		super.paintComponent(g);

		g.fillRect(0, 0, table_width, table_height); // making the screen black

		for (int i = 0; i < x.length; i++) {

			g.setColor(Color.red);
			if (v[i] == true) {
				g.fillRect(x[i], y[i], 50, 30); // drawing the bricks
			}
		}

		g.setColor(Color.blue);
		g.fillRect(pow_x, pow_y, pow_s, pow_s); // drawing the power up

		g.setColor(Color.green);
		g.fillRect(pow2_x, pow2_y, pow2_s, pow2_s);

		g.setColor(Color.white);
		g.fillRect(paddle.getP_x(), paddle.getP_y(), paddle.getP_w(), 15 / 2); // drawing the paddle

		String count = Integer.toString(brickCounter);
		String width = Integer.toString(paddle.getP_w());
		String numLevel = Integer.toString(levelNum);

		// displaying paddle with and bricks broken
		g.setColor(Color.white);
		g.drawString(lengthP, 500, 20);
		g.drawString(width, 580, 20);
		g.drawString(bricksBroken, 500, 40);
		g.drawString(count, 580, 40);
		g.drawString(level, 10, 20);
		g.drawString(numLevel, 45, 20);
		g.drawString(start, start_x, 500);

		int red, green, blue; // rgb color flashing thingy
		red = (int) (Math.random() * (256) + 0);
		green = (int) (Math.random() * (256) + 0);
		blue = (int) (Math.random() * (256) + 0);
		Color c = new Color(red, green, blue);
		g.setColor(c);

		g.fillOval(ball.getB_x(), ball.getB_y(), ball.getB_s(), ball.getB_s()); // drawing the ball
		g.fillOval(b_x2, b_y2, b_s2, b_s2);

	}

	public void update() {
		// updating variables

		ball.move();
		paddle.move();

		b_x2 = b_x2 + b_vx2;
		b_y2 = b_y2 + b_vy2;

		pow_y = pow_y + pow_vy; // so then the velocity works
		pow2_y = pow2_y + pow2_vy;

		// add a right wall to ball
		if (b_x2 + b_s2 >= table_width) {
			b_vx2 *= -1;
		}

		// add a left wall to ball
		if (b_x2 <= 0) {
			b_vx2 *= -1;
		}

		// add a top wall to ball
		if (b_y2 <= 0) {
			b_vy2 *= -1;
		}

		// ball bouncing off the paddle

		if (ball.getB_y() + ball.getB_s() >= paddle.getP_y() && ball.getB_x() + ball.getB_s() >= paddle.getP_x()
				&& ball.getB_x() <= paddle.getP_x() + paddle.getP_w() && ball.getB_y() <= paddle.getP_y() + 15 / 2) {
			ball.setB_vy(ball.getB_vy() * -1);
		}
		if (b_y2 + b_s2 >= paddle.getP_y() && b_x2 + b_s2 >= paddle.getP_x()
				&& b_x2 <= paddle.getP_x() + paddle.getP_w() && b_y2 <= paddle.getP_y() + 15 / 2) {
			b_vy2 *= -1;
		}

		if (move == true) {
			start_x += 800;
		}

		// the end of the game
		if (b_x2 <= 615 && b_x2 >= 0) {
			if (ball.getB_y() + ball.getB_s() >= table_height && b_y2 + b_s2 >= table_height) {
				brickCounter = 0;
				levelNum = 1;
				paddle.setP_w(110);
				ball.setB_x(table_width / 2 - 15 / 2);
				ball.setB_y(400 - 15 / 2);
				ball.setB_vx(0);
				ball.setB_vy(0);
				b_y2 = 892;
				b_x2 = 800;
				paddle.setP_x(table_width / 2 - 60);
				for (int i = 0; i < x.length; i++) {
					v[i] = true;
					pow_x = 892;
					pow2_x = 892;
				}
				levelPad += 10;
			}

		} else if (b_x2 >= 616) {
			if (ball.getB_y() + ball.getB_s() >= table_height) {
				brickCounter = 0;
				levelNum = 1;
				paddle.setP_w(110);
				ball.setB_x(table_width / 2 - 15 / 2);
				ball.setB_y(400 - 15 / 2);
				ball.setB_vx(0);
				ball.setB_vy(0);
				b_x2 = 800;
				b_y2 = 892;
				paddle.setP_x(table_width / 2 - 60);
				for (int i = 0; i < x.length; i++) {
					v[i] = true;
					pow_x = 892;
					pow2_x = 892;
				}
				levelPad += 10;
				start_x = 235;
			}
		}

		for (int i = 0; i < x.length; i++) {
			if (v[i] == false)
				continue; // allows skip
			// making objects to make things easier
			Rectangle bricksHRight = new Rectangle(x[i] + 40, y[i], 1, 20);
			Rectangle bricksHLeft = new Rectangle(x[i], y[i], 1, 20);
			Rectangle bricksWTop = new Rectangle(x[i], y[i], 40, 1);
			Rectangle bricksWBottom = new Rectangle(x[i], y[i] + 20, 40, 1);
			Rectangle ball1 = new Rectangle(ball.getB_x(), ball.getB_y(), 15, 15);
			Rectangle ball2 = new Rectangle(b_x2, b_y2, b_s2, b_s2);
			Rectangle powerUp = new Rectangle(pow_x, pow_y, pow_s, pow_s);
			Rectangle powerUp2 = new Rectangle(pow2_x, pow2_y, pow2_s, pow2_s);
			Rectangle pad = new Rectangle(paddle.getP_x(), paddle.getP_y(), paddle.getP_w(), 15 / 2);

			if (brickCounter != 0 && brickCounter % 10 == 0) {
				int random = (int) (Math.random() * 600);
				pow_x = random;
				pow_y = -16;
				pow_vy = 2;
			}

			if (brickCounter % 5 == 0 && brickCounter != 0 && (b_y2 > 615 || ball.getB_y() > 615) && pow_y == -15) {
				int random1 = (int) (Math.random() * 600);
				pow2_x = random1;
				pow2_y = -15;
			}

			if (pad.intersects(powerUp)) {
				paddle.setP_w(80); // power up sets the paddle to 90
				pow_x = 892;
				pow_y = -15;
				pow_vy = 0;
			}

			if (pad.intersects(powerUp2) && b_y2 > 615) {
				pow2_x = 892;
				pow2_y = -15;
				b_x2 = 292;
				b_y2 = 400 - b_s2;
				int random1 = (int) (Math.random() * 2 + 2);
				int random2 = (int) (Math.random() * 2 + 2);
				b_vx2 = -random1 - levelNum + 1;
				b_vy2 = random2 + levelNum - 1;
			}
			if (pad.intersects(powerUp2) && ball.getB_y() > 615) {
				pow2_x = 892;
				pow2_y = -15;
				ball.setB_x(292);
				ball.setB_y(400 - b_s2);
				int random1 = (int) (Math.random() * 2 + 2);
				int random2 = (int) (Math.random() * 2 + 2);
				ball.setB_vx(-random1 - levelNum + 1);
				ball.setB_vy(random2 + levelNum - 1);
			}

			// if ball helps right bottom corner
			if (ball1.intersects(bricksHRight) && ball1.intersects(bricksWBottom)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				ball.setB_vx(ball.getB_vx() * -1);
				ball.setB_vy(ball.getB_vy() * -1);
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			}

			// if ball helps right top corner
			else if (ball1.intersects(bricksHRight) && ball1.intersects(bricksWTop)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				ball.setB_vx(ball.getB_vx() * -1);
				ball.setB_vy(ball.getB_vy() * -1);
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			}

			// if ball helps left bottom corner
			else if (ball1.intersects(bricksHLeft) && ball1.intersects(bricksWBottom)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				ball.setB_vx(ball.getB_vx() * -1);
				ball.setB_vy(ball.getB_vy() * -1);
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			}

			// if ball helps left top corner
			else if (ball1.intersects(bricksHLeft) && ball1.intersects(bricksWTop)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				ball.setB_vx(ball.getB_vx() * -1);
				ball.setB_vy(ball.getB_vy() * -1);
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			}

			// if ball helps right corner
			else if (ball1.intersects(bricksHRight)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				ball.setB_vx(ball.getB_vx() * -1);
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			}

			// if ball helps left corner
			else if (ball1.intersects(bricksHLeft)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				ball.setB_vx(ball.getB_vx() * -1);
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			}

			// if ball helps top corner
			else if (ball1.intersects(bricksWTop)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				ball.setB_vy(ball.getB_vy() * -1);
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			}

			// if ball helps bottom corner
			else if (ball1.intersects(bricksWBottom)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				ball.setB_vy(ball.getB_vy() * -1);
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			}
			if (ball2.intersects(bricksHRight) && ball2.intersects(bricksWBottom)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				b_vx2 *= -1;
				b_vy2 *= -1;
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			} else if (ball2.intersects(bricksHRight) && ball2.intersects(bricksWTop)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				b_vx2 *= -1;
				b_vy2 *= -1;
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			} else if (ball2.intersects(bricksHLeft) && ball2.intersects(bricksWBottom)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				b_vx2 *= -1;
				b_vy2 *= -1;
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			} else if (ball2.intersects(bricksHLeft) && ball2.intersects(bricksWTop)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				b_vx2 *= -1;
				b_vy2 *= -1;
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			}
			// if ball2 helps right corner
			else if (ball2.intersects(bricksHRight)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				b_vx2 *= -1;
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			}

			// if ball2 helps left corner
			else if (ball2.intersects(bricksHLeft)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				b_vx2 *= -1;
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			}

			// if ball2 helps top corner
			else if (ball2.intersects(bricksWTop)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				b_vy2 *= -1;
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			}

			// if ball2 helps bottom corner
			else if (ball2.intersects(bricksWBottom)) {
				brickCounter++;
				paddle.setP_w(paddle.getP_w() - 5);
				n[i]++;
				b_vy2 *= -1;
				if (n[i] >= 1) {
					v[i] = false; // set location for visible to be false

				}

			}
			if (paddle.getP_w() == 0) {
				paddle.setP_w(paddle.getP_w() + 5);

			}

			if (v[0] == false && v[1] == false && v[2] == false && v[3] == false && v[4] == false && v[5] == false
					&& v[6] == false && v[7] == false && v[8] == false && v[9] == false && v[10] == false
					&& v[11] == false && v[12] == false && v[13] == false && v[14] == false && v[15] == false
					&& v[16] == false && v[17] == false && v[18] == false && v[19] == false && v[20] == false) {
				levelNum++;

				for (int j = 0; j < x.length; j++) {
					v[j] = true;
				}

				ball.setB_x(table_width / 2 - 15 / 2);
				ball.setB_y(400 - 15 / 2);
				ball.setB_vx(0);
				ball.setB_vy(0);
				b_y2 = 800;
				b_x2 = 892;
				b_vx2 = 0;
				b_vy2 = 0;

				levelPad += 10;
			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		repaint();
	}

	public static void main(String[] arg) {
		driver d = new driver();
	}

	public driver() {

		JFrame f = new JFrame();
		f.setTitle("Break Bricker");
		f.setSize(table_width, table_height);
		f.setBackground(Color.BLACK);
		f.setResizable(false);
		f.addKeyListener(this);
		f.add(this);
		t = new Timer(17, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		// making new coordinates for bricks
		for (int i = 0, z = 125, w = 100; i < x.length; i++, z += 60) {
			x[i] = z;
			y[i] = w;
			v[i] = true;

			if (i == 5) {
				w += 40;
				z = 95;
			}

			if (i == 10) {
				w += 40;
				z = 125;
			}

			if (i == 14) {
				w += 40;
				z = 155;
			}

			if (i == 17) {
				w += 40;
				z = 185;
			}

			if (i == 19) {
				w += 40;
				z = 215;
			}
		}

		t = new Timer(17, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

	}

	Timer t;

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == 39) {
			paddle.setRight(true);
		}
		if (arg0.getKeyCode() == 37) {
			paddle.setLeft(true);
		}

		// randomize the starting velocity
		if (arg0.getKeyCode() == 32 && ball.getB_vx() == 0) {
			int random1 = (int) (Math.random() * 2 + 2);
			int random2 = (int) (Math.random() * 2 + 2);
			ball.setB_vx(random1 + levelNum - 1);
			ball.setB_vy(random2 + levelNum - 1);
			move = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == 39) {
			paddle.setRight(false);
		}

		if (arg0.getKeyCode() == 37) {
			paddle.setLeft(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getKeyCode());

	}

}



