package main;

import entity.EnemyTank;
import entity.Entity;
import entity.PlayerTank;
import entity.rocket1;

/**creates methods which will check for collision between entities and tiles
 * 
 * @author Cynthia Wu
 * @author Alec Zhu
 * @author Shreyas Sao
 */
public class CollisionDetec {
	/**
	 * BackgroundPanel used within the CollisionDetec Class
	 */
	private BackgroundPanel bp;
	
	/**
	 * sets the BackgroundPanel declared to the one given
	 * @param bp	BackgroundPanel to be used
	 */
	public CollisionDetec(BackgroundPanel bp) {
		this.bp = bp;
	}
	
	/**
	 * checks collision for the given entity
	 * @param ent	entity in which collision is checked for
	 */
	public void checkColli(Entity ent) {
		/**
		 * defines cardinal sides of entity and defines which tile each side is in
		 * sets collision to not true
		 */
		int entLeft = (int) ent.getX() + ent.getSolid().x;
		int entRight = (int) ent.getX() + ent.getSolid().x + ent.getSolid().width;
		int entTop = (int) ent.getY() + ent.getSolid().y;
		int entBot = (int) ent.getY() + ent.getSolid().y + ent.getSolid().height;
		ent.setCollisionY(false);

		int entLeftCol = entLeft / bp.getTileSize();
		int entRightCol = entRight / bp.getTileSize();
		int entTopRow = entTop / bp.getTileSize();
		int entBotRow = entBot / bp.getTileSize();
		
		/**
		 * represents the tiles being checked for collision 
		 */
		int tileN1, tileN2;
		
		/**
		 * switchcase which chooses is based on the direction of the entity, 
		 * which determines which column and row are checked for colliding with the given tiles
		 */
		switch (ent.getDirection()) {
		case "up":
			entTopRow = (entTop - ent.getSpeed()) / bp.getTileSize();
			tileN1 = bp.getTileM().getMap()[entLeftCol][entTopRow];
			tileN2 = bp.getTileM().getMap()[entRightCol][entTopRow];
			if (bp.getTileM().getTile()[tileN1].collision == true || bp.getTileM().getTile()[tileN2].collision == true) {
				ent.setCollisionY(true);
			}
			break;
		case "down":
			entBotRow = (entBot + ent.getSpeed()) / bp.getTileSize();
			tileN1 = bp.getTileM().getMap()[entLeftCol][entBotRow];
			tileN2 = bp.getTileM().getMap()[entRightCol][entBotRow];
			if (bp.getTileM().getTile()[tileN1].collision == true || bp.getTileM().getTile()[tileN2].collision == true) {
				ent.setCollisionY(true);
			}
			break;
		case "left":
			entLeftCol = (entLeft - ent.getSpeed()) / bp.getTileSize();
			tileN1 = bp.getTileM().getMap()[entLeftCol][entTopRow];
			tileN2 = bp.getTileM().getMap()[entLeftCol][entBotRow];
			if (bp.getTileM().getTile()[tileN1].collision == true || bp.getTileM().getTile()[tileN2].collision == true) {
				ent.setCollisionY(true);
			}
			break;
		case "right":
			entRightCol = (entRight + ent.getSpeed()) / bp.getTileSize();
			tileN1 = bp.getTileM().getMap()[entRightCol][entTopRow];
			tileN2 = bp.getTileM().getMap()[entRightCol][entBotRow];
			if (bp.getTileM().getTile()[tileN1].collision == true || bp.getTileM().getTile()[tileN2].collision == true) {
				ent.setCollisionY(true);
			}
			break;
		}
	}
	
