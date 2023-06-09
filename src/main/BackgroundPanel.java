package main;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import Pathfinding.Pathfinder;
import entity.EnemyManager;
import entity.EnemyTank;
import entity.PlayerTank;
import entity.Rocket1Manager;
import entity.rocket1;
import tile.TileManager;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JPanel;
/** Creates the screen which all objects and tiles will be displayed on 
 * 
 * @author Alec Zhu
 * @author Ashmit Baghele
 * @author Cynthia Wu
 * @author Shreyas Sao
 *
 */
public class BackgroundPanel extends JPanel implements Runnable , KeyListener
{
	/**screen settings*/
	private int originalTileSize = 16;
	private int scale = 2;
	
	/**
	 * scales the tiles to the correct size
	 */
	private  int tileSize = originalTileSize * scale;
	
	/**number of columns  and rows*/
	private int maxScreenCol = 32;
	private int maxScreenRow = 16; 
	
	/**sets the screen width and height*/
	private int screenWidth = getTileSize() * getMaxScreenCol();
	private  int screenHeight = getTileSize() * getMaxScreenRow();
	
	/**
	 * represents state of title screen
	 */
	private int titleState = 0;
	
	/**creates a rectangle the size of the screen*/
	private Rectangle screen = new Rectangle(0,0,screenWidth, screenHeight);
	
	/**sets the player speed and fps and map */
	private int playerSpeed = 1;
	private int FPS = 60;
	private int map = 1;
	
	/**
	 * creates the keyhandler, thread, and collision detector needed within the game
	 */
	private KeyHandler kh = new KeyHandler();
	private Thread thread;
	public CollisionDetec coll = new CollisionDetec(this);
	
	/**
	 * creates the needed objects in order to run the game
	 */
	private PlayerTank p1 = new PlayerTank(this, kh);
	private TileManager tileM = new TileManager(this);
	private Rocket1Manager rocketManager = new Rocket1Manager(this,kh,p1);
	private EnemyManager badTanks = new EnemyManager(this,p1, rocketManager);
	private Pathfinder pFinder = new Pathfinder(this);
	
	/**
	 * represents amount of dead tanks
	 */
	private int deadTanks = 0;
	
	
	/**
	 * fields which define the players starting pressure
	 */
	private int p1x = 400;
	private int p1y = 400;
	
