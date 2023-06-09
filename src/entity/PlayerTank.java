package entity;

import main.KeyHandler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import tile.TileManager;
import javax.swing.Timer;

import javax.imageio.ImageIO;

import main.BackgroundPanel;

/**creates the object for tank controlled by the player
 * 
 * @author Alec Zhu
 * @author Cynthia Wu
 */

public class PlayerTank extends Entity {
	/** creates background game panel */
	private BackgroundPanel panel;
	
	/** variable for key handler, the control of the keys */
	private KeyHandler kh;
	
	/** number of lives for player tank */
	private int lives;
	
	/** determiens if tank is dead*/
	private boolean dead;
	
	/** constructor for player tank 
	 * @param p		background game panel 
	 * @param k		key handler for the function of keys
	 */
	public PlayerTank(BackgroundPanel p, KeyHandler k) {
		/** instantiating the parameters */
		panel = p;
		kh = k;
		/** sets bounds, values, location, image, lives, and fire rate of player tank */
		setSolid(new Rectangle(0,0,32,32));
		setDefaultValues();
		setHitbox( new Rectangle((int)getX(),(int)getY(),32,32));
		getPlayerImage();		
		lives = 3;
		setFireRate(900);
	}
	
	/** sets default values for player tank */
	public void setDefaultValues() {
		
		/** default location, speed, and direction of player tank */
		setX(75);
		setY(250);
		setSpeed(2);
		setDirection("up");

	}
	
