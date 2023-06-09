package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import main.BackgroundPanel;
import entity.Rocket1Manager;
/**
 * 
 * @author Alec Zhu
 * @author Shreyas Sao
 *
 */
public class EnemyManager 
{
	private ArrayList<EnemyTank> enemies; 
	private BackgroundPanel panel;
	private PlayerTank tank;
	private Rocket1Manager rkManager;
	
	public EnemyManager(BackgroundPanel p, PlayerTank tank, Rocket1Manager rks) {
		panel = p;
		enemies = new ArrayList<EnemyTank>();
		this.tank = tank;
		rkManager = rks;
		if(panel.getMap() == 1) {
			addTank(900,100);
			addTank(900,400);
			addTank(100,360);
		}

	}
	
	public void addTank(int x, int y) {
		enemies.add(new EnemyTank(panel,x,y,tank,rkManager));
	}
	
	public void updateETankManager() {	
		if(enemies.size()!=0) {	
			for(int i = 0; i<enemies.size(); i++) {
					enemies.get(i).updateETank();
				for(int j = 0; j< rkManager.getRocketsOnScreen().size(); j++) {
					panel.coll.checkRocketAndEnemyCollision(enemies.get(i),rkManager.getRocketsOnScreen().get(j) );
				}
			}
			clearEnemies();
		}
		
	}
	
	public void clearEnemies() {
		for(int i = 0; i < enemies.size(); i++) {
			if(!enemies.get(i).isDying() && enemies.get(i).isDead()) {
				enemies.remove(i);
				
				i--;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		if(enemies.size()!= 0) {
			for(int i = 0; i<enemies.size(); i++) {
				enemies.get(i).draw(g2);
			}
		}	
	}
	
	public void respawn() {
		if(panel.getMap() == 2) {
			addTank(400,100);
			addTank(500,400);
			addTank(600,260);
		}
		else if(panel.getMap() == 3) {
			addTank(400,100);
			addTank(500,400);
			addTank(600,260);
			addTank(750,400);
			for(int i = 0; i < enemies.size(); i++) {
				enemies.get(i).setFireRate(500);
			}
		}
	}
	
}