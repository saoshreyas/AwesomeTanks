package entity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.geom.Rectangle2D;
import java.awt.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import main.CollisionDetec;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Pathfinding.Pathfinder;
import main.BackgroundPanel;
import entity.Rocket1Manager;

/** main class for enemy tank 
 * @author Alec Zhu
 * @author Cynthia Wu
 */
public class EnemyTank extends Entity
{	
	/** background game panel */
	private BackgroundPanel panel;

	/** rate of which x changes */
	private double dx;

	/** rate of which y changes */
	private double dy;
	
	/** dead boolean for enemy tank */
	private boolean dead;
	
	/** player tank */
	private PlayerTank target;
	
	/** boolean for player collision */
	private boolean playerColl;
	
	/** time between bullets shot */
	private Date lastBulletTime;
	
	/** sub main class for rockets */
	private Rocket1Manager rk;
	
	/** boolean for dying animation */
	private boolean dying;
	
	/** boolean for if enemy tank is on path */
	private boolean onPath;
	
	
	/** constructor for enemy tank 
	 * @param p		background game panel
	 * @param defaultX		default value of x
	 * @param defaultY		default value of y
	 * @param target		player tank
	 * @param rk		rocket sub class
	 */
	public EnemyTank(BackgroundPanel p, int defaultX, int defaultY, PlayerTank target, Rocket1Manager rk) 
	{
		/** instantiating parameters */
		panel = p;
		setAngle(calculatePathAngle(target));
		this.target = target;
		setSolid(new Rectangle(6,6,26,26));
		playerColl = false;
		lastBulletTime = convertToDateViaSqlTimestamp(LocalDateTime.now());
		this.rk = rk;
		setFireRate(1600);
		dying = true;
		setDirection("up");
		onPath = true;
		
		/** setting default values */
		setDefaultValues(defaultX, defaultY);
		
		/** extracting png for tank and explosion */
		getEnemyTankImage();
		getExplosionImage();
	
		/** hit box */
		setHitbox( new Rectangle((int)getX(),(int)getY(),32,32));
	}
	
	/** determining direction of enemy tank moving towards player tank */
	public void determineDirection() 
	{
		/** determining which direction enemy tank is going in */
		boolean xFavored = false;
		if(Math.abs(dx)> Math.abs(dy)) {
			xFavored = true;
		}	
		if(xFavored) {
			if(dx>0)
				setDirection("right");
			else
			setDirection("left");
		}
		else {
			if(dy>0)
				setDirection("up");
			else
				setDirection("down");
		}
		
		
	}
	
	/** setting default values for enemy tank 
	 * @param x		enemy tank position on x axis
	 * @param y		enemy tank position on y axis
	 */
	public void setDefaultValues(int x, int y) {
		/** settings for enemy tank */
		this.setX(x);
		this.setY(y);
		setSpeed(1);

		calcDistance();
		setDead(false);
	}
	
