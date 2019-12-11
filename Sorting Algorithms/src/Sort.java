import java.util.Arrays;
import java.io.*;
import java.util.Scanner;
import java.lang.Math;


public class Sort {
	
	
	public static void main(String[] args) {
		int min = 0;
		int max = 200;
		int range = max-min;
		int [] counts = new int[2];
		int reps = 10;
		int [] movesC = new int[1000];
		int [] compsC = new int[1000];
		int sumc; int sumb;
		
		//for each number of elements in list
		for(int i = 1; i < 5001; i = i+5) {
			int [] list = new int[i];
			
			
			//do it ten times to average out results
			int Mavgs = 0;
			int Cavgs = 0;
			for(int b = 0; b < reps; b++) {
				//for each element in the list
				for(int j = 0; j < i; j++) {
					list[j] = (int)Math.round(Math.random()*range + min);
				}
				counts = hellaSort.insertionSort(list);
				Cavgs += counts[0];
				Mavgs += counts[1];
			}
			
			movesC[(i-1)/5] = Mavgs/reps;
			compsC[(i-1)/5] = Cavgs/reps;
			
		}
		
		System.out.print("\n" + Arrays.toString(movesC));
		System.out.print("\n" + Arrays.toString(compsC));
		
		
		
		
		
		System.out.print("How many elements in list?\n");
		Scanner input = new Scanner (System.in);
		
		int numElements = input.nextInt();
		boolean random = true;
		int[] list = new int[numElements];
		if(random == true) {
			double coef; int num;
			min = 0;
			max = 25;
			range = max-min;
			for(int k = 0; k < numElements; k++) {
				coef = Math.random();
				num = (int)Math.round(coef*range + min);
				list[k] = num;
			}
			
		}
		System.out.print("InsertionSort, SelectionSort, HeapSort, BucketSort, MergeSort, or QuickSort?");
		String select = input.next();
		char cselect = select.charAt(0);
		System.out.print("Old List:" + Arrays.toString(list));
		int newList[] = new int[numElements];
		if(cselect == 'i') {
			newList = hellaSort.insertionSort(list);
		}
		else if(cselect == 's') {
			newList = hellaSort.selectionSort(list);
		}
		else if(cselect == 'q') {
			newList = hellaSort.quickSort(list);
		}
		/* this method does hard copy, other don't (bc technicality) */
		else if(cselect == 'h') {
			//create Integer version of list
			Integer[] list1 = new Integer[list.length];
			for(int i = 0; i < list.length; i++) 
				list1[i] = list[i];
			//create Integer version of newList
			Integer[] newList1 = new Integer[list.length];
			newList1 = HeapSort.<Integer>heapSort(list1);
			//convert Integer version back to int version
			for(int i = 0; i < list.length; i++)
				newList[i] = newList1[i];
		}
		else if(cselect == 'm') {
			hellaSort.mergeSort(list);
			newList = list;
		}
		else if(cselect == 'b') {
			hellaSort.bucketSort(list);
			newList = list;
		}
		System.out.print("\nNew List: " + Arrays.toString(newList));
		
	}
	
	

}

