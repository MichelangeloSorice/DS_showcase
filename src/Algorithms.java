

public class Algorithms<T extends Comparable> {

    // TODO fix using a single copy array
    public T[] mergeSort(T[] arr){
        return mergeSort(arr, 0, arr.length);
    }

    // Last is not included
    public T[] mergeSort(T[] arr, int first, int last){
        int length = last - first;

        //AlreadySorted
        if(length <= 1) return arr;

        int middle = (first + last) / 2;
        T[] merge1 = mergeSort(arr, 0, middle);
        T[] merge2 = mergeSort(arr, middle +1, last);

        return merge(merge1, merge2, last);
    }

    private T[] merge(T[] arr1, T[] arr2, int size){
        int i = 0, j = 0, k = 0;
        T[] res = (T[]) new Object[size];

        for(;i < arr1.length && j < arr2.length; k++){
            if(arr1[i].compareTo(arr2[j]) <= 0){
                res[k] = arr1[i];
                i++;
            }else{
                res[k] = arr2[j];
                j++;
            }
        }

        while(i < arr1.length){
            res[k] = arr1[i];
            i++;
            k++;
        }

        while(j < arr2.length){
            res[k] = arr2[j];
            j++;
            k++;
        }
        return res;
    }


    public void quickSort(T[] arr){
        quickSort(arr, 0, arr.length);
    }

    private void quickSort(T[] arr, int first, int last){
        if(last - first <= 1) return;

        T pivot = arr[last-1];

        // Points to the next element to be swapped
        int nextSwap = first;
        for (int i = first; i < last-1; i++){
            // If value is minor than pivot we swap it with next swap index
            if(arr[i].compareTo(pivot) <  0){
                T tmp = arr[nextSwap];
                arr[nextSwap] = arr[i];
                arr[i] = tmp;
                nextSwap++;
            }
        }

        // Setting pivot to its proper position
        arr[last-1] = arr[nextSwap];
        arr[nextSwap] = pivot;

        quickSort(arr, first, nextSwap-1);
        quickSort(arr, nextSwap+1, last);

    }

}
