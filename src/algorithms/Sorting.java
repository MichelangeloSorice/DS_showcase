package algorithms;

public class Sorting<T extends Comparable<T>> {

    /**
     * MERGE SORT: main idea is that if an array is not sorted,
     * we split it in two halves. If those are not sorted we split them again.
     * Finally we end up with a single element array which is obviiously sorted.
     * The we merge sorted arrays which can be done in linear time by scanning
     * both halves in order
     *
     * STABLE - Does maintain original ordering of elements in case of equal keys
     * NB In java arrays.sort uses quick sort for primitive types and ,erge sort for object[] (including Strings)
     *
     * AVG complexity - O(n*log(N)) but requires copy of data: not in place
     * WC - O(n*log(N))
     */
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
            res[k] = arr1[i]; i++; k++;
        }

        while(j < arr2.length){
            res[k] = arr2[j]; j++; k++;
        }
        return res;
    }


// ================================================================================================================

    /**
     * QUICK SORT:
     * We take a pivot point (el in the list), move all bigger things to the right of pivot
     * and all smaller things to the left of it. Recurse and sort the left side and the right side.
     * NOT STABLE - Does not maintain original ordering of elements in case of equal keys
     *
     * AVG. Complexity - O(n*log(N)) in place algorithm
     * WC - O(N*N) when we choose as pivot always the larger or smaller element
     */
    public void quickSort(T[] arr){
        quickSort(arr, 0, arr.length);
    }

    private void quickSort(T[] arr, int first, int last){
        if(last - first <= 1) return;

        // We know nothing about list elements thus we can select any element as pivot
        // Using the last one we can avoid the need of swapping pivot with the last element of the list
        T pivot = arr[last-1];

        // Points to the next element to be swapped
        int nextSwap = first;
        // i goes through the array
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

// ================================================================================================================

    /**
     * COUNTING SORT:
     * Basic assumption is that we are sorting a list of items
     * AND items are all element from a limited (and relatively small) set of keys eg. [0, 100] integers.
     * 1. We scan the array recording how many items of each type are there using an array of counters
     * 2. Scan the array of counters disposing in the result array as many elements as we found for each type
     *
     * Complexity - O(N+k) N is the number of items, K is the number of keys
     * NB if K is in the order of N square we have got a problem :)
     */

// ================================================================================================================

    /**
     * RADIX SORT:
     * The idea here is that in case our set K is too big we need to represent it in a more compact way.
     * 1. Imagine each of those K keys is an integer in base b - it will be represented on log_b(K)+1 digits
     * 2. We use counting sort to sort digits on each digit starting from least up to the most significant one
     *
     * Complexity - O((N+b)*log_b(K))
     * - To minimize it we must choose b = N in that case if k <= N^c - we get O(NC) linear time
     */

}
