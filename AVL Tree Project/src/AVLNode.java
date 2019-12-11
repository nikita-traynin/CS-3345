import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.*;

public class AVLNode {

	String key; //ISBN number
	Book value; //
	int height;
	AVLNode left;
	AVLNode right;
	AVLNode parent;
	
	AVLNode(String key, Book value, int height, AVLNode left, AVLNode right, AVLNode parent) {
		this.key = key;
		this.value = value;
		this.height = height;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}

	public static void main(String[] args) throws FileNotFoundException {
		//Set up file read mechanism
		File data = new File("dat.txt");
		Scanner input = new Scanner(data);
		
		//Set up empty tree and current node
		AVLNode root = null;
		
		//read file and insert
		while(input.hasNext() ) {
				
			//if tree is empty
			String tempK = input.next().replaceAll("[\\D]", "");
			//FIND
			AVLNode parent = find(root, tempK);	
			if(root == null ) {
				Book tempB = new Book(input.next(), input.next());
				root = new AVLNode(tempK, tempB, 0, null, null, null);
			}

			//if tree already has element(s)
			else {
					
				//BST insert
				System.out.print("Inserting " + tempK + ", With parent: " + parent.key + "\n");
				if(Integer.parseInt(parent.key) <= Integer.parseInt(tempK)) {
					Book tempB = new Book(input.next(), input.next());
					parent.right = new AVLNode(tempK, tempB, 0, null, null, parent);
					correct(parent.right);		//correct HEIGHT
				
				}
				else if(Integer.parseInt(parent.key) > Integer.parseInt(tempK)){
					Book tempB = new Book(input.next(), input.next());
					parent.left = new AVLNode(tempK, tempB, 0, null, null, parent);
					correct(parent.left);		//correct HEIGHT
				}
				else {
					System.out.print("BIG DOODOO\n");
				}
				System.out.print("preBalance: \n");
				displayTree(root);
				System.out.print("\n");
				//identify and correct imbalance
				balanceTree(parent);
				root = findRoot(parent);
				System.out.print("postBalance: \n");
				displayTree(root);
				System.out.print("\n");
			}
			
			
			
			
			
			//displayTree(root);

		}
		displayTree(root);
		input.close();
	}
	
	static AVLNode find(AVLNode current, String key) {
		int num = Integer.parseInt(key);
		if(current == null)
			return null;
		else if(Integer.parseInt(current.key) <= num){
			if(current.right == null) 
				return current;
			else
				return find(current.right, key);
		}
		else if(Integer.parseInt(current.key) > num) {
			if(current.left == null)
				return current;
			else
				return find(current.left, key);
		}
		else {
			System.out.print("BIG DOO\n");
			return current;
		}
	}
	
	static int height(AVLNode current) {
		if(current == null) 
			return -1;
		else
			return current.height;
	}

	static void displayTree(AVLNode root) {
		System.out.print("Height: " + root.height + ", ISBN: " + root.key + ", Title: " + root.value.title + ", Author: " + root.value.lastName + "\n");
		if(root.left != null)
			displayTree(root.left);
		if(root.right != null)
			displayTree(root.right);
	}
	
	static int balanceFactor(AVLNode node) {
		return height(node.left) - height(node.right);
	}
	static void correct(AVLNode node) {
		if(node == null)
			return;
		node.height = 1 + java.lang.Math.max(height(node.left), height(node.right));
		correct(node.parent);
	}
	static void balanceTree(AVLNode node) {
		if(node == null)
			return;
		else if(balanceFactor(node) > 1) {
			if(balanceFactor(node.left) == 1) {
				left(node); //left-left
			}
			else if(balanceFactor(node.left) == -1) {
				leftRight(node);
			}
		}
		else if(balanceFactor(node) < -1) {
			if(balanceFactor(node.right) == 1) {
				
			}
		}
		else
			balanceTree(node.parent);
	}
	static AVLNode findRoot(AVLNode node) {
		if(node == null)
			return null;
		else if(node.parent == null)
			return node;
		else
			return findRoot(node.parent);
	}
	static void left(AVLNode node) {
		System.out.print(", fixed in left-left rotation.\n");
		//change middle node's parent and right child, also change the top node's parent's child to hook up to middle
		//change top node's left child to null and change its parent
		node.left.parent = node.parent;
		node.parent = node.left;
		node.left = node.parent.right;
		node.parent.right = node;
		if(node.parent.parent != null) {
			if(node.parent.parent.left == node)
				node.parent.parent.left = node.parent;
			else if(node.parent.parent.right == node)
				node.parent.parent.right = node.parent;
		}
		if(node.left != null) {
			node.left.parent = node;
			correct(node.left);
			return;
		}
		correct(node);
	}
	static void right(AVLNode node) {
		System.out.print(", fixed in right-right rotation.\n");
		//change middle node's parent and right child, also change the top node's parent's child to hook up to middle
		//change top node's left child to null and change its parent
		node.right.parent = node.parent;
		node.parent = node.right;
		node.right = node.parent.left;
		node.parent.left = node;
		if(node.parent.parent != null) {
			if(node.parent.parent.left == node)
				node.parent.parent.left = node.parent;
			else if(node.parent.parent.right == node)
				node.parent.parent.right = node.parent;
		}
		if(node.left != null) {
			node.left.parent = node;
			correct(node.left);
			return;
		}
		correct(node);
	}
	static void leftRight(AVLNode node) {
		System.out.print(", fixed in left-right rotation.\n");
	}
	static void rightLeft(AVLNode node) {
		System.out.print(", fixed in right-left rotation.\n");
	}
}