	/**
	 * checks collision for the given rocket1
	 * @param ent	rocket1 in which collision is checked for
	 */
	public void checkRocketColli(rocket1 rocket) {
		
		/**
		 * defines cardinal sides of entity and defines which tile each side is in
		 */
		int entLeft = (int) (rocket.getX() + rocket.getSolid().x);
		int entRight = (int) (rocket.getX() + rocket.getSolid().x + rocket.getSolid().width);
		int entTop = (int) (rocket.getY() + rocket.getSolid().y);
		int entBot = (int) (rocket.getY() + rocket.getSolid().y + rocket.getSolid().height);

		int entLeftCol = entLeft / bp.getTileSize();
		int entRightCol = entRight / bp.getTileSize();
		int entTopRow = entTop / bp.getTileSize();
		int entBotRow = entBot / bp.getTileSize();
		
		/**
		 * represents the tiles being checked for collision 
		 */
		int tileN1, tileN2;
		
		/**
		 * switchcase which chooses is based on the angle of the rocket1, 
		 * which determines which column and row are checked for colliding with the given tiles
		 */
		if ((rocket.getAngle() >= 0 && rocket.getAngle() < 45)
				|| (rocket.getAngle() >= 315 && rocket.getAngle() < 360)) {
			entTopRow = entTop / bp.getTileSize();
			tileN1 = bp.getTileM().getMap()[entLeftCol][entTopRow];
			tileN2 = bp.getTileM().getMap()[entRightCol][entTopRow];
			if (bp.getTileM().getTile()[tileN1].collision || bp.getTileM().getTile()[tileN2].collision) {
				rocket.setCollisionY(true);
			}
		}

		if (rocket.getAngle() >= 45 && rocket.getAngle() < 135) {
			entTopRow = (entTop - rocket.getSpeed()) / bp.getTileSize();
			tileN1 = bp.getTileM().getMap()[entLeftCol][entTopRow];
			tileN2 = bp.getTileM().getMap()[entRightCol][entTopRow];
			if (bp.getTileM().getTile()[tileN1].collision || bp.getTileM().getTile()[tileN2].collision) {
				rocket.setCollisionY(true);
			}
		}

		if (rocket.getAngle() >= 135 && rocket.getAngle() < 225) {
			entLeftCol = (entLeft - rocket.getSpeed()) / bp.getTileSize();
			tileN1 = bp.getTileM().getMap()[entLeftCol][entTopRow];
			tileN2 = bp.getTileM().getMap()[entLeftCol][entBotRow];
			if (bp.getTileM().getTile()[tileN1].collision || bp.getTileM().getTile()[tileN2].collision) {
				rocket.setCollisionY(true);
			}
		}

		if (rocket.getAngle() >= 225 && rocket.getAngle() < 315) {
			entLeftCol = (entLeft - rocket.getSpeed()) / bp.getTileSize();
			tileN1 = bp.getTileM().getMap()[entLeftCol][entTopRow];
			tileN2 = bp.getTileM().getMap()[entLeftCol][entBotRow];
			if (bp.getTileM().getTile()[tileN1].collision || bp.getTileM().getTile()[tileN2].collision) {
				rocket.setCollisionY(true);
			}
		}
	}
	/** checks for collision between rocket and enemy tank
	 *  also edits variables which allow for display of win screen and level change screen
	 * 
	 * @param e1	enemy tank which is checked for collision with rocket
	 * @param rk	rocket which is checked for collision with enemy tank
	 */
	public void checkRocketAndEnemyCollision(EnemyTank e1, rocket1 rk) {
		if(!rk.isEnemyFired() && rk.getHitbox().intersects(e1.getHitbox())) {
			e1.setDying(true);
			e1.setDead(true);
			bp.setDeadTanks(bp.getDeadTanks() +1);
			if(bp.getDeadTanks() == 3) {
				bp.setTitleNum(3);
			}
			else if(bp.getDeadTanks() == 6) {
				bp.setTitleNum(4);
			}
			else if(bp.getDeadTanks() == 10) {
				bp.setTitleNum(5);
			}
			rk.setCollisionY(true);
		}
	}
	
	/** checks for collision between rocket and player tank
	 * 
	 * @param p1 	player tank which is checked for collision with rocket
	 * @param rk	rocket which is checked for collision with player tank
	 * @return 		returns whether or not a rocket collides with a player
	 */
	public boolean checkRocketAndPlayerCollision(PlayerTank p1, rocket1 rk) {
		
		if(rk.getHitbox().intersects(p1.getHitbox())) {
			rk.setCollisionY(true);
			return true;

		}
		return false;
	}
	

	
	
}