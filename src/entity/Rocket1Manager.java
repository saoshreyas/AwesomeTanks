package entity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import java.awt.*;

import main.BackgroundPanel;
import main.KeyHandler;

import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

/**
 * 
 *@author Shreyas Sao
 *@author Ashmit Baghele
 *@author Alec Zhu
 *
 */
public class Rocket1Manager
{
	/**
	 * array list containing the rockets used in the game
	 */
	private ArrayList<rocket1> rockets;
	
	/**
	 * BackgroundPanel used for rockets
	 */
	private BackgroundPanel gp;
	
	/**
	 * KeyHandler used for rockets
	 */
	private KeyHandler kh;
	
	/**
	 * Player Tank used for rockets
	 */
	private PlayerTank tank;
	
	/**
	 * fields which determine if the tanks can shoot and when their last shot was
	 */
	private boolean canShoot;
	private Date lastShot;

	/** Initializes the instance variables
	 * 
	 * @param gp	BackgroundPanel used for class
	 * @param key	KeyHandler used for class
	 * @param p1	PlayerTank used  for class
	 */
	public Rocket1Manager(BackgroundPanel gp, KeyHandler key, PlayerTank p1) {
		this.gp = gp;
		this.kh = key;
		tank = p1;
		rockets = new ArrayList<rocket1>();
		canShoot = false;
		lastShot = convertToDateViaSqlTimestamp(LocalDateTime.now());

		
	}
	
	/**
	 * determines if the tank can shoot
	 * shoots if space is pressed and it can shoot; resets lastShot 
	 * cleans the screen of rockets(removes them when needed)
	 */
	public void update() {
		if ((convertToDateViaSqlTimestamp(LocalDateTime.now()).getTime() - lastShot.getTime()) >= tank.getFireRate()) {
		    //lastShot = convertToDateViaSqlTimestamp(LocalDateTime.now());
		    canShoot = true;
		}
		if(kh.isSpacePressed() && canShoot) {
			getRocketsOnScreen().add(new rocket1(tank));
			lastShot = convertToDateViaSqlTimestamp(LocalDateTime.now());
			canShoot = false;
		}
		for(int i = 0; i< getRocketsOnScreen().size();i++) {
			getRocketsOnScreen().get(i).update();
		}
		cleanRockets();

	}
	
	/** draws the rockets on the screen
	 *
	 * @param g2	Graphics2D object which allows drawing
	 */
	public void draw(Graphics2D g2) {
		//rocket is 16x9
		for(int i = 0; i<getRocketsOnScreen().size(); i++) {
			g2.drawImage(
					rotateGPT(getRocketsOnScreen().get(i).getRocket(),360.0-getRocketsOnScreen().get(i).getAngle()),
					(int)Math.round(getRocketsOnScreen().get(i).getX()),(int)Math.round(getRocketsOnScreen().get(i).getY()),16,9,null);
			g2.setColor(Color.MAGENTA);
			g2.draw(getRocketsOnScreen().get(i).getHitbox());
		}	
	}
	
	/**
	 * returns rockets ArrayList
	 * @return ArrayList of rockets
	 */
	public ArrayList<rocket1> getRockets(){
		return getRocketsOnScreen();
	}
	
	/**
	 * checks if the rockets on the screen collide with tiles,
	 * if so removes them off the screen
	 */
	public void cleanRockets() {
		for(int i = 0; i<getRocketsOnScreen().size(); i++) {
			gp.coll.checkRocketColli(getRocketsOnScreen().get(i));

			if(getRocketsOnScreen().get(i).isCollisionY()||isOffscreen(getRocketsOnScreen().get(i))) {
				getRocketsOnScreen().remove(i);
				i--;
			}
		}
	}
	
	/** rotates a BufferedImage by a given angle
	 * 
	 * @param image		BufferedImage which is rotated
	 * @param angle		amount BufferedImage is rotated in degrees
	 * @return			the rotated image
	 */
	public static BufferedImage rotateGPT(BufferedImage image, double angle) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();

        /**Set the rotation angle and the center of rotation*/
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), width / 2, height / 2);

        /**Apply the transformation to the graphics context*/
        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }
	
	/** converts a LocalDateTime to Date
	 * 
	 * @param dateToConvert		LocalDateTime which is being converted
	 * @return					converted Date
	 */
	public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
	    return java.sql.Timestamp.valueOf(dateToConvert);
	}
	
	/** checks if rk is offscreen
	 * 
	 * @param rk 	rocket1 which is being checked whether its offscreen
	 * @return		whether or not rk is offscreen
	 */
	public boolean isOffscreen(rocket1 rk) {
		boolean off;
		if(gp.getScreen().contains(rk.getX(),rk.getY(),16,9)) {
			off = false;
		}
		else {
			off = true;
		}
		return off;
	}
	
	/** returns rockets 
	 * 
	 * @return rockets on the screen
	 */
	public ArrayList<rocket1> getRocketsOnScreen() {
		return rockets;
	}
	
	public void setShoot(boolean shoot) {
		canShoot = shoot;
	}
}