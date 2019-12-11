import java.io.File;
import java.util.Scanner;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.Random;

public class TreeProperties {

	public static void main(String[] args) throws FileNotFoundException{
		// TODO Auto-generated method stub
		//Set up file read mechanism
				File data = new File("datpart2.txt");
				Scanner input = new Scanner(data);
				
				//Set up empty tree and current node
				AVLNode root = null;
				
				//read file and insert
				while(input.hasNext() ) {
						
					//if tree is empty
					String tempK = input.next().replaceAll("[\\D]", "");
					//FIND
					if(root == null ) {
						Book tempB = new Book(input.next(), input.next());
						root = new AVLNode(tempK, tempB, 0, null, null, null);
					}

					//if tree already has element(s)
					else {
							
						
						AVLNode.displayTree(root);
						System.out.print("\n");
					}
					
					


				}
				
				input.close();
	}
	
	static AVLNode place(AVLNode node) {
		Random rand = new Random();
		int num = rand.nextInt(10);
		if(num > 4) {
			if(node.right != null)
				return node.right;
			else if(node.left != null)
				return node.left;
			else
				return place(node.right);
		}
		else {
			if(node.left != null)
				return node.left;
			else if(node.right != null)
				return node.right;
			else 
				return place(node.left);
		}
	}

}
