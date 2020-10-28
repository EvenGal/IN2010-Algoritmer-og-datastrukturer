// Freely picked algorithm with worst case estimate equal to O(n^2)
public class Bubble extends Sorter {

    @Override
    void sort() {
        // Variables for storing array and length of array
        int[] list = this.A;
        int n = list.length;


        for(int i = 0; i < n - 1; i++){
            // Count comparison
            comparisonCounter();
            for(int j = 0; j < n - i - 1; j++){
                // Count comparisons
                comparisonCounter();
                if(list[j] > list[j + 1]){
                    // Count swaps
                    swapCounter();
                    int t = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = t;
                }
            }
        }
        this.A = list;
    }

    @Override
    String algorithmName() {
        return "bubbleSort";
    }
    
}
