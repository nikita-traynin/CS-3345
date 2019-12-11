import java.util.LinkedList;

public class City {
	String name;
	LinkedList<Destination> destList;
	
	//constructor
	public City(String name, LinkedList<Destination> destList) {
		this.name = name;
		this.destList = destList;
	}
	
	//print method
	public void print() {
		System.out.print(name + ": ");
		for(Destination d : this.destList) {
			d.print();
			System.out.print(" | ");
		}
		System.out.print(".\n");
	}
}
