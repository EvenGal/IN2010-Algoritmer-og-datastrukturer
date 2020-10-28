// Precode from task
class Quick extends Sorter {

    void sort(){
        
    }

    void sort(int[] list, int min, int max) {
        // Array to be sorted is list, length of array is n
        if(min < max){
            int n = partitionPivot(this.A, min, max);

            sort(list, min, n-1);
            sort(list, n+1, max);
        }
    }

    // Finds pivot element (last element), places this element at correct position
    // Smaller elements is then placed to the left to pivot, bigger to the right of pivot
    int partitionPivot(int[] array, int min, int max){

        int pivotPoint = array[max];
        int i = (min - 1);
        
        for(int j = min; j < max; j++){
            comparisonCounter();
            if(array[j] < pivotPoint){
                swapCounter();
                i++;

                int temporary = array[i];
                array[i] = array[j];
                array[j] = temporary;
            }
        }
        swapCounter();
        int temporary2 = array[i + 1];
        array[i + 1] = array[max];
        array[max] = temporary2;

        return i + 1;
    }

    String algorithmName() {
        return "quickSort";
    }
}
