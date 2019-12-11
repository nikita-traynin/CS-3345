
public class HeapSort {
	/*Heap sort method*/
	public static <E extends Comparable<E>> E[] heapSort(E[] list) {
		//Create a Heap of integers
		Heap<E> heap = new Heap<>();
		
		//Add elements to the heap
		for(int i = 0; i < list.length; i++) {
			heap.add(list[i]);
		}
		
		//Remove elements from the heap 
		for(int i = list.length - 1; i >= 0; i--) 
			list[i] = heap.remove();
		
		return list;
	}
	
	


	public static Integer[] hehe(Integer[] list) {
		heapSort(list);
		for (int i = 0; i < list.length; i++) 
			System.out.print(list[i] + " ");
		return list;
	}

}
	
	
