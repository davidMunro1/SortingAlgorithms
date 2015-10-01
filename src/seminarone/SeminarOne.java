/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seminarone;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author davidmunro
 */
public class SeminarOne {

    /**
     * @param args the command line arguments
     */
    private static int[] array;
    private static Scanner scan = new Scanner(System.in);
    private Scanner sc;
    private FileReader file;
    private static SeminarOne sem = new SeminarOne();

    public static void main(String[] args) {
        int input = 0;
        try {
            System.out.println("Enter number to sort: ");
            scan = new Scanner(System.in);
            input = scan.nextInt();
        } catch (Exception ee) {
            System.out.println("Integer not entered");
        }
        scan.close();
        sem.addElementsToArray(input);
        
        sem.printArrayAfterSorting();

        int length = array.length;
        long startTime = System.nanoTime();

        sem.mergeSort(array, length);
        //sem.quicksort();
        //sem.bubbleSort();
        //sem.insertionSort();

        long stopTime = System.nanoTime();
        long total = stopTime - startTime;

        sem.printArrayAfterSorting();

        System.out.println("Algorithm took  : " + total + " nano seconds" + " / " + (double) total / 1000000000 + " seconds");

    }

    private void addElementsToArray(int in) {
        array = new int[in];
        int p = 0;

        try {
            file = new FileReader("C:\\Users\\davidmunro\\Documents\\million.txt");
            sc = new Scanner(file);

            for (int i = 0; i < in; i++) {

                if (sc.hasNextInt() && i < array.length) {
                    array[i] = sc.nextInt();
                } else {
                    array[i] = array[p++];
                }
            }

            sc.close();

        } catch (FileNotFoundException ee) {
            System.out.println("File not found!");
        } catch (NoSuchElementException ee) {
            System.out.println("No such element");
        }
    }

    private void printArrayAfterSorting() {

        System.out.println("array after sorting: ");

        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    private void bubbleSort() {
        boolean sorted = false;
        int n = array.length;
        int temp;
        while (!sorted) {
            sorted = true;
            for (int j = 1; j < n; j++) {
                if (array[j - 1] < array[j]) {
                    temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                    sorted = false;
                }
            }
        }
    }

    private void insertionSort() {
        int insert;
        int i;
        int j;

        for (j = 1; j < array.length; j++) {
            insert = array[j];
            for (i = j - 1; i >= 0 && array[i] < insert; i--) {
                System.out.println(i);
                array[i + 1] = array[i];
            }
            array[i + 1] = insert;
        }
    }

    private void mergeSort(int array[], int length) {
        int segmentSize = 1;
        int[] temp = new int[length];

        while (segmentSize < length) { //when the segment size is greater than length then the array has been sorted
            mergeSegments(array, temp, segmentSize, length);
            System.out.println("segment size 1:  after first merge or segments :" +segmentSize);
            segmentSize += segmentSize;
            System.out.println("new segement size 2: before second call :" +segmentSize);
            mergeSegments(temp, array, segmentSize, length);
            segmentSize += segmentSize;
            System.out.println("final segment size 3: after second call :" +segmentSize);
        }
    }

    private void mergeSegments(int[] left, int[] right, int segmentSize, int length) {
        int i = 0;

        while (i <= length - 2 * segmentSize) {
            mergeArrays(left, right, i, i + segmentSize - 1, i + 2 * segmentSize - 1);
            i = i + 2 * segmentSize;
        }
        if (i + segmentSize < length) {
            mergeArrays(left, right, i, i + segmentSize - 1, length - 1);
        } else {
            for (int j = i; j <= length - 1; j++) {
                right[j] = left[j];
            }
        }
    }

    private void mergeArrays(int[] leftArray, int[] rightArray, int left, int middle, int right) {
        int i = left;
        int j = middle + 1;
        int k = left;

        while ((i <= middle) && (j <= right)) {
            if (leftArray[i] < (leftArray[j])) {
                rightArray[k] = leftArray[i];
                k++;
                i++;
            } else {
                rightArray[k] = leftArray[j];
                k++;
                j++;
            }
        }

        while (i <= middle) { 
            rightArray[k] = leftArray[i];
            k++;
            i++;
        }
        while (j <= right) {
            rightArray[k] = leftArray[j];
            k++;
            j++;
        }
    }

    public void quicksort() {
        boolean[] sorted = new boolean[array.length];
        int j;
        int sortedCount = 0;
        int number;

        //will first exit the while loop when all of the elements are in the 
        //correct place. 
        while (sortedCount < array.length) {
            for (int i = 0; i < array.length; i++) {
                if (!sorted[i]) { 
                    j = i;
                    while ((j < array.length - 1) && (!sorted[j + 1])) {
                        j++;
                    }
                    i = partition(i, j);
                    number = partition(i, j);
                    sorted[number] = true; //sets the specified number in the array to be sorted
                    sortedCount++; //when the numbers have been swapped the sorted count increases
                }
            }
        }
    }

    private int partition(int left, int right) {
        int i = left;
        int j = right;
        boolean hasMore = true;

        //if there is only one element 
        if (left == right) {
            return left;
        } else {
            while (hasMore) {
                //if the index i is less than the right then it is in the correct
                //position and the i variable should be incremented 
                while (array[i] < array[right]) {
                    i++;
                }
                j--;
                while (array[right] < array[j]) {
                    j--;
                    if (j == left) {
                        hasMore = false;
                    }
                }
                //like base case, there is only 1 element 
                if (i >= j) {
                    hasMore = false;
                } else {
                    swap(i, j);
                }
            }
            swap(i, right);
            return i;
        }
    }

    private void swap(int firstNumberToSwap, int secondNumberToSwap) {
        int temp;
        temp = array[firstNumberToSwap];
        array[firstNumberToSwap] = array[secondNumberToSwap];
        array[secondNumberToSwap] = temp;
    }
}