	/** extracts player tank image */
	public void getPlayerImage() {
		try {
			setUp1(ImageIO.read(getClass().getResourceAsStream("/player/up1.png")));
			setUp2(ImageIO.read(getClass().getResourceAsStream("/player/up2.png")));
			setSide1(ImageIO.read(getClass().getResourceAsStream("/player/side1.png")));
			setSide2(ImageIO.read(getClass().getResourceAsStream("/player/side2.png")));
			setHead(ImageIO.read(getClass().getResourceAsStream("/player/head.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** rotates image and keeping the center in the same place 
	 * 
	 * @param image		png of player tank
	 * @param angle		angle of png of player tank
	 * @return			rotated image without moving the center
	 */
	public static BufferedImage rotateGPT(BufferedImage image, double angle) {
		
		/** width and height of player tank png */
        int width = image.getWidth();
        int height = image.getHeight();
        
        /** creates image and rotate */
        BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();

        /** set the rotation angle and the center of rotation */
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), width / 2, height / 2);

        /** apply the transformation to the graphics context */
        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }
	
	/** update components of player tank: ex. movement, collision check, etc. */
	public void update() {
		
		/** updates amount of lives the player has in order to determine if alive or not*/
		updateLives();
		
		/** checks pressed key, set to corresponding direction only if alive*/
		if(!isDead()) {
		if(kh.iswPressed() == true||kh.isaPressed() == true||kh.issPressed() == true||kh.isdPressed() == true) {
			if(kh.iswPressed() == true) {
				setDirection("up");
			}
			if(kh.isaPressed() == true) {
				setDirection("left");
			}
			if(kh.issPressed() == true) {
				setDirection("down");
			}
			if(kh.isdPressed() == true) {
				setDirection("right");
			}
			
			/** check tile collision */
			setCollisionY(false);
			panel.coll.checkColli(this);
			
			
			/** operation IF tile doesn't collide */
			if(isCollisionY() == false)
			{
				switch(getDirection())
				{
					case "up":
						setY(getY() - getSpeed());
						break;
					case "down":
						setY(getY() + getSpeed());
						break;
					case "left":
						setX(getX() - getSpeed());
						break;
					case "right":
						setX(getX() + getSpeed());
						break;
				}
			}
			
			/**update the hitbox */
			setHitbox( new Rectangle((int)getX(),(int)getY(),32,32));
			
			setTankCounter(getTankCounter() + 1);
			if(getTankCounter()>12) {
				if(getTankNum() == 1) {
					setTankNum(2);
				}
				else if(getTankNum() == 2) {
					setTankNum(1);
				}
				setTankCounter(0);
			}
		}
		}
		
		/** adds mouse listener for mouse position */
		panel.addMouseMotionListener(new MouseMotionListener() {
			
			/** check if mouse dragged */
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
			
			/** check if mouse moved */
			@Override
			public void mouseMoved(MouseEvent e) {
				setMouseXPos(e.getX());
				setMouseYPos(e.getY());

			}
		});
		
	}
	
	/** draws, or displays player tank onto the background game panel
	 * 	only draws if alive 
	 * @param g2	graphics2D variable
	 */
	public void draw(Graphics2D g2) {
		if(!isDead()) {
		
			/** variable for buffered image and rotation */
			BufferedImage image = null;
			BufferedImage top = getHead();
			double rotate;
	
			/** animation for player tank based on direction */
			switch (getDirection()) {
			case "up":
				if (getTankNum() == 1) {
					image = getUp1();
				}
				if (getTankNum() == 2) {
					image = getUp2();
				}
				break;
			case "left":
				image = getUp1();
				if (getTankNum() == 1) {
					image = getSide1();
				}
				if (getTankNum() == 2) {
					image = getSide2();
				}
				break;
			case "down":
				if (getTankNum() == 1) {
					image = getUp1();
				}
				if (getTankNum() == 2) {
					image = getUp2();
				}
				break;
			case "right":
				image = getUp1();
				if (getTankNum() == 1) {
					image = getSide1();
				}
				if (getTankNum() == 2) {
					image = getSide2();
				}
				break;
		}
		
		/** draws image of player tank: the tank body, the head, and hitbox */
		g2.drawImage(image, (int)Math.round(getX()), (int)Math.round(getY()), panel.getTileSize(), panel.getTileSize(), null);
		g2.drawImage(rotateGPT(getHead(),90.0-getAngle()), (int)Math.round(getX()), (int)Math.round(getY()), panel.getTileSize(), panel.getTileSize(), null);
		g2.setColor(Color.CYAN);
		g2.draw(getHitbox());
		}
	}
	
	/** gets angle of the mouse to the player tank
	 * @return angle	returns the double value of angle of the mouse to the player tank
	 */
	public double getAngle() {
		
		/** x and y coordinates for middle center of player tank */
		double tankCenterX = getX() + 8;
		double tankCenterY = getY() + 8;
		
		/** x and y coordinates for location of mouse */
	    double dx = getMouseXPos() - tankCenterX;
	    double dy = tankCenterY - getMouseYPos();
	    
	    /** calculate the angle of the mouse to the player tank */
	    double angle = Math.toDegrees(Math.atan(dy / dx));
	    
	    
	    /**Adjust angle based on the quadrant the cursor is in */
	    if (dx < 0 && dy >= 0) {
	        angle += 180;
	    } 
	    
	    else if (dx < 0 && dy < 0) { 
	        angle += 180;
	    } 
	   
	    else if (dx >= 0 && dy < 0) {
	        angle += 360;
	    	
	    }

	    return angle;
	}
	
	/**
	 * adjusts the lives when player tank is hit by a rocket and then sends to death screen if dead
	 */
	public void updateLives() {
		for(int i =0; i < panel.getRocketM().getRocketsOnScreen().size(); i++) {
			
			if( panel.getRocketM().getRocketsOnScreen().get(i).isEnemyFired() && panel.coll.checkRocketAndPlayerCollision(this, panel.getRocketM().getRocketsOnScreen().get(i))) {
				lives--;
				
			}
			if(lives == 0) {
				panel.getRocketM().setShoot(dead);
				setDead(true);
				panel.setTitleNum(2);
			}
		}
		
	}
	
	public void respawn() {
		setX(75);
		setY(250);
	}
	public void setLives(int lives) {
		this.lives = lives;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}