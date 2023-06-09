
package Pathfinding;

import main.BackgroundPanel;
import java.util.ArrayList;

import entity.Entity;
/**
 * an A* algorithm that helps the EnemyTank track the player
 * 
 * @author Ashmit Baghele
 * @author Alec Zhu
 *
 */

public class Pathfinder {
	
	/**
	 * Background used for pathfinding
	 */
	private BackgroundPanel bp;
	
	/**
	 * A 2D array of nodes that mimic the map
	 */
	private Node[][] node;
	
	/**
	 * An ArrayList of Nodes that constitute all of the nodes in the path
	 */
	private Node[][] p;
	
	/**
	 * An ArrayList of Nodes for all of the nodes that are opened
	 */
	private ArrayList<Node> openList = new ArrayList<>();
	
	/**
	 * An ArrayList of Nodes that constitute all of the nodes in the path
	 */
	private ArrayList<Node> pathList = new ArrayList<>();
	
	/**
	 * fields for the starting node, the ending node, and the current node
	 */
	private Node startNode, goalNode, currentNode;
	
	/**
	 * boolean representing if the goal has been reached or not
	 */
	private boolean goalReached = false;
	
	/**
	 * variable that keeps the number of steps
	 */
	private int step = 0;

	/**
	 * Makes a Pathfinder object and intitalizes p, the 2D array of Nodes
	 * @param bp
	 */
	public Pathfinder(BackgroundPanel bp) {
		this.bp = bp;
		instantiateNodes();
	}
	
	/**
	 * instantiates all nodes in the p array with their respective row and column
	 */
	public void instantiateNodes() {
		p = new Node[bp.getMaxScreenRow()][bp.getMaxScreenCol()];

		int pcol = 0;
		int prow = 0;

		/**
		 * iterate through every index in p
		 */
		while(prow < bp.getMaxScreenRow() && pcol < bp.getMaxScreenCol()) {
			p[prow][pcol] = new Node(prow, pcol);
			pcol++;
			if(pcol == bp.getMaxScreenCol()) {
				pcol = 0;
				prow++;
			}
		}
		
		
		
	}
	
	/**
	 * resets the 2D Node array p by setting open, checked, and solid fields of all nodes to false
	 * clears all ArrayLists
	 * resets fields like goalReached and step
	 */
	public void resetNodes() {

		int pcol = 0;
		int prow = 0;
		
		/**
		 * iterate through every index in p
		 */
		while (prow < bp.getMaxScreenRow() && pcol < bp.getMaxScreenCol()) {
			p[prow][pcol].setOpen(false);
			p[prow][pcol].setChecked(false);
			p[prow][pcol].setSolid(false);

			pcol++;
			if (pcol == bp.getMaxScreenCol()) {
				pcol = 0;
				prow++;
			}
		}
		
		
		openList.clear();
		getPathList().clear();
		goalReached = false;
		step = 0;
	}
	
	/**
	 * intializes the starting, current, & goal node
	 * sets solid to true for all nodes that have collision
	 * intitializes the costs of all nodes
	 * @param startCol
	 * @param startRow
	 * @param goalCol
	 * @param goalRow
	 * @param e1
	 */
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity e1) {
		resetNodes();
		
		startNode = p[startRow][startCol];
		
		currentNode = startNode;
		
		goalNode = p[goalRow][goalCol];
		
		openList.add(currentNode);
	
		/**
		 * iterates through every index in p but uses numbers
		 * sets solid for all nodes that have collision set to true, meaning entities cannot walk through
		 * initializes the cost of all nodes
		 */
		for(int i = 0; i< 16; i++) {
			for(int j = 0; j< 32; j++) {
				if(bp.getTileM().getTile()[bp.getTileM().getMap()[j][i]].collision) {
					p[i][j].setSolid(true);
				}
				
				getCost(p[i][j]);
			}
		}
		
	}
	
	/**
	 * calculates and sets the gCost, hCost, and fCost
	 * gCost is Manhattan distance from current node to start node
	 * hCost is Manhattan distance from current node to goal node
	 * fCost is combination of the two
	 * @param node
	 */
	private void getCost(Node node) {


		int xDistance = Math.abs(node.getCol() - startNode.getCol());
		int yDistance = Math.abs(node.getRow() - startNode.getRow());
		node.setgCost(xDistance + yDistance);



		xDistance = Math.abs(node.getCol() - goalNode.getCol());
		yDistance = Math.abs(node.getRow() - goalNode.getRow());
		node.sethCost(xDistance + yDistance);

		node.setfCost(node.getgCost() + node.gethCost());
	}
	
	/**
	 * performs the A* search algorithm to find the path from the startNode to the goalNode. 
	 * continues the search as long as the goal has not been reached.
	 * examines the neighboring nodes (up, left, down, right) and calls openNode() to process each neighbor. 
	 * It selects the best node based on its fCost and gCost & sets it as the new currentNode	 
	 * If the goalNode is reached, it sets goalReached to true and calls trackThePath() to determine the path.
	 * @return
	 */
	public boolean search() {
		
		/**
		 * keeps on searching as long as the goal has not been reached
		 */
		while(!goalReached && step <500) {
			
			int col = currentNode.getRow();
			int row = currentNode.getCol();
	
			
			currentNode.setChecked(true);
			openList.remove(currentNode);
			
			
			
			
			/** open the up node*/
			if (row - 1 >= 0) {
			    openNode(p[col][row - 1]);
			}

			/** open the left node*/
			if (col - 1 >= 0) {
			    openNode(p[col - 1][row]);
			}

			/** open the down node*/
			if (row + 1 < bp.getMaxScreenCol()) {
			    openNode(p[col][row + 1]);
			}

			/** open the right node*/
			if (col + 1 < bp.getMaxScreenRow()) {
			    openNode(p[col + 1][row]);
			}
			

			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			/**
			 * finds the best node for the shortest distance based off of cost
			 */
			for (int i = 0; i < openList.size(); i++) {
				if (openList.get(i).getfCost() < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).getfCost();
				} else if (openList.get(i).getfCost() == bestNodefCost) {
					if (openList.get(i).getgCost() < openList.get(bestNodeIndex).getgCost()) {
						bestNodeIndex = i;
					}
				}
			}

			if (openList.size() == 0) {
				break;
			}

			currentNode = openList.get(bestNodeIndex);
			if (currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
			step++;
		}	
		return goalReached;
	}
	
	/**
	 * Traces the path from the goalNode back to the startNode
	 * ends up with pathList representing all of the steps
	 */
	private void trackThePath() {
		Node current = goalNode;
		
		/**
		 * continues until the startNode is reached again
		 */
		while(current != startNode) {
			getPathList().add(0, current);
			current = current.getParent();
			
		}

	}
	
	/**
	 * Opens a node under certain conditions, sets parent node and add its to openList
	 * @param node
	 */
	private void openNode(Node node) {
		
		if (!node.isOpen() && !node.isChecked() && !node.isSolid()) {
			node.setOpen(true);
			node.setParent(currentNode);
			openList.add(node);

		}
	}

	public ArrayList<Node> getPathList() {
		return pathList;
	}

	public void setPathList(ArrayList<Node> pathList) {
		this.pathList = pathList;
	}
}