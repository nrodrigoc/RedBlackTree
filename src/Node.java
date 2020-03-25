
public class Node {
	
	
	public static final char RED = 'R';
	public static final char BLACK = 'B';
	
	private int key;
	private char color;
	private Node leftSon;
	private Node rightSon;
	private Node dad;
	
	public Node(int key) {
		this.key = key;
		leftSon = null;
		rightSon = null;
		dad = null;
		color = RED;
	}
	
	/**
	 * This method is responsible to swap the color of the this node
	 */
	public void swapColor() {
		color = color == RED ? BLACK : RED;
	}
	
	/**
	 * @param dad will be the new dad of this node
	 * At the same time, this node will be placed like the leftSon, if it's less than dad, otherwise, we'll set this like rightSon
	 */
	public void setDad(Node dad) {
		this.dad = dad;
		
		if(dad != null) {
			if(key < dad.getKey())
				dad.setLeftSon(this);
			else if(key > dad.getKey())
				dad.setRightSon(this);
		}
	}
	
	
	public Node getDad() {
		return dad;
	}
	
	public Node getGrandpa() {
		return dad.getDad();
	}
	
	/**
	 * @return the uncle of this node, no matter it's at the left or right of this node's dad
	 */
	public Node getUncle() {
		if(getGrandpa() == null)
			return null;
		
		if(dad.getKey() > getGrandpa().getKey()) {
			return getGrandpa().getLeftSon();
		}
		
		return getGrandpa().getRightSon();
	}
	
	public int getKey() {
		return key;
	}

	public Node getLeftSon() {
		return leftSon;
	}

	public void setLeftSon(Node leftSon) {
		this.leftSon = leftSon;
	}
	
	public Node getRightSon() {
		return rightSon;
	}


	public void setRightSon(Node rightSon) {
		this.rightSon = rightSon;
	}
	
	public char getColor() {
		return color;
	}

	
	
}