	/**
	 * sets the size of the screen which contains the game objects
	 */
	public BackgroundPanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(kh);
		this.setFocusable(true);
		
	}
	
	/**
	 * allows for multitasking to occur and the program runs more efficiently
	 */
	public void startGameThread() {
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	/**
	 * creates the loop which allows  for the game to run smoother
	 */
	public void run() {
		// TODO Auto-generated method stub
		/**creates the variables which are the time difference between each update/draw */
		double intervalTime = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + intervalTime;
		
		/**
		 * while multitasking is on the BackgroundPanel will keep redrawing and updating every
		 * nextDrawTime 
		 */
		while(thread!=null) {
			/** time of program working */
			long currentTime = System.nanoTime();
			
			update();
			
			
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime/=1000000;
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				/** pauses the execution of the game loop for the amount of time remaining */
				thread.sleep((long)remainingTime);
				nextDrawTime += intervalTime;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		
		}
	}
	
	/**
	 * runs the update methods of each entity
	 */
	public void update() {
		p1.update();
		rocketManager.update();
		badTanks.updateETankManager();
		//System.out.println("hi");
	}
	
	/** displays title screen
	 * @param g 	graphics object which draws  
	 */
	@SuppressWarnings("unused")
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		/**
		 * sets the the color to white
		 */
		g2.setColor(Color.white);
		
		/**
		 * displays the title screen and the words on it
		 */
		if (titleState == 0) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
			String title = "Awesome Tanks";
			g2.setColor(Color.white);
			g2.drawString(title, 100, 100);

			String prompt = "Press enter to play!";

			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
			g2.setColor(Color.white);
			g2.drawString(prompt, screenWidth / 2 - 120, 250);

		}
		
		/**
		 * displays the game over screen when needed
		 */
		else if(titleState == 2) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
			String title = "You Died";
			g2.setColor(Color.white);
			g2.drawString(title, 100, 100);

			String prompt = "Game Over";

			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
			g2.setColor(Color.white);
			g2.drawString(prompt, screenWidth / 2 - 120, 250);
			
			for(int i = 0; i<getRocketM().getRocketsOnScreen().size(); i++) {
				getRocketM().getRocketsOnScreen().remove(i);
			}
			
		}
		
		/**
		 * displays end of level one screen
		 */
		else if(titleState == 3) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
			String title = "You Passed Level One";
			g2.setColor(Color.cyan);
			g2.drawString(title, 100, 100);

			String prompt = "Click Two to move on";

			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
			g2.setColor(Color.cyan);
			g2.drawString(prompt, screenWidth / 2 - 120, 250);
			for(int i = 0; i<getRocketM().getRocketsOnScreen().size(); i++) {
				getRocketM().getRocketsOnScreen().remove(i);
			}
		}
		
		/**
		 * displays end of level two screen
		 */
		else if(titleState == 4) {
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
				String title = "You Passed Level Two";
				g2.setColor(Color.cyan);
				g2.drawString(title, 100, 100);

				String prompt = "Press Three to move on";

				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
				g2.setColor(Color.cyan);
				g2.drawString(prompt, screenWidth / 2 - 120, 250);
				for(int i = 0; i<getRocketM().getRocketsOnScreen().size(); i++) {
					getRocketM().getRocketsOnScreen().remove(i);
				}
			
			
		}
		
		/**
		 * displays end of game screen, if win
		 */
		else if(titleState == 5) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
			String title = "MEOW";
			g2.setColor(Color.cyan);
			g2.drawString(title, 100, 100);

			String prompt = "You are da true tankmaster";

			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
			g2.setColor(Color.cyan);
			g2.drawString(prompt, screenWidth / 2 - 120, 250);
			for(int i = 0; i<getRocketM().getRocketsOnScreen().size(); i++) {
				getRocketM().getRocketsOnScreen().remove(i);
			}
		
		
	}
		
		/**
		 * displays the game when not displaying the title screen
		 */
		else {
			
			tileM.draw(g2);
			p1.draw(g2);
			rocketManager.draw(g2);
			badTanks.draw(g2);
		}

		g2.dispose();
	}
	
	

	/**
	 * adds a KeyListner to the BackgroundPanel
	 */
	public void addKeyListenerToPanel() {
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	/**
	 * does nothing
	 * @param e	indicates a keystroke
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * when the correct button is pressed the past screen goes away the game screen appears in accordance to the level
	 * @param e represents a keystroke
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch(code) {
			case KeyEvent.VK_ENTER:
				if(this.titleState ==0) {
					this.titleState = 1;
				}
				break;
			case KeyEvent.VK_2:
				if(this.titleState == 3){
				this.titleState = 1;
				p1.setLives(3);
				setMap(2);
				tileM.loadMap(tileM.chooseMap(getMap()));
				badTanks.respawn();
				p1.respawn();
				}
				break;
			case KeyEvent.VK_3:
				if(this.titleState == 4){
				this.titleState = 1;
				p1.setLives(3);
				setMap(3);
				tileM.loadMap(tileM.chooseMap(getMap()));
				badTanks.respawn();
				p1.respawn();
				}
				break;
		}
		
	}
	
	/**
	 * does nothing
	 * @param e represents a keystroke
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	/** returns screen
	 * 
	 * @return	a rectangle the size of the screen
	 */
	public Rectangle getScreen() {
		return screen;
	}
	
	/** returns number of columns in the screen
	 * 
	 * @return	number of columns in the screen
	 */
	public int getMaxScreenCol() {
		return maxScreenCol;
	}
	
	/**	changes number of columns in screen
	 * 
	 * @param maxScreenCol	represents number of columns in the screen
	 */
	public void setMaxScreenCol(int maxScreenCol) {
		this.maxScreenCol = maxScreenCol;
	}
	
	/** returns number of rows in screen
	 * 
	 * @return	number of rows in screen
	 */
	public int getMaxScreenRow() {
		return maxScreenRow;
	}
	
	/**
	 * changes number of rows 
	 * @param maxScreenRow
	 */
	public void setMaxScreenRow(int maxScreenRow) {
		this.maxScreenRow = maxScreenRow;
	}
	
	/**
	 * returns scaled size of tile
	 * @return	scaled  size of tile
	 */
	public int getTileSize() {
		return tileSize;
	}
	
	/**
	 * changes scaled size of tile
	 * @param tileSize	represents scaled size of tile
	 */
	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}
	
	/**
	 * returns the speed of the player
	 * @return speed of player
	 */
	public int getPlayerSpeed() {
		return playerSpeed;
	}
	
	/**
	 * changes the player speed
	 * @param playerSpeed	new player speed
	 */
	public void setPlayerSpeed(int playerSpeed) {
		this.playerSpeed = playerSpeed;
	}
	
	/**
	 * returns which map it is 
	 * @return	which map it is
	 */
	public int getMap() {
		return map;
	}
	
	/**
	 * changes which map it is
	 * @param map	which map it will be changed to
	 */
	public void setMap(int map) {
		this.map = map;
	}
	
	/**
	 * returns the TileManager used for the BackgroundPanel
	 * @return TileManager used for the BackgroundPanel
	 */
	public TileManager getTileM() {
		return tileM;
	}
	
	/**
	 * changes the TileManager used for the BackgroundPanel
	 * @param tileM	the new TileManager used for the BackgroundPanel
	 */
	public void setTileM(TileManager tileM) {
		this.tileM = tileM;
	}
	
	/**
	 * returns RocketManager
	 * @return	the RocketManager used in BackgroundPanel
	 */
	public Rocket1Manager getRocketM() {
		return rocketManager;
	}
	
	/**
	 * changes the titleState
	 * @param title 	state which the title is set up
	 */
	public void setTitleNum(int title) {
		titleState = title;
	}
	
	public int getTitleNum() {
		return titleState;
	}
	
	public int getDeadTanks() {
		return deadTanks;
	}

	public void setDeadTanks(int deadTanks) {
		this.deadTanks = deadTanks;
	}

	public Pathfinder getpFinder() {
		return pFinder;
	}

	public void setpFinder(Pathfinder pFinder) {
		this.pFinder = pFinder;
	}
}