import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	private int totalShots = 25;
	private int [][] cannonPoints = {{50, 40, 60}, {525, 450, 450}};
	private javax.swing.Timer timer;
	private int delay = 8;
	private int cannonAngle = 90;
	private int ballPosX = 5;
	private int ballPosY = 50;
	private int ballXvec = 0;
	private int ballYvec = 0;

	private Targets targets;

	public Gameplay() {
		targets = new Targets(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new javax.swing.Timer(delay, this);
		timer.start();
	}

	public void paint(Graphics g) {
		// background
		g.setColor(Color.black);
		g.fillRect(1,  1,  692, 592);

		// drawing targets
		targets.draw((Graphics2D)g);

		// borders
		g.setColor(Color.yellow);
		g.fillRect(0,  0,  3,  592);
		g.fillRect(0,  0,  692,  3);
		g.fillRect(691,  0,  3,  592);

		// scores
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Score: " + score,  10,  30);
		g.drawString("Cannon angle: " + cannonAngle,  10,  60);
		g.drawString("Ammo: " + totalShots, 10, 90);

		// the cannon
		g.setColor(Color.green);
		g.fillRect(25,  525,  100,  20);
		g.fillPolygon(cannonPoints[0], cannonPoints[1], 3);

		// the wall
		g.fillRect(346, 400, 10, 150);

		// the ball
		//		g.setColor(Color.yellow);
		//		g.fillOval(ballPosX, ballPosY, 20, 20);

		if(totalBricks <= 0) {
			play = false;
			ballXvec = 0;
			ballYvec = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("YOU WON!!", 190, 300);

			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Press Enter to Restart", 230, 350);
		}

		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play){
			A: for(int i = 0; i < targets.targets.length; i++) {
				for(int j = 0; j < targets.targets[0].length; j++) {
					if(targets.targets[i][j] > 0) {
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = targets.targetRectangles[i][j];

						if(ballRect.intersects(brickRect)) {
							targets.setBrickValue(0,  i,  j);
							totalBricks--;
							score += 5;
							break A;
						}
					}
				}
			}

		ballPosX += ballXvec;
		ballPosY += ballYvec;

		if(ballPosX > 670){
			ballXvec = 0;
			ballYvec = 0;
		}
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(cannonAngle <= 15) {
				cannonAngle = 15;
			} else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(cannonAngle >= 90) {
				cannonAngle = 90;
			} else {
				moveLeft();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballPosX = 120;
				ballPosY = 350;
				ballXvec = 0;
				ballYvec = 0;
				cannonAngle = 45;
				score = 0;
				totalBricks = 21;
				targets = new Targets(3, 7);

				repaint();
			}
		}
	}

	public void moveRight() {
		play = true;
		cannonAngle -= 1;
		setCannonCoordinates();
	}

	public void moveLeft() {
		play = true;
		cannonAngle += 1;
		setCannonCoordinates();
	}

	public void setCannonCoordinates() {
		// Left side of triangle
		double leftX = 75.664 * Math.cos(Math.toRadians(cannonAngle + 7.595));
		double leftY = 75.664 * Math.sin(Math.toRadians(cannonAngle + 7.595));

		// Right side of triangle
		double rightX = 75.664 * Math.cos(Math.toRadians(cannonAngle - 7.595));
		double rightY = 75.664 * Math.sin(Math.toRadians(cannonAngle - 7.595));

		cannonPoints[0][1] = (int) leftX + 50;
		cannonPoints[1][1] = 525 - (int) leftY;
		cannonPoints[0][2] = (int) rightX + 50;
		cannonPoints[1][2] = 525 - (int) rightY;
	}
}
