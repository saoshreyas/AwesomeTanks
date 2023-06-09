package main;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import entity.PlayerTank;
/**creates all the conditions in which keys are pressed and the boolean variables associated 
 * 
 * with those conditions
 * @author Alec Zhu
 * @author Shreyas Sao
 */
public class KeyHandler implements KeyListener{
	
	/**
	 * fields which represent when each key (w,s,a,d, and space) are pressed(true or false)
	 */
	private boolean wPressed;
	private boolean aPressed;
	private boolean sPressed;
	private boolean dPressed;
	private boolean spacePressed;
	
	
	@Override
	/**
	 * is not utilized in the game
	 */
	public void keyTyped(KeyEvent e) {
	}
	
	/**
	 * sets the corresponding variable to true if the corresponding letter is pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			setwPressed(true);
			
		}
		if(code == KeyEvent.VK_A) {
			setaPressed(true);
		}
		if(code == KeyEvent.VK_S) {
			setsPressed(true);
		}
		if(code == KeyEvent.VK_D) {
			setdPressed(true);
		}
		if(code == KeyEvent.VK_P) {
			setwPressed(true);
		}
		if(code == KeyEvent.VK_SPACE) {
			setSpacePressed(true);
		}
		
		
		
	}
	
	/**
	 * sets the corresponding variable to false if the corresponding letter is released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			setwPressed(false);
			
		}
		if(code == KeyEvent.VK_A) {
			setaPressed(false);
		}
		if(code == KeyEvent.VK_S) {
			setsPressed(false);
		}
		if(code == KeyEvent.VK_D) {
			setdPressed(false);
		}
		if(code == KeyEvent.VK_SPACE) {
			setSpacePressed(false);
		}
	}

	public boolean iswPressed() {
		return wPressed;
	}

	public void setwPressed(boolean wPressed) {
		this.wPressed = wPressed;
	}

	public boolean isaPressed() {
		return aPressed;
	}

	public void setaPressed(boolean aPressed) {
		this.aPressed = aPressed;
	}

	public boolean issPressed() {
		return sPressed;
	}

	public void setsPressed(boolean sPressed) {
		this.sPressed = sPressed;
	}

	public boolean isdPressed() {
		return dPressed;
	}

	public void setdPressed(boolean dPressed) {
		this.dPressed = dPressed;
	}

	public boolean isSpacePressed() {
		return spacePressed;
	}

	public void setSpacePressed(boolean spacePressed) {
		this.spacePressed = spacePressed;
	}

	
	
}