	/** extracting image for enemy tank */
	public void getEnemyTankImage() {
		try {
			setUp3(ImageIO.read(getClass().getResourceAsStream("/enemies/cetop1.png")));
			setUp2(ImageIO.read(getClass().getResourceAsStream("/enemies/cetop2.png")));
			setUp1(ImageIO.read(getClass().getResourceAsStream("/enemies/cetop3.png")));
			setSide1(ImageIO.read(getClass().getResourceAsStream("/enemies/side1.png")));
			setSide2(ImageIO.read(getClass().getResourceAsStream("/enemies/side2.png")));
			setSide3(ImageIO.read(getClass().getResourceAsStream("/enemies/side3.png")));
			setHead(ImageIO.read(getClass().getResourceAsStream("/enemies/ehead.png")));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	/** extracting image for explosion*/
	public void getExplosionImage() {
		try {
			setEx1((ImageIO.read(getClass().getResourceAsStream("/explosion/explosion1.png"))));
			setEx2((ImageIO.read(getClass().getResourceAsStream("/explosion/explosion2.png"))));
			setEx3((ImageIO.read(getClass().getResourceAsStream("/explosion/explosion3.png"))));
			setEx4((ImageIO.read(getClass().getResourceAsStream("/explosion/explosion4.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** calculating distance from enemy tank to player tank */
	public void calcDistance() {
		dx = (getSpeed() * Math.cos(Math.toRadians(getAngle())));
		dy = (getSpeed() * Math.sin(Math.toRadians(getAngle())));
	}  
	
	/** update method for enemy tank */
	public void updateETank() 
	{
		/** setting action and checking collision */
		setAction(target);
		setCollisionY(false);
		panel.coll.checkColli(this);
		
		/** checking to see if enemy tank is eligible to move */
		if(getExplodCounter() == 0) {
			if(isCollisionY() == false)
			{
				switch(getDirection())
				{
					
					case "up":
						this.setY(getY()-getSpeed());
						break;
					case "down":
						this.setY(getY()+getSpeed());
						break;
					case "left":
						this.setX(this.getX()-getSpeed());
						break;
					case "right":
						this.setX(getX()+getSpeed());
						break;
				}
			}
		}
		
		/** shooting bullets */
		if ((convertToDateViaSqlTimestamp(LocalDateTime.now()).getTime() - lastBulletTime.getTime()) >= getFireRate() && panel.getTitleNum()!=0) {
			rk.getRocketsOnScreen().add(new rocket1(this));
			lastBulletTime = convertToDateViaSqlTimestamp(LocalDateTime.now());
			
		}
		
		/** setting up death animation rotation */
		if(this.dead) 
		{
			setDying(true);
			setExplodCounter(getExplodCounter() + 1);
			if(getExplodCounter()<33)
			{
				if(getExplodCounter()<10)
				{
					setExplodNum(0);
				}
				else if(getExplodCounter()>=10 && getExplodCounter()<20)
				{
					setExplodNum(1);
				}
				else if(getExplodCounter()>=20 && getExplodCounter()<30)
				{
					setExplodNum(2);
				}
				else
				{
					setExplodNum(3);
				}
			}
			else
			{
				setDying(false);
			}
		}
		
		/** calculating angle and distance of enemy tank to player tank */
		setAngle(calculatePathAngle(target));
		calcDistance();
	
		/** setting hitbox */
		setHitbox( new Rectangle((int)this.getX(),(int)this.getY(),32,32));
		
		/** setting up tank animation rotation */
		setTankCounter(getTankCounter() + 1);
		if(getTankCounter()>12) {
			if(getTankNum() == 1) {
				setTankNum(2);
			}
			else if(getTankNum() == 2) {
				setTankNum(3);
			}
			else if(getTankNum() == 3)
			{
				setTankNum(1);
			}
				setTankCounter(0);
			}
	}

	/** displaying/drawing the enemy tanks and exploding animation */
	public void draw(Graphics2D g2) {
		
		/** buffered image variables for enemy tank and explosion */
		BufferedImage image = null;
		BufferedImage top = getHead();
		BufferedImage explod = null;
		
		/** animation for enemy tank based on direction enemy tank is going towards */
		switch (getDirection()) {
		case "up":
			if (getTankNum() == 1) {
				image = getUp1();
			}
			if (getTankNum() == 2) {
				image = getUp2();
			}
			if (getTankNum() == 3) {
				image = getUp3();
			}
			break;
		case "left":
			//image = up1;
			if (getTankNum() == 1) {
				image = getSide1();
			}
			if (getTankNum() == 2) {
				image = getSide2();
			}
			if (getTankNum() == 3) {
				image = getSide3();
			}
			break;
		case "down":
			if (getTankNum() == 1) {
				image = getUp1();
			}
			if (getTankNum() == 2) {
				image = getUp2();
			}
			if (getTankNum() == 3) {
				image = getUp3();
			}
			break;
		case "right":
			//image = up1;
			if (getTankNum() == 1) {
				image = getSide1();
			}
			if (getTankNum() == 2) {
				image = getSide2();
			}
			if (getTankNum() == 3) {
				image = getSide3(); 
			}
			break;
		}
		
		/** death animations */
		if(dead == true)
		{
			if(getExplodNum() == 0)
			{
				explod = getEx1();
			}
			if(getExplodNum() == 1)
			{
				explod = getEx2();
			}
			if(getExplodNum() == 2)
			{
				explod = getEx3();
			}
			if(getExplodNum() == 3)
			{
				explod = getEx4();
			}
		}
		
		/** drawing/displaying all parts of animations including tank parts and explosion */
		g2.drawImage(image,(int)Math.round(getX()), (int)Math.round(getY()), panel.getTileSize(), panel.getTileSize(), null);
		g2.setColor(Color.BLUE);
		g2.drawImage(rotateGPT(top,90.0-getAngle()), (int)Math.round(getX()), (int)Math.round(getY()), panel.getTileSize(), panel.getTileSize(), null);
		g2.draw(getHitbox());
		g2.drawImage(explod,(int)Math.round(getX()), (int)Math.round(getY()), panel.getTileSize(), panel.getTileSize(), null);
		
	}
	
	/** sets action of enemy tank to find path towards player tank */
	public void setAction(PlayerTank target) {
		/** if it is on path, look for path from enemy tank to player tank */
		if(onPath = true) {
			int goalCol = (int)(target.getX()+16)/panel.getTileSize();
			int goalRow = (int)(target.getY()+16)/panel.getTileSize();
			
			/** uses search path */
			searchPath(goalCol, goalRow);

		}
	}
	
	/** searches path from enemy tank to player tank */
	public void searchPath(int goalCol, int goalRow) {
		/** obtains starting column and row of enemy tank */
		int startCol = (int) ((getX()+getSolid().x)/32);
		int startRow = (int) ((getY()+getSolid().y)/32);
		panel.getpFinder().setNodes(startCol, startRow, goalCol, goalRow, this);
		
		/** searching for path for enemy tank */
		if (panel.getpFinder().search()) {
			int nextX = panel.getpFinder().getPathList().get(0).getCol() * panel.getTileSize();
			int nextY = panel.getpFinder().getPathList().get(0).getRow() * panel.getTileSize();
			
			
			int enLeftX = (int)getX();
			int enRightX = (int)(getX()+getSolid().width);
			int enTopY = (int)getY();
			int enBottomY = (int)(getY()+ getSolid().height);
			
			if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + panel.getTileSize()) {
		
				setDirection("up");
	
			} 
			else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + panel.getTileSize()) {
				setDirection("down");
			} 
			 if (enTopY >= nextY && enBottomY < nextY + panel.getTileSize()) {
				
				if (enLeftX > nextX) {
					setDirection("left");

				}
				if (enLeftX < nextX) {
					setDirection("right");
			
				}
			} 
			
			 
			else if (enTopY > nextY && enLeftX > nextX) {
				
				setDirection("up"); 
				panel.coll.checkColli(this);
				if (isCollisionY()) {
					setDirection("left");
				}
			} else if (enTopY > nextY && enLeftX < nextX) {
				setDirection("up");
				panel.coll.checkColli(this);
				if (isCollisionY()) {
					setDirection("right");
				}
			} else if (enTopY < nextY && enLeftX > nextX) {
				setDirection("down");
				panel.coll.checkColli(this);
				if (isCollisionY()) {
					setDirection("left");
				}
			} else if (enTopY < nextY && enLeftX < nextX) {
				setDirection("down");
				panel.coll.checkColli(this);
				if (isCollisionY()) {
					setDirection("right");
				}
			}
					
			int nextCol = panel.getpFinder().getPathList().get(0).getCol();
			int nextRow = panel.getpFinder().getPathList().get(0).getRow();
			if (nextCol == goalCol && nextRow == goalRow) {
				onPath = false;
			}
			

		}
	}	

	/** rotates image without changing the center
	 *  @return rotatedImages	BufferedImage rotated without displacing center
	 */
	public static BufferedImage rotateGPT(BufferedImage image, double angle) {
		
		/** obtains width and height of image */
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();

        /** Set the rotation angle and the center of rotation */
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), width / 2, height / 2);

        /** apply the transformation to the graphics context */
        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }
	
	/** calculates path angle from enemy tank towards player tank 
	 * 	@return angle		calculated angle for enemy tank to travel
	 */
	public double calculatePathAngle(PlayerTank p1) {
		
		/** getting the center of enemy and player tank */
		double enemyTankCenterX = getX() + 8;
		double enemyTankCenterY = getY() + 8;
		double tankCenterX = p1.getX() + 8;
		double tankCenterY = p1.getY() + 8;
		
		/** getting rate of dx and dy */
	    double dx = tankCenterX - enemyTankCenterX;
	    double dy = enemyTankCenterY - tankCenterY;
	    
	    /** calculating angle */
	    double angle = Math.toDegrees(Math.atan(dy / dx));
	    
	    /** Adjust angle based on the quadrant the cursor is in
		    * quadrant 2 */
	    if (dx < 0 && dy >= 0) {
	        angle += 180;
	    } 
	    /** quadrant 3 */
	    else if (dx < 0 && dy < 0) { 
	        angle += 180;
	    } 
	    /** quadrant 4 */
	    else if (dx >= 0 && dy < 0) {
	        angle += 360;
	    	
	    }

	    return angle;
	}
	
	/** used for periodic time keeper 
	 * @param dateToConvert		local date time
	 * @return 		local date time as a date
	 */
	public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
	    return java.sql.Timestamp.valueOf(dateToConvert);
	}

	
	
	public PlayerTank getTarget() {
		return target;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public boolean isDying() {
		return dying;
	}
	public void setDying(boolean dying) {
		this.dying = dying;
	}
}