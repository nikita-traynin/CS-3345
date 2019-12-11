import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class hellaSort {
	
	public static int [] insertionSort(int[] list) {
		//set the counting variables
		int comps = 0;
		int moves = 0;
		
		//begin alg
		for(int i = 1; i < list.length; i++) {
			int currentElement = list[i]; 
			int k;
			for (k = i-1; k>=0 && list[k] > currentElement; k--) {
				list[k+1] = list[k]; moves++; comps++;
			}
			
			list[k+1] = currentElement;
		}
		int [] counts = {comps, moves};
		return counts;
	}
	
	public static int [] selectionSort(int[] list) {
		for(int i = 0; i < list.length-1; i++ ) {
			int currentMin = list[i];
			int currentMinIndex = i;
			
			for(int j = i+1; j < list.length; j++) {
				if(currentMin > list[j]) {
					currentMin = list[j];
					currentMinIndex = j;
				}
			}
			
			if(currentMinIndex != i) {
				list[currentMinIndex] = list[i];
				list[i] = currentMin;
			}
		}
		return list;
	}
	
	public static int [] quickSort(int [] list) {
		list = quickSortSection(list, 0, list.length - 1);
		return list;
	}
	
	public static int [] quickSortSection(int [] list, int first, int last) {
		if(last > first) {
			int pivotIndex = partition(list, first, last);
			list = quickSortSection(list, first, pivotIndex-1);
			list = quickSortSection(list, pivotIndex + 1, last);
		}
		return list;
	}
	
	public static int partition(int [] list, int first, int last) {
		int pivot = list[first];
		int low = first + 1;
		int high = last;
		
		while (low < high) {
			while(low <= high && list[low] <= pivot)
				low++;
			while(low <= high && list[high] > pivot)
				high--;
			
			if(high > low) {
				int temp = list[high];
				list[high] = list[low];
				list[low] = temp;
			}
		}
		
		while (high > first && list[high] >= pivot)
			high--;
		
		if(pivot > list[high]) {
			list[first] = list[high];
			list[high] = pivot;
			return high;
		}
		else {
			return first;
		}
	}
	
	public static void mergeSort(int[] list) {
		if(list.length > 1) {
			//first half
			int[] firstHalf = new int[list.length/2];
			System.arraycopy(list,  0,  firstHalf, 0, list.length/2);
			mergeSort(firstHalf);
			
			//second half
			int secondHalfLength = list.length - list.length/2;
			int[] secondHalf = new int[secondHalfLength];
			System.arraycopy(list, list.length/2, secondHalf, 0, secondHalfLength);
			mergeSort(secondHalf);
			
			//merge first and second half into list
			int current1 = 0;
			int current2 = 0;
			int current3 = 0;
			
			int [] list1 = firstHalf;
			int [] list2 = secondHalf;
			int [] temp = list;
			while(current1 < list1.length && current2 < list2.length) {
				if(list1[current1] < list2[current2])
					temp[current3++] = list1[current1++];
				else
					temp[current3++] = list2[current2++];
			}
			
			while(current1 < list1.length)
				temp[current3++] = list1[current1++];
			
			while(current2 < list2.length)
				temp[current3++] = list2[current2++];
			
			
		}
	}
	public static void bucketSort(int[] list) {
		//mod number
		int n = 10; //decimal system
		int m;
		ArrayList<Integer> [] buckets = new ArrayList[n];
		
		//add all elements of list into buckets
		for(int i = 0; i < list.length; i++) {
			//get key
			m = list[i]%n;
			//create list at key if not yet done
			if(buckets[m] == null) {
				buckets[m] = new ArrayList<Integer>();
			}
			//add value to key
			buckets[m].add(list[i]);
		}
		
		int k = 0;
		//now we insertionsort every bucket
		for(int i = 0; i < n; i++) {
			if(!buckets[i].isEmpty()) {
				int [] temp = new int[buckets[i].size()];
				for(int j = 0; j < buckets[i].size(); j++) {
					temp[j] = buckets[i].get(j);
				}
				insertionSort(temp);
				java.lang.System.arraycopy(temp, 0, list, k, temp.length);
				k = k + temp.length;
			}
			
		}
	}
}
