package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import javax.imageio.ImageIO;
/** creates the rocket object as a game piece used in the game
 * @author Alec Zhu
 * @author Cynthia Wu
 */
public class rocket1 extends Entity{
	
	/** creates PlayerTank*/
	private PlayerTank tank;
	
	/** the rate of which x moves in*/
	private double dx;
	
	/** the rate of which y moves in*/
	private double dy;
	
	/** check if the enemy fired a rocket*/
	private boolean enemyFired;
	
	/** constructor of rocket1 class for player tank
	 * 
	 * @param p1	playertank object
	 */
	public rocket1(PlayerTank p1) {
		
		/** sets field tank to p1*/
		tank = p1;
		
		/** adjust bounds, sets speed, sets enemyFired to false3*/
		setSolid(new Rectangle(1,1,16,9));
		setSpeed(5);
		setEnemyFired(false);
		
		/** obtains rocket png*/
		try {
			setRocket(ImageIO.read(getClass().getResourceAsStream("/rockets/rocket1.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		/** sets location and angle of rocket */
		this.setX(tank.getX()+8);
		this.setY(tank.getY()+8);
		setAngle(p1.getAngle()); 
		setHitbox(new Rectangle((int)getX(),(int)getY(),12,7));
		
		/** calculates travel distance of rocket */
		calcDistance();
		
	}
	
	/** constructor of rocket1 class for enemy tank
	 * 
	 * @param e1	creates enemy tank object
	 */
	public rocket1(EnemyTank e1) {
		
		/** sets boundary and speed*/
		setSolid(new Rectangle(1,1,16,9));
		setSpeed(4);
		
		/** obtains rocket png*/
		try {
			setRocket(ImageIO.read(getClass().getResourceAsStream("/rockets/rocket1.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		/** sets boolean of enemy fired to true*/
		setEnemyFired(true);
		
		/** movement and angle of rocket for enemy tank*/
		double adjustedX;
		double adjustedY;
		final double radius = Math.sqrt(128);
		
		adjustedY = radius* Math.sin(getAngle());
		adjustedX = radius * Math.cos(getAngle());
		
		
		
		this.setX(e1.getX()+8);
		this.setY(e1.getY()+8);
		setAngle(e1.getAngle()); 
		setHitbox(new Rectangle((int)getX(),(int)getY(),12,7));
		
		
		
		calcDistance();
	}
	
	/**calculate distance of rocket to player tank
	 */
	public void calcDistance() {
		dx = (getSpeed() * Math.cos(Math.toRadians(getAngle())));
		dy = (getSpeed() * Math.sin(Math.toRadians(getAngle())));
	} 
	
	/** update the movement of the rocket
	 */
	public void update() {
		setX(getX() + dx);
		setY(getY() - dy);
		setHitbox(new Rectangle((int)getX(),(int)getY(),16,9));
	}

	public boolean isEnemyFired() {
		return enemyFired;
	}
	public void setEnemyFired(boolean enemyFired) {
		this.enemyFired = enemyFired;
	}

	
}