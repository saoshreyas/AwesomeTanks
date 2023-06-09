package tile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import java.awt.*;

import main.BackgroundPanel;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
/** Draws background of the game utilizing many Tiles 
 * 
 * @author Alec Zhu
 * @author Shreyas Sao
 *
 */
public class TileManager {
	/**declare private fields representing the BackgroundPanel on the screen*/
	private BackgroundPanel gp;
	
	/**declares an array which will consist of all possible tiles*/
	private Tile[] tile;
	
	/**declares a 2D array which represents the screen and is utilized as a map*/
	private int[][] map;
	
	/** Instantiates the private fields
	 * @param gp		represents the BackgroundPanel being displayed on the screen*/
	public TileManager(BackgroundPanel gp) {
		/** this.gp is set the to the BackgroundPanel displayed on the screen*/
		this.gp = gp;
		/** sets the length of tile to 10*/
		setTile(new Tile[10]);
		/** sets the length and width of map to the full screen  */
		map = new int[gp.getMaxScreenCol()][gp.getMaxScreenRow()];
		
		/** initializes tile to contain all possible tiles*/
		getTileImage();
		/**
		 * 
		 */
		
		loadMap(chooseMap(gp.getMap()));
	}
	
	public String chooseMap(int map) {
		if(map ==1)
			return "/maps/Map2.txt";
		else if(map==2) {
			return "/maps/mapTest.txt";
		}
		else if(map ==3) {
			return "/maps/Map3.txt";
		}
		return null;
	}
	/**
	 * returns map
	 * @return		returns 2D array which represents the map/screen
	 */
	public int[][] getMap(){
		return map;
	}
	
	
	
	/**
	 * fills the tile array with all possible tiles
	 */
	public void getTileImage() {
		try {			  
			getTile()[0] = new Tile();
	        getTile()[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass2.png"));
	        getTile()[1] = new Tile();
	        getTile()[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/woody.png"));
	        getTile()[1].collision = true;
	        getTile()[2] = new Tile();
	        getTile()[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water2.png"));
	        getTile()[3] = new Tile();
	        getTile()[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lavabig.png"));
	        getTile()[4] = new Tile();
	        getTile()[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
	        getTile()[5] = new Tile();
	        getTile()[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lava.png"));
	        getTile()[6] = new Tile();
	        getTile()[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sandstone.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * reads the text file and puts each value into map  
	 * @param txtfile		a .txt file which is the map for the tiles
	 */
	public void loadMap(String txtfile) {
		try {
			/**creates an InputStream and Buffered Reader which will read through 
			 * the parameter (txtfile)*/
			InputStream input = getClass().getResourceAsStream(txtfile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			int col = 0;
			int row = 0;
			
			/**loop which will iterate through each value separated 
			 * by a space within each column going row by row*/
			while(col <gp.getMaxScreenCol() && row <gp.getMaxScreenRow()) {
				String result = reader.readLine();
				while(col < gp.getMaxScreenCol()) {
					String numbers[] = result.split(" ");
					int num = Integer.parseInt(numbers[col]);
					map[col][row] = num;
					col++;
				}
				if(col == gp.getMaxScreenCol()) {
					col = 0;
					row++;
				}
			}
			reader.close();	
		}catch(Exception e){
			
		}
	}
	
	/**
	 * draw the map on the screen
	 * @param g2 	graphics2D object which draws  
	 */
	public  void draw(Graphics2D g2) {
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		/**
		 * iterates through each value in map and draws the Tile 
		 * which correlates to the value from the array tile
		 */
		while(col <gp.getMaxScreenCol() && row < gp.getMaxScreenRow()) {
			int tileNum = map[col][row];
			
			g2.drawImage(getTile()[tileNum].image, x, y, gp.getTileSize(), gp.getTileSize(), null);
			col++;
			x+= gp.getTileSize();
			if(col == gp.getMaxScreenCol()) {
				col = 0;
				x= 0;
				row++;
				y+=gp.getTileSize();
			}
			
		}
	}
	
	/**@return 	returns the array containing all possible tiles*/
	public Tile[] getTile() {
		return tile;
	}
	
	/**
	 * sets the tile array to another  one
	 * @param tile	an array consisting of only Tiles
	 */
	public void setTile(Tile[] tile) {
		this.tile = tile;
	}
	

	
	
}