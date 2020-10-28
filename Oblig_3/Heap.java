
// Freely picked algorithm with worst case estimate equal to O(n log(n))
public class Heap extends Sorter {

    @Override
    void sort() {
        
        // Sorting list, wiht list length equal to n
        int[] list = this.A;
        int n = list.length;

        for(int i = n/2-1; i >= 0; i--){
            // Recursive method
            heapifySubtree(list, n, i);
        }
        for(int i = n-1; i>0; i--){
            int temporary = list[0];
            list[0] = list[i];
            list[i] = temporary;

            // Recursively calling method heapify
            heapifySubtree(list, i, 0);
        }

        this.A = list;

    }
    // Heapify subtree method
    void heapifySubtree(int[] array, int n, int i){
        
        int max = i;
        int left = 2*i + 1;
        int right = 2*i + 2;

        // If the tree has a larger left child than root
        if(left < n && array[left] > array[max]){
            comparisonCounter();
            swapCounter();
            max = left;
        }

        // If the tree has a larger right child than root
        if(right < n && array[right] > array[max]){
            comparisonCounter();
            swapCounter();
            max = right;
        }

        // If max is not the root in tree
        if(max != i){
            comparisonCounter();
            swapCounter();
            int swap = array[i];
            array[i] = array[max];
            array[max] = swap;

            // Recursive call until tree is heapified
            heapifySubtree(array, n, max);
        }
    }

    @Override
    String algorithmName() {
        return "heapSort";
    }
    
}
