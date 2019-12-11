//New elements only in leaf nodes
//Either 2,3, or 4 nodes - each have (n) amount of children
//and (n-1) amount of data. 
//Each internal node is full, each leaf node has no children
//"Pre-emptive split" prevents the occurance of a leaf being
//a 4-node so that we can always fit data.
//Current node is never a 4-node while traversing for insertion
//(including root)

//**START WITH ADDITION
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Tree234 {
	static Node root;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		root = null;
		Scanner input = new Scanner(System.in);
		for(int i = 0; i < 20; i ++) {
			System.out.print("What key would you like to insert?\n");
			int numEntered = input.nextInt();
			insert(numEntered, root);
			System.out.print("Check the structure:\n");
			displayTree(root, 4); //test
		}
		for(int i = 0; i < 9; i++) {
			System.out.print("Which key would you like to delete?\n");
			int numEntered = input.nextInt();
			deleteKeyTree(numEntered, root);
			System.out.print("Check the structure:\n");
			displayTree(root, 4);
		}
		//TimeUnit.SECONDS.sleep(10);
		//deleteKeyTree(3, root);
		//displayTree(root,4);
	}
	
	//Tree find
	public static Node findSuccessor(Node node) {
		if(!node.isLeaf()) {
			return findSuccessor(node.childArray[0]);
		}
		else
			return node;
	}
	
	//Tree insert
		//this is where pre-emptive split happens
	public static void insert(int key, Node node) {
		if (node == null) {
			root = new Node(key, null);
			return;
		}
		else {
			//System.out.print("(inserting at: ");
			//node.displayNode();
			//System.out.print(") ");
			//go top-down looking for splits
			if (node.isLeaf() == true && node.numItems != 3){
				//insert at the leaf (leaf TRUE, 2- or 3-node TRUE - therefore we good)
				//System.out.print("\nNode is leaf, we insert here\n");
				node.addElement(key);
			}
			else if (node.numItems == 3) {
				//needa split
				int pos = split(node);
				node = node.parent; //huh?
				if(key < node.dataArray[pos-1]) {
					insert(key, node.childArray[pos-1]);
				}
				else {
					insert(key, node.childArray[pos]);
				}
				//etc.
			}
			else {
				//continue
				//System.out.print("\nInternal node; continue down the tree\n");
				for(int i = 0; i < node.numItems; i++) {
					if(key < node.dataArray[i]) {
						//spot found
						insert(key, node.childArray[i]);
						return;
					}
				}
				//insert at far-right child b/c greater than all elements in current node
				insert(key, node.childArray[node.numItems]);
			}
		}
	}
	
	//Tree split (returns the parent of the node that is splitting)
	public static int split(Node node) {
		//System.out.print("\nSplitting at: ");
		//node.displayNode();
		//System.out.print("\n");
		//assume that node is a 4-node
		//because of the laws, it's parent will always be either 2- or 3- node because it would have split already
		int parentPos = 1;
		if (node.parent == null) {
			//node is root
			node.parent = new Node(node.dataArray[1], null);
			root = node.parent;
		}
		else {
			//node is not root
			//push the middle element up into the parent node
			parentPos = node.parent.addElement(node.dataArray[1]);
		}
		//create the two new 2-nodes as children
		for(int i = node.parent.numItems; i > -1; i--) {
			if(i == parentPos-1) {
				node.parent.childArray[i] = new Node(node.dataArray[0], node.parent);
				node.parent.childArray[i+1] = new Node(node.dataArray[2], node.parent);
				break;
			}
			else {
				node.parent.childArray[i] = node.parent.childArray[i-1];
			}
		}
		//rearrange the 4 orphans
		node.parent.childArray[parentPos-1].childArray[0] = node.childArray[0];
		node.parent.childArray[parentPos-1].childArray[1] = node.childArray[1];
		node.parent.childArray[parentPos].childArray[0] = node.childArray[2];
		node.parent.childArray[parentPos].childArray[1] = node.childArray[3];
		
		for(int i = parentPos-1; i < parentPos+1; i++) {
			for(int j = 0; j < 2; j++) {
				if(node.parent.childArray[i].childArray[j] != null)
					node.parent.childArray[i].childArray[j].parent = node.parent.childArray[i];
			}
		}
//		node.parent.childArray[parentPos-1].childArray[0].parent = node.parent.childArray[parentPos-1];
//		node.parent.childArray[parentPos-1].childArray[1].parent = node.parent.childArray[parentPos-1];
//		node.parent.childArray[parentPos].childArray[0].parent = node.parent.childArray[parentPos];
//		node.parent.childArray[parentPos].childArray[1].parent = node.parent.childArray[parentPos];
		//adjust the rest of the node's children
		//displayTree(root, 4);
		return parentPos;
	}
		
	//Tree display
	public static void displayTree(Node node, int count) {
		//indent, display the current node
		for(int i = 0; i < count; i++) {
			System.out.print("\t");
		}
		node.displayNode();
		System.out.print("\n");
		//TODO display the node's subtrees
		if(node.isLeaf())
			return;
		else
			//System.out.print("(current node's numItems is: " + Integer.toString(node.numItems));
			for(int i = 0; i < node.numItems + 1; i++) {
				displayTree(node.childArray[i], count-1);
			}
		System.out.print("\n");
	}

	//Tree delete (kms)
	public static void deleteKeyTree(int key, Node node) {
		//search for the key
		
		//node along the way that is 2-node
		if(node.numItems == 1 && root != node) {
			//rotate (if node has a 3- or 4-node sibling)
			if (node.leftSibling() != null && node.leftSibling().numItems >= 2) 
				deleteKeyTree(key, rotateLeft(node));
			else if(node.rightSibling() != null && node.rightSibling().numItems >= 2)
				deleteKeyTree(key, rotateRight(node));
			else if(node.parent.numItems >= 2){
				//need to merge
				if(node.leftSibling() == null) {
					//has right (full property of 2-3-4 tree)
					deleteKeyTree(key, merge(node, node.rightSibling()));
				}
				else {
					//has left (and right possibly)
					deleteKeyTree(key, merge(node.leftSibling(), node));
				}
			}
			else {
				//shrink tree (not writing a function bc its the only time it will be invoked)
				//assume node.parent is root
				root.addElement(root.childArray[0].dataArray[0]);
				root.addElement(root.childArray[1].dataArray[0]);
				root.childArray[0] = root.childArray[0].childArray[0];
				root.childArray[0].parent = root;
				root.childArray[1] = root.childArray[0].childArray[1];
				root.childArray[1].parent = root;
				root.childArray[2] = root.childArray[1].childArray[0];
				root.childArray[2].parent = root;
				root.childArray[3] = root.childArray[1].childArray[1];
				root.childArray[3].parent = root;
			}
		}
		else if(node.isLeaf()) {
			//delete from here
			if(!node.deleteKey(key))
				System.out.print("\nUH-OH! Node to delete not found: " + Integer.toString(key) + "\n");
			return;
		}
		else {
			if(node.findKey(key) == -1) {
				//not in node, move on
				for (int i = 0; i < node.numItems; i++) {
					if(key < node.dataArray[i]) {
						deleteKeyTree(key, node.childArray[i]);
						return;
					}
				}
				//far right
				deleteKeyTree(key, node.childArray[node.numItems]);
				return;
			}
			else {
				//key is in this node! need to find successor
				int nodePos = node.findKey(key);
				int temp = node.dataArray[nodePos-1];
				Node succ = findSuccessor(node.childArray[nodePos]);
				node.dataArray[nodePos-1] = succ.dataArray[0];
				succ.dataArray[0] = temp;
				//switch complete, continue search
				deleteKeyTree(key, node.childArray[nodePos]);
			}
		}
	}

	//Tree rotateright
	public static Node rotateRight(Node node) {
		Node sibling = node.parent.childArray[node.childNum()];			//define sibling
		node.addElement(node.parent.dataArray[node.childNum()-1]);  	//add the element to the current node (step1 rotation)
		node.parent.dataArray[node.childNum()-1] = sibling.dataArray[0];//push the element up (step2 rotation)
		node.childArray[node.numItems] = sibling.childArray[0];			//reconnect orphan
		if(node.childArray[node.numItems] != null)
			node.childArray[node.numItems].parent = node;		//orphan needs new parent pointer
		for(int i = 0; i < sibling.numItems-1; i++) {		//shift the sibling's data array
			sibling.dataArray[i] = sibling.dataArray[i+1];
		}
		sibling.dataArray[sibling.numItems-1] = 0;			//eliminate an element (step3 rotation)
		for(int i = 0; i < sibling.numItems; i++) {			//shift the sibling's child array
			sibling.childArray[i] = sibling.childArray[i+1];
		}
		sibling.childArray[sibling.numItems] = null;		//eliminate one of the children
		sibling.numItems--;									//finally, correct the number of items of the sibling 
		return node;
	}
	
	//Tree rotateLeft
	public static Node rotateLeft(Node node) {
		Node sibling = node.parent.childArray[node.childNum()-2];		//define sibling
		node.addElement(node.parent.dataArray[node.childNum()-2]);  	//add the element to the current node (step1 rotation)
		node.parent.dataArray[node.childNum()-2] = sibling.dataArray[sibling.numItems-1];//push the element up (step2 rotation)
		for(int i = node.numItems; i > 0; i--) {
			node.childArray[i] = node.childArray[i-1];		//shift node's child array
		}
		node.childArray[0] = sibling.childArray[sibling.dataArray[sibling.numItems]];			//reconnect orphan
		if(node.childArray[0] != null)
			node.childArray[0].parent = node;				//orphan needs new parent pointer
		sibling.dataArray[sibling.numItems-1] = 0;			//eliminate an element (step3 rotation)
		sibling.childArray[sibling.numItems] = null;		//eliminate one of the children
		sibling.numItems--;									//finally, correct the number of items of the sibling 
		return node;
	}

	//Tree merge
	public static Node merge(Node lnode, Node rnode) {
		//ASSUME that lnode and rnode are adjacent and share a parent, also both 2-nodes
		//ASSUME that parent is 3- or 4-node b/c it's not root
		lnode.addElement(lnode.parent.dataArray[lnode.childNum()-1]);
		lnode.addElement(rnode.dataArray[0]);
		lnode.childArray[2] = rnode.childArray[0];
		lnode.childArray[3] = rnode.childArray[1];
		if(lnode.childArray[2] != null)
			lnode.childArray[2].parent = lnode;
		if(lnode.childArray[3] != null)
			lnode.childArray[3].parent = lnode;
		for(int i = lnode.childNum()-1; i < lnode.parent.numItems-1; i++) {
			lnode.parent.dataArray[i] = lnode.parent.dataArray[i+1];
		}
		lnode.parent.dataArray[lnode.parent.numItems-1] = 0;
		for(int i = rnode.childNum()-1; i < rnode.parent.numItems; i++) {
			rnode.parent.childArray[i] = rnode.parent.childArray[i+1];
		}
		lnode.parent.childArray[lnode.parent.numItems] = null;
		lnode.parent.numItems--;
		return lnode;
		//"rnode" is the only pointer to that object, so when the method is completed
		//garbage collector removes it (forever :o)
	}

}


