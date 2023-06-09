package entity;

import java.awt.image.BufferedImage;

import main.BackgroundPanel;

import java.awt.Rectangle;
import java.awt.*;
/**
 * 
 * @author Shreyas Sap
 * @author Ashmit Baghele
 * @author Alec Zhu
 * @author Cynthia Wu
 *
 */
public class Entity {
	/**
	 * creates fields needed for positioning and speed of entities
	 */
	private double x;
	private double y;
	private int mouseXPos;
	private int mouseYPos;
	private int speed;
	private double angle;
	
	/**
	 * represents the images of the entities
	 */
	private BufferedImage up1, up2, up3, side1, side2, side3, head;
	private BufferedImage ex1, ex2, ex3, ex4;
	private BufferedImage rocket;
	
	/**
	 * direction the entity is facing
	 */
	private String direction;

	/**
	 * counters needed for tank animation
	 */
	private int tankCounter = 0;
	private int tankNum = 1;
	private int explodCounter = 0;
	private int explodNum = 0;
	
	/**
	 * creates a rectangle used for collision
	 */
	private Rectangle solid;
	
	/**
	 * sets collision to false
	 */
	private boolean collisionY = false;
	
	/**
	 * fields which represent the direction of the entities movement
	 */
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	/**
	 * used for rocket collision
	 */
	private Rectangle hitbox;
	
	/**
	 * firerate of rockets 
	 */
	private int fireRate;
	
	/**
	*Returns the image of the rocket.
	*@return the rocket image
	*/
	public BufferedImage getRocket() {
		return rocket;
	}
	
	/**
	*Sets the image of the rocket.
	*@param rocket the rocket image to set
	*/
	public void setRocket(BufferedImage rocket) {
		this.rocket = rocket;
	}
	
	/**
	*Returns the speed of the rocket.
	*@return the speed of the rocket
	*/
	public int getSpeed() {
		return speed;
	}
	
	/**
	*Returns the angle of the rocket.
	*@return the angle of the rocket
	*/
	public double getAngle() {
		return angle;
	}
	
	/**
	*Returns the hitbox of the rocket.
	*@return the hitbox of the rocket
	*/
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	/**
	*Sets the hitbox of the rocket.
	*@param rect the hitbox rectangle to set
	*/
	public void setHitbox(Rectangle rect) {
		hitbox = rect;
	}
	
	/**
	*Returns the fire rate of the rocket.
	*@return the fire rate of the rocket
	*/
	public int getFireRate() {
		return fireRate;
	}
	
