import java.util.ArrayList;
import java.io.*;

public class SearchStack {
	ArrayList<Destination> citiesSearched;
	int totalCost;
	int totalTime;
	
	SearchStack() {
		citiesSearched = new ArrayList<Destination>();
		totalCost = 0;
		totalTime = 0;
	}
	SearchStack(ArrayList<Destination> allCities) {
		citiesSearched = allCities;
		totalCost = 0;
		totalTime = 0;
	}
	
	SearchStack(SearchStack another) {
		this.citiesSearched = new ArrayList<Destination>(another.citiesSearched);
		this.totalCost = another.totalCost;
		this.totalTime = another.totalTime;
	}
	
	//removes and returns top element
	public Destination pop() {
		Destination lastOne = citiesSearched.get(citiesSearched.size()-1);
		updatePathWeight(-lastOne.cost, -lastOne.time);
		citiesSearched.remove(citiesSearched.size()-1);
		return lastOne;
	}
	
	//returns index of pushed element (better than add)
	public int push(Destination destination) {
		citiesSearched.add(destination);
		updatePathWeight(destination.cost, destination.time);
		return citiesSearched.indexOf(destination);
	}
	
	public Destination peek() {
		return citiesSearched.get(citiesSearched.size() - 1);
	}
	
	public boolean isIn(Destination d) {
		for(Destination x: citiesSearched) {
			if(x.name.equals(d.name))
				return true;
		}
		return false;
	}
	
	public int getVar(char var) {
		if(var == 'C')
			return totalCost;
		else if(var == 'T')
			return totalTime;
		else
			return -1;
	}
	
	
	
	//check if empty
	public boolean isEmpty() {
		if(citiesSearched.isEmpty())
			return true;
		else
			return false;
	}
	
	public void displayFlightPath(int index, char var) {
		System.out.print("\nFlight Path (");
		if (var == 'C')
				System.out.print("Cost");
		else
			System.out.print("Time");
		System.out.print(") " + (index+1) + ": \n");
		for(int i = 0; i < citiesSearched.size(); i++) {
			System.out.print(citiesSearched.get(i).name );
			if(i != citiesSearched.size() - 1)
				System.out.print(" -> ");
			else
				System.out.print("; ");
		}
		if(var == 'C')
			showCost();
		else if(var == 'T')
			showTime();
		else
			System.out.print("\nuh-oh\n");
		System.out.print(". \n");
	}
	
	public void writeFlightPath(int index, char var, BufferedWriter writer) throws IOException {
		writer.write("\nFlight Path " + (index+1) + ": \n");
		for(int i = 0; i < citiesSearched.size(); i++) {
			writer.write(citiesSearched.get(i).name );
			if(i != citiesSearched.size() - 1)
				writer.write(" -> ");
			else
				writer.write("; ");
		}
		if(var == 'C')
			{writer.write("Total Cost: " + totalCost + "$"); }
		else if(var == 'T')
			{writer.write("Total Time: " + totalTime + " minutes"); }
		else
			writer.write("\nuh-oh\n");
		writer.write(". \n");
	}
	
	public void showCost() {
		System.out.print("Total Cost: " + totalCost + "$");
		
	}
	
	public void showTime() {
		System.out.print("Total Time: " + totalTime + " minutes");
	}
	
	public void updatePathWeight(int cost, int time) {
		addCost(cost);
		addTime(time);
	}
	
	public void addCost(int cost) {
		totalCost += cost;
	}
	
	public void addTime(int time) {
		totalTime += time;
	}
	//find is built-in to the ArrayList type
}
