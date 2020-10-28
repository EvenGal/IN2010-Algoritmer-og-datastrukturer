// Precode from task
class Insertion extends Sorter {

    void sort() {
        // Do insertion sort here. Use the Sorter's comparison- and swap
        // methods for automatically counting the swaps and comparisons.
        // Stored array from A, as a array containing int
        // Array length is n
        int[] list = this.A;
        int n = list.length;
        // Sort
        for(int i = 1; i < n; i++){
            // Count comparison
            comparisonCounter();
            int object = list[i];
            int j = i - 1;

            while(j >= 0 && list[j] > object){
            // Count swap
                list[j + 1] = list[j];
                j = j - 1;
                swapCounter();
            }
            list[j + 1] = object;
        }
        this.A = list;
    }

    String algorithmName() {
        return "insertionSort";
    }
}
