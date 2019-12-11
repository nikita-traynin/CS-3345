//we need an undirected graph
//take in a list of pairs (these are the edges)
//need to create the adjacency matrix based off this
import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;


public class Project4 {

	public static void main(String[] args) throws IOException {
		//3 args: flightdata, pathtocalculate file, and outputfile
		Scanner flightDat = readFDF(args[0]);							//create scanner object from data file
		Scanner pathsToCalc = readPTC(args[1]);							//create scanner object from paths file
		BufferedWriter writer = new BufferedWriter(new FileWriter(args[2] + ".txt"));
		ArrayList<City> allCities = new ArrayList<City>();				//allocate a linked list of cities
		allCities = readDataFile(flightDat, allCities);					//read and create the adjacency list
		int maxNumPaths = 1;											//how many paths to display
		printCities(allCities);											//display the adjacency list
		flightCalculation(pathsToCalc, allCities, maxNumPaths, writer);			//calculate and write the ouput
		writer.close();
	}
	
	public static Scanner readFDF(String name) {
		String flightDatFileName = name;
		File flightDatF = new File(flightDatFileName);
		Scanner flightDat;
		try{
			flightDat = new Scanner(flightDatF);
			return flightDat;
		}
		catch (IOException e) {
			System.out.print("\nProblem: can't create scanner obj from file obj\n");
			return null;
		}
	}
	
	public static Scanner readPTC(String name) {
		String pathsToCalculateFile = name;
		File pTCF = new File(pathsToCalculateFile);
		Scanner pathsFile;
		try {
			pathsFile = new Scanner(pTCF);
			return pathsFile;
		}
		catch (IOException e) {
			System.out.print("\nProblem: can't create scanner obj from file obj (file2)\n");
			return null;
		}
	}
	
	public static void printCities(ArrayList<City> cityList) {
		for(City x : cityList) {
			x.print();
		}
	}

	public static ArrayList<City> readDataFile(Scanner flightDat, ArrayList<City> allCities) {
		flightDat.useDelimiter("(\\|)|(\\s+)");
		int numLines = Integer.parseInt(flightDat.nextLine());
		for(int i = 0; i < numLines; i++) {
			//read the first city
			City cityA = addCity(flightDat, allCities);
			String nameA = cityA.name;
			City cityB = addCity(flightDat, allCities);
			String nameB = cityB.name;
			
			//read cost and time
			int tempCost = Integer.parseInt(flightDat.next());
			int tempTime = Integer.parseInt(flightDat.next());
			
			//throw it into the destination list for city A and B
			for(City c : allCities) {
				if(c.name.equals(nameA)) {
					c.destList.add(new Destination(nameB, tempCost, tempTime));
				}
				else if(c.name.equals(nameB)) {
					c.destList.add(new Destination(nameA, tempCost, tempTime));
				}
			}
			
		}
		return allCities;
	}
	
	public static void flightCalculation(Scanner pathsToCalc, ArrayList<City> allCities, int maxNumPaths, BufferedWriter writer) {
		//start calculating hehe
		int numLines = Integer.parseInt(pathsToCalc.nextLine());
		pathsToCalc.useDelimiter("(\\|)|(\\s+)");
		for(int i = 0; i < numLines; i++) {
			//READ IN THE CITIES
			City sourceCity = getCity(pathsToCalc, allCities);								//source city
			City destCity = getCity(pathsToCalc, allCities);								//destination city
			ArrayList<SearchStack> paths = findAllPaths(allCities, sourceCity, destCity);
			//DETERMINE IF TIME OR COST CONSTRAINS
			char var = pathsToCalc.next().charAt(0);
			displayShortestPaths(paths, maxNumPaths, var, writer);
		}
	}
	
	public static City addCity(Scanner flightDat, ArrayList<City> allCities) {
		String tempCityNameA = flightDat.next();
		boolean cityInside = false;
		for (City c : allCities) {													//search for it
			if (c.name.equals(tempCityNameA))
				{cityInside = true; return c;}
		}
		if (!cityInside) {
			City newCity = new City(tempCityNameA, new LinkedList<Destination>());
			allCities.add(newCity);													//if/when it's not there, add!
			return newCity;
		}
		return null;
	}
	
	public static City getCity(Scanner pathsToCalc, ArrayList<City> allCities) {
		String sourceName = pathsToCalc.next();
		City theCity = null;
		for (City x: allCities) {
			if(x.name.equals(sourceName))
			{theCity = x; break;}
		}
		if(theCity == null) {
			System.out.print("\nUh-oh, path list file contains a city not on the map\n");
			String temp = pathsToCalc.nextLine();
		}
		return theCity;
	}
	
	public static void displayShortestPaths(ArrayList<SearchStack> paths, int maxNumPaths, char var, BufferedWriter writer) {
		if(paths.isEmpty())
			System.out.print("\nNo flight paths found!\n");
		else {
			for(int j = 0; j < maxNumPaths && !paths.isEmpty(); j++) {
				SearchStack minStk = null;
				int minimum = paths.get(0).getVar(var);
				for(SearchStack s: paths) {
					if (s.getVar(var) <= minimum)
					{minimum = s.getVar(var); minStk = s;}
				}
				minStk.displayFlightPath(j, var);
				try {
					minStk.writeFlightPath(j, var, writer);
				} catch (IOException e) {
					e.printStackTrace();
				}
				paths.remove(minStk);
			}
		}		
	}
	
	public static ArrayList<SearchStack> findAllPaths(ArrayList<City> allCities, City sourceCity, City destCity) {
		ArrayList<SearchStack> paths = new ArrayList<SearchStack>();
		SearchStack stk = new SearchStack();
		stk.push(new Destination(sourceCity.name, 0, 0));
		stk.peek().currentIndex = 0;
		while(!stk.isEmpty()) {
			//find the next destination
			for (City x : allCities) {								//find the city of that name
				if(x.name.equals(stk.peek().name)) {
					if(stk.peek().currentIndex < x.destList.size()) {
						Destination upNext = x.destList.get(stk.peek().currentIndex);
						//if we already hit this destination previously (skip it)
						if(stk.isIn(upNext)) {
							stk.peek().currentIndex++;
							break;
						}
						//if we now reached the final destination (path found)
						else if (upNext.name.equals(destCity.name)){
							stk.push(upNext);
							paths.add(new SearchStack(stk));		//note down the found path!
							stk.pop();
							stk.peek().currentIndex++;
							break;
						}
						//if this is a new path node
						else {
							stk.push(upNext);
							stk.peek().currentIndex = 0;
							break;
						}
					}
					else {
						stk.pop();
						if(!stk.isEmpty())
							stk.peek().currentIndex++;
						break;
					}									

				}
			}
		}
		return paths;
	}
}
