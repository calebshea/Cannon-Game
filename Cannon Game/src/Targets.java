import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Targets {
	public int targets[][];
	public Rectangle targetRectangles[][];
	public int brickWidth;
	public int brickHeight;
	public int firstBrickX;
	public int firstBrickY;
	
	public Targets (int row, int col) {
		targets = new int[row][col];
		for(int i = 0; i < targets.length; i++) {
			for(int j = 0; j < targets[0].length; j++) {
				targets[i][j] = 1;
			}
		}
		firstBrickX = 375;
		firstBrickY = 450;
		brickWidth = 40;
		brickHeight = 30;
	}
	
	public void draw(Graphics2D g) {
		targetRectangles = new Rectangle[targets.length][targets[0].length];
		for(int i = 0; i < targets.length; i++) {
			for(int j = 0; j < targets[0].length; j++) {
				if(targets[i][j] > 0) {
					g.setColor(Color.white);
					g.fillRect(j * brickWidth + firstBrickX, i * brickHeight + firstBrickY, brickWidth, brickHeight);
				
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * brickWidth + firstBrickX, i * brickHeight + firstBrickY, brickWidth, brickHeight);
					
					targetRectangles[i][j] = new Rectangle(j * brickWidth + firstBrickX, i * brickHeight + firstBrickY, brickWidth, brickHeight);
				}
			}
		}
	}
	
	public void setBrickValue(int value, int row, int col) {
		targets[row][col] = value;
		
	}
}