	/**
	*Sets the fire rate of the rocket.
	*@param fireRate the fire rate to set
	*/
	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}
	
	/**
	*Changes the alpha value of the graphics context.
	*@param g2 the graphics context
	*@param alphaValue the new alpha value
	*/
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	
	/**
	*Checks if the rocket is moving up.
	*@return true if the rocket is moving up, false otherwise
	*/
	public boolean isUp() {
		return up;
	}
	
	/**
	*Sets the direction of the rocket to up or down.
	*@param up true to move the rocket up, false to move it down
	*/
	public void setUp(boolean up) {
		this.up = up;
	}
	
	/**
	*Checks if the rocket is moving down.
	*@return true if the rocket is moving down, false otherwise
	*/
	public boolean isDown() {
		return down;
	}
	
	/**
	*Sets the direction of the rocket to down or up.
	*@param down true to move the rocket down, false to move it up
	*/
	public void setDown(boolean down) {
		this.down = down;
	}
	
	/**
	*Checks if the rocket is moving left.
	*@return true if the rocket is moving left, false otherwise
	*/
	public boolean isLeft() {
		return left;
	}
	
	/**
	*Sets the direction of the rocket to left or right.
	*@param left true to move the rocket left, false to move it right
	*/
	public void setLeft(boolean left) {
		this.left = left;
	}
	
	/**
	*Checks if the rocket is moving right.
	*@return true if the rocket is moving right, false otherwise
	*/
	public boolean isRight() {
		return right;
	}
	
	/**

	*Sets the direction of the rocket to right or left.
	*@param right true to move the rocket right, false to move it left
	*/
	public void setRight(boolean right) {
		this.right = right;
	}
	
	/**

	Returns the direction of the rocket.
	@return the direction of the rocket
	*/
	public String getDirection() {
		return direction;
	}
	
	/**
	*Sets the direction of the rocket.
	*@param direction the direction to set
	*/
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	Returns the solid rectangle representing the object.
	@return the solid rectangle
	*/
	public Rectangle getSolid() {
		return solid;
	}
	
	/**
	Sets the solid rectangle representing the object.
	@param solid the solid rectangle to set
	*/
	public void setSolid(Rectangle solid) {
		this.solid = solid;
	}
	
	/**
	Returns the explosion counter of the object.
	@return the explosion counter
	*/
	public int getExplodCounter() {
		return explodCounter;
	}
	
	/**
	Sets the explosion counter of the object.
	@param explodCounter the explosion counter to set
	*/
	public void setExplodCounter(int explodCounter) {
		this.explodCounter = explodCounter;
	}
	
	/**
	*Checks if there is a collision on the Y-axis.
	*@return true if there is a collision on the Y-axis, false otherwise
	*/
	public boolean isCollisionY() {
		return collisionY;
	}
	
	/**
	*Sets the collision flag for the Y-axis.
	*@param collisionY true to indicate a collision on the Y-axis, false otherwise
	*/
	public void setCollisionY(boolean collisionY) {
		this.collisionY = collisionY;
	}
	
	/**
	*Returns the explosion number of the object.
	*@return the explosion number
	*/
	public int getExplodNum() {
		return explodNum;
	}
	
	/**
	*Sets the explosion number of the object.
	*@param explodNum the explosion number to set
	*/
	public void setExplodNum(int explodNum) {
		this.explodNum = explodNum;
	}
	
	/**
	*Returns the tank counter of the object.
	*@return the tank counter
	*/
	public int getTankCounter() {
		return tankCounter;
	}
	
	/**
	*Sets the tank counter of the object.
	*@param tankCounter the tank counter to set
	*/
	public void setTankCounter(int tankCounter) {
		this.tankCounter = tankCounter;
	}
	
	/**
	*Returns the tank number of the object.
	*@return the tank number
	*/
	public int getTankNum() {
		return tankNum;
	}
	
	/**
	*Sets the tank number of the object.
	*@param tankNum the tank number to set
	*/
	public void setTankNum(int tankNum) {
		this.tankNum = tankNum;
	}

	/**
	*Sets the angle of the object.
	*@param angle the angle to set
	*/
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	/**
	*Returns the X coordinate of the object.
	*@return the X coordinate
	*/
	public double getX() {
		return x;
	}
	
	/**
	*Sets the X coordinate of the object.
	*@param x the X coordinate to set
	*/
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	*Returns the Y coordinate of the object.
	*@return the Y coordinate
	*/
	public double getY() {
		return y;
	}
	
	/**
	*Sets the Y coordinate of the object.
	*@param y the Y coordinate to set
	*/
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	*Sets the speed of the object.
	*@param speed the speed to set
	*/
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	*Returns the image for the up3 direction.
	*@return the up3 image
	*/
	public BufferedImage getUp3() {
		return up3;
	}
	
	/**
	*Sets the image for the up3 direction.
	*@param up3 the up3 image to set
	*/
	public void setUp3(BufferedImage up3) {
		this.up3 = up3;
	}
	
	/**
	*Returns the image for the up2 direction.
	*@return the up2 image
	*/
	public BufferedImage getUp2() {
		return up2;
	}
	
	/**
	*Sets the image for the up2 direction.
	*@param up2 the up2 image to set
	*/
	public void setUp2(BufferedImage up2) {
		this.up2 = up2;
	}
	
	/**
	*Returns the image for the up1 direction.
	*@return the up1 image
	*/
	public BufferedImage getUp1() {
		return up1;
	}
	
	/**
	*Sets the image for the up1 direction.
	*@param up1 the up1 image to set
	*/
	public void setUp1(BufferedImage up1) {
		this.up1 = up1;
	}
	
	/**
	*Returns the image for the side1 direction.
	*@return the side1 image
	*/
	public BufferedImage getSide1() {
		return side1;
	}
	
	/**
	*Sets the image for the side1 direction.
	*@param side1 the side1 image to set
	*/
	public void setSide1(BufferedImage side1) {
		this.side1 = side1;
	}
	
	/**
	*Returns the image for the side2 direction.
	*@return the side2 image
	*/
	public BufferedImage getSide2() {
		return side2;
	}
	
	/**
	*Sets the image for the side2 direction.
	*@param side2 the side2 image to set
	*/
	public void setSide2(BufferedImage side2) {
		this.side2 = side2;
	}
	
	/**
	*Returns the image for the side3 direction.
	*@return the side3 image
	*/
	public BufferedImage getSide3() {
		return side3;
	}
	
	/**
	*Sets the image for the side3 direction.
	*@param side3 the side3 image to set
	*/
	public void setSide3(BufferedImage side3) {
		this.side3 = side3;
	}
	
	/**
	*Returns the image for the head direction.
	*@return the head image
	*/
	public BufferedImage getHead() {
		return head;
	}
	
	/**
	*Sets the image for the head direction.
	*@param head the head image to set
	*/
	public void setHead(BufferedImage head) {
		this.head = head;
	}
	
	/**
	*Returns the image for the ex1 explosion state.
	*@return the ex1 image
	*/
	public BufferedImage getEx1() {
		return ex1;
	}
	
	/**
	*Sets the image for the ex1 explosion state.
	*@param ex1 the ex1 image to set
	*/
	public void setEx1(BufferedImage ex1) {
		this.ex1 = ex1;
	}
	
	/**
	*Returns the image for the ex2 explosion state.
	*@return the ex2 image
	*/
	public BufferedImage getEx2() {
		return ex2;
	}
	
	/**
	*Sets the image for the ex2 explosion state.
	*@param ex2 the ex2 image to set
	*/
	public void setEx2(BufferedImage ex2) {
		this.ex2 = ex2;
	}
	
	/**
	*Returns the image for the ex3 explosion state.
	*@return the ex3 image
	*/
	public BufferedImage getEx3() {
		return ex3;
	}
	
	/**
	*Sets the image for the ex3 explosion state.
	*@param ex3 the ex3 image to set
	*/
	public void setEx3(BufferedImage ex3) {
		this.ex3 = ex3;
	}
	
	/**
	*Returns the image for the ex4 explosion state.
	*@return the ex4 image
	*/
	public BufferedImage getEx4() {
		return ex4;
	}
	
	/**
	*Sets the image for the ex4 explosion state.
	*@param ex4 the ex4 image to set
	*/
	public void setEx4(BufferedImage ex4) {
		this.ex4 = ex4;
	}
	
	/**
	*Returns the X position of the mouse.
	*@return the X position of the mouse
	*/
	public int getMouseXPos() {
		return mouseXPos;
	}
	
	/**
	*Sets the X position of the mouse.
	*@param mouseXPos the X position of the mouse to set
	*/
	public void setMouseXPos(int mouseXPos) {
		this.mouseXPos = mouseXPos;
	}
	
	/**
	*Returns the Y position of the mouse.
	*@return the Y position of the mouse
	*/
	public int getMouseYPos() {
		return mouseYPos;
	}
	
	/**
	*Sets the Y position of the mouse.
	*@param mouseYPos the Y position of the mouse to set
	*/
	public void setMouseYPos(int mouseYPos) {
		this.mouseYPos = mouseYPos;
	}
		
	}
