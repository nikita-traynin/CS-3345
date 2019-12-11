public class Node {
	//Node data is simply numbers (yay)
	static final int order = 4;
	int numItems;
	int dataArray[];
	Node childArray[];
	Node parent;
	
	Node(int key, Node parent) {
		this.numItems = 1;
		this.parent = parent;
		this.childArray = new Node[order];
		this.dataArray = new int[order-1];
		this.dataArray[0] = key;

	}
	
	public int addElement(int key) {
		//System.out.print("\nAdd Element " + Integer.toString(key) + " to node ");
		//this.displayNode();
		//System.out.print("\n");
		//assuming either 2- or 3- node LEAF
		int i;
		for (i = 0; i < this.numItems; i++) {
			if(key < this.dataArray[i]) {
				//found spot to insert at
				this.numItems++;
				//shift the array
				for (int j = numItems-1; j > i-1; j--) {
					if(j == i) {
						//insert here
						this.dataArray[j] = key; 
						return j+1;
					}
					else {
						////System.out.print("\nThis is j: \n" + j);
						this.dataArray[j] = this.dataArray[j-1];
					}
				}
			}
		}
		//greater than all other data elements, simply add to the end
		this.numItems++;
		this.dataArray[numItems-1] = key;
		return numItems;
	}
	
	public void displayNode() {
		for(int i = 0; i < numItems; i++) {
			System.out.print(this.dataArray[i]);
			if (i != numItems-1) 
				System.out.print("|");
		}
		System.out.print("(" + Integer.toString(this.numItems) + ")");
	}
	
	public boolean isLeaf() {
		if(this.childArray[0] == null)
			return true;
		else 
			return false;
	}
	
	public boolean deleteKey(int key) {
		//cycle through dataArray
		for(int i = 0; i < this.numItems; i++) {
			//found!!
			if(this.dataArray[i] == key) {
				for(int j = i; j < this.numItems; j++) {
					if(j == this.numItems-1) {
						this.dataArray[j] = 0;  //default int value for an area
						this.numItems--;
						return true;
					}
					else
						this.dataArray[j] = this.dataArray[j+1];
				}
			}
			else if(i== this.numItems-1) {
				return false;
			}
		}
		return false;
	}
	
	public int findKey(int key) {
		for(int i = 0; i < this.numItems; i++) {
			if(this.dataArray[i] == key) {
				//found!
				return i+1;
			}
		}
		return -1; //not found
	}
	
	public int childNum() {
		for(int i = 0; i < this.parent.numItems+1; i++) {
			if(this.parent.childArray[i] == this) {
				return i+1;
			}
		}
		System.out.print("\nCouldn't find child number of this node: ");
		this.displayNode();
		System.out.print("\n");
		return -1;
	}
	
	public Node leftSibling() {
		if(this.childNum() == 1)
			return null;
		else
			return this.parent.childArray[this.childNum()-2];
	}
	
	public Node rightSibling() {
		if(this.childNum() == this.parent.numItems+1)
			return null;
		else
			return this.parent.childArray[this.childNum()];
	}
	
}