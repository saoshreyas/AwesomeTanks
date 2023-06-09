
package main;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**main class running the game panel and the game
 * @author Ashmit Baghele
 * @author Alec Zhu
 * @author Cynthia Wu
 * @author Shreyas Sao
 */

public class Main {
	/**main method
	 * 
	 * @param args	sequence passed to the main function
	 */
	public static void main(String[] args) {
		/**
		 * new Background(); 
		 */
		JFrame frame = new JFrame();

		/**
		 * background characteristics
		 */
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Awesome Tanks");

		/**
		 * new background panel
		 */
		BackgroundPanel background = new BackgroundPanel();
		frame.add(background);
		frame.pack();
		
		/**
		 * adds a KeyListener to BackgroundPanel
		 */
		background.addKeyListenerToPanel();

		/**
		 * sets background visibility and location
		 */
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		/**
		 * executes game thread in background panel
		 */
		background.startGameThread();

	}

}