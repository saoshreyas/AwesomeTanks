package Pathfinding;
/**
 * Creates the fields needed for Pathfinding
 * @author Ashmit Baghele
 * @author Shreyas Sao
 */
public class Node {
	/**
	 * Node object  used
	 */
	private Node parent;
	
	/**
	 * represent column and row of Node
	 */
	private int col, row;
	
	/**
	 * three possible node options
	 */
	private int gCost, hCost, fCost;
	
	/**
	 * represent possible states of nodes
	 */
	private boolean solid, open, checked;

	public Node(int row, int col) {
		this.setCol(col);
		this.setRow(row);

	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getgCost() {
		return gCost;
	}

	public void setgCost(int gCost) {
		this.gCost = gCost;
	}

	public int gethCost() {
		return hCost;
	}

	public void sethCost(int hCost) {
		this.hCost = hCost;
	}

	public int getfCost() {
		return fCost;
	}

	public void setfCost(int fCost) {
		this.fCost = fCost;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}