
/**
 * 
 * @author Nathan Rodrigo
 *
 */
import java.util.ArrayList;

public class RedBlackTree {
	
	ArrayList<Node> nodes;
	Node root;
	
	public RedBlackTree(int rootKey) {
		root = new Node(rootKey);
		root.swapColor();
		nodes = new ArrayList<>();
	}
	
	/**
	 * Insert a new node in the tree
	 * @param key of the new node
	 */
	public void insertNode(int key) {
		
		Node insertedNode = new Node(key);
		nodes.add(insertedNode);
		
		Node noAtual = root;
		
		while(true) {
			
			if(key > noAtual.getKey()) {
				
				if(noAtual.getRightSon() != null) {
					noAtual = noAtual.getRightSon();
					continue;
				}else {
					insertedNode.setDad(noAtual);
					break;
				}
				
			}else if(key < noAtual.getKey()){
				
				if(noAtual.getLeftSon() != null) {
					noAtual = noAtual.getLeftSon();
					continue;
				}else {
					insertedNode.setDad(noAtual);
					break;
				}		
				
			}
			
		}
		
		verifyColor(insertedNode);
		
	}
	
	/**
	 * Secondary method used to reinsert a node without needing to start from the root
	 * @param insertedNode  node that will be inserted
	 * @param subtreeRoot is the starting point of the insertion
	 */
	public void insertNode(Node insertedNode, Node subtreeRoot) {
		
		int key = insertedNode.getKey();
		
		Node noAtual = subtreeRoot;
		
		while(true) {

			if(key > noAtual.getKey()) {
				
				if(noAtual.getRightSon() != null) {
					noAtual = noAtual.getRightSon();
					continue;
				}else {
					insertedNode.setDad(noAtual);
					break;
				}
				
			}else if(key < noAtual.getKey()){
				
				if(noAtual.getLeftSon() != null) {
					noAtual = noAtual.getLeftSon();
					continue;
				}else {
					insertedNode.setDad(noAtual);
					break;
				}		
				
			}
			
		}
		
	}
	
	/**
	 * Search method who walks through the tree to return the searched node
	 * @param key is the key of the node
	 * @return the node that contains the same key
	 */
	public Node getNode(int key) {
		
		Node noAtual = root;
		
		while(true) {
			
			int keyAtual = noAtual.getKey();
			
			if(key == keyAtual) {
				return noAtual;
				
			}else if(key > keyAtual) {
				
				if(noAtual.getRightSon() != null) {
					noAtual = noAtual.getRightSon();
					continue;
				}else {
					return null;
				}
				
			}else if(key < noAtual.getKey()){
				
				if(noAtual.getLeftSon() != null) {
					noAtual = noAtual.getLeftSon();
					continue;
				}else {
					return null;
				}		
				
			}

		}
		
	}
	
	/**
	 * This method is responsible for verifying the color of the dad and the uncle of the node
	 * when dad and uncle are red, the method "recolor"
	 * 1 - dad > node and dad > grandfather
	 * 2 - dad < node and dad < grandfather
	 * @param node
	 */
	private void verifyColor(Node node) {
		
		Node dad = node.getDad();
		
		if(dad.getColor() == Node.RED && node.getColor() == Node.RED) {
			Node uncle = node.getUncle();
			
			if(uncle == null || uncle.getColor() == Node.BLACK) {

				Node grandpa = node.getGrandpa();
				
				if(node.equals(dad.getLeftSon()) && dad.equals(grandpa.getLeftSon())) { //Rotacao a direita
					//Pai > filho e pai < avô
					simpleRotation(node);
				}else if(node.equals(dad.getRightSon()) && dad.equals(grandpa.getRightSon())) { //Rotação à esquerda
					//Pai < filho e pai > avô
					simpleRotation(node);
				}else{
					//Pai > filho e pai > avô ou Pai < filho e pai < avô
					secondRotation(node);
				}
				
			}else if(uncle.getColor() == Node.RED) {
				recolor(node);
			}
			
		}

	}
	
	private void recolor(Node node) {
		
		Node dad = node.getDad();
		Node uncle = node.getUncle();
		Node grandpa = node.getGrandpa();
		
		dad.swapColor();
		uncle.swapColor();
		
		if(!grandpa.equals(root)) {
			grandpa.swapColor();
			verifyColor(grandpa);
		}
	}
	
	
	/**
	 * This method is responsible for rotating the subtree when:
	 * 1 - dad > node and dad < grandfather
	 * 2 - dad < node and dad > grandfather
	 * @param node
	 */
	private void simpleRotation(Node node) {
		
		Node dad = node.getDad(); //pai
		Node grandpa = node.getGrandpa(); //avo
		Node grandpaDad = grandpa.getDad(); //bisavo
		Node brother = dad.getRightSon(); //irmao
		
		if(node.getKey() < dad.getKey() && dad.getKey() < grandpa.getKey()) {	//Rotação pra direita
			
			dad.setDad(grandpaDad);
			grandpa.setDad(dad);
			
			grandpa.setLeftSon(null);
			
		}else if(node.getKey() > dad.getKey() && dad.getKey() > grandpa.getKey()){ //Rotação pra esquerda
			brother = dad.getLeftSon();
			
			dad.setDad(grandpaDad);
			grandpa.setDad(dad);
			
			grandpa.setRightSon(null);
			
		}
		
		dad.swapColor();
		grandpa.swapColor();
		
		if(dad.getDad() == null) { // Coloca o nó do meio na variável da raíz, caso ele esteja no topo da árvore
			//System.out.println("Nova raiz!\n");
			root = dad;
			if(dad.getColor() == Node.RED) {
				dad.swapColor();
			}
		}
		
		if(brother != null) { //se o nó tinha irmao, esse irmao vai ser reinserido na arvore
			insertNode(brother, grandpa);
		}
	}
	
	
	/**
	 * This method is responsible for rotating the subtree when:
	 * 1 - dad > node and dad > grandfather
	 * 2 - dad < node and dad < grandfather
	 * 
	 * @param node
	 */
	private void secondRotation(Node node) {
		
		
		Node dad = node.getDad(); //pai
		Node grandpa = node.getGrandpa(); //avo
		Node grandpaDad = grandpa.getDad(); //bisavo
		Node leftSon = node.getLeftSon(); //filho da esquerda
		Node rightSon = node.getRightSon(); //filho da direita
			
		node.setDad(grandpaDad);
		dad.setDad(node);
		grandpa.setDad(node);
		
		if(node.equals(dad.getRightSon())) {	
			//Pai < filho e pai < avô 
			dad.setRightSon(null);
			grandpa.setLeftSon(null);
			
		}else if(node.equals(dad.getLeftSon())){ //Rotação pra esquerda
			//Pai > filho e pai > avô 
			dad.setLeftSon(null);
			grandpa.setRightSon(null);
			
		}
		
		node.swapColor();
		grandpa.swapColor();
		
		if(node.getDad() == null) { // Coloca o nó do meio na variável da raíz, caso ele esteja no topo da árvore
			//System.out.println("Nova raiz!\n");
			root = node;
			if(node.getColor() == Node.RED) {
				node.swapColor();
			}
		}
		
		if(leftSon != null) { //se o nó tinha filho à esquerda, esse irmao vai ser reinserido na arvore partindo do antigo pai
			insertNode(leftSon, node);
		}
		
		if(rightSon != null) { //se o nó tinha filho à direita, esse irmao vai ser reinserido na arvore partindo do antigo pai
			insertNode(rightSon, node);
		}
		
	}
	
	/**
	 * 
	 * @return the tree's root
	 */
	public Node getRoot() {
		return root;
	}
	
}
