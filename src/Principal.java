import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.naming.directory.SearchControls;

/**
 * 
 * @author Nathan Rodrigo
 *
 * Main class where the archives are read
 */
public class Principal {
	
	private static int array[];
	private static RedBlackTree rbt;
	
	/**
	 * This method prints at the prompt the node's informations
	 */
	public static void searchNode() {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			System.out.println("\nType the key that you wanna find: ");
			try {
				String entrada = in.readLine();
				int key = Integer.parseInt(entrada);
				
				// Binary searching
				Node node = rbt.getNode(key);
				
				if(node == null)
					System.out.println("Non-existent node.");
				else {
					System.out.println("\nNode key: " + node.getKey());
					System.out.println("Node color: " + node.getColor());
					
					if(node.getDad() != null) { // Print the dad's informations
						System.out.println("\nDad's key: " + node.getDad().getKey());
						System.out.println("Dad's color: " + node.getDad().getColor());
					}
					
					if(node.getLeftSon() != null) { // Print the left son's informations
						System.out.println("\nLeft son's key: " + node.getLeftSon().getKey());
						System.out.println("Left son's color: " + node.getLeftSon().getColor());
					}else {
						System.out.println("\nThis node hasn't a left son");
					}
					
					if(node.getRightSon() != null) { // Print the right son's informations
						System.out.println("\nLeft son's key: " + node.getRightSon().getKey());
						System.out.println("Left son's color: " + node.getRightSon().getColor());
					}else {
						System.out.println("\nThis node hasn't a right son");
					}
					
				}	
				
			} catch (IOException e) {
				System.out.println("Invalid input.");
				break;
			} catch ( NumberFormatException e) {
				System.out.println("You didn't enter a int number.");
			}
			
			
			
		}
		
	}
	
	/**
	 * The main method that read the file's content and put it in an int array
	 * The file should have, on the first line, the size of the array
	 * 
	 * @param args contains the name of the file that will be read
	 */
	public static void main(String[] args) {
		
		String filename = args[0];
		
		try {
			String directory = Principal.class.getResource(filename).toString();
			
			if(directory != null)
				directory = directory.replace("file:/", "");
			else {
				System.out.println("Unknown file");
				System.exit(-1);
			}
				
			
			System.out.println("File directory: \n" + directory + "\n");
			
			int size = 0; // tamanho do array
			try {
				BufferedReader br = new BufferedReader(new FileReader(directory));
				
				//Interpreta a primeira linha do arquivo como o tamanho do array
				if(br.ready())
					size = Integer.parseInt(br.readLine().toString());
				array = new int[size];
				
				int index = 0;
				
				while(br.ready()) {
			        String linha = br.readLine();
			        int i = Integer.parseInt(linha);
			        array[index++] = i;
			     }
			     br.close();
			     
			} catch (IOException e) {
				System.out.println("Enter a valid directory and file!");
				e.printStackTrace();
			}catch(ArrayIndexOutOfBoundsException o) {
				System.out.println("The first line of the archive should contains the number of elements.");
				System.exit(-1);
			}
		}catch (NullPointerException e) {
			System.out.println("Invalid file or repository.");
			return;
		}
		
		
		rbt = new RedBlackTree(array[0]); // The first element in the array gonna be the first root
		
		for(int i = 1; i < array.length; i++) { //Creation of the nodes
			rbt.insertNode(array[i]);
		}
		
		searchNode();
		
	}

}
