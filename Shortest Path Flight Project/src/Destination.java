
public class Destination {
	String name;
	int cost;
	int time;
	int currentIndex;
	
	//constructor, the only one we'll need
	public Destination(String name, int cost, int time) {
		this.name = name;
		this.cost = cost;
		this.time = time;
	}
	
	public void print() {
		System.out.print(name + ", " + cost + "$, " + time + "min");
	}
}
