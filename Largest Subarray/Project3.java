// Cody Bradley
// Project 3
// 11/19/2021
// Divide and Conquer for Largest Subarray

import java.util.*;
import java.io.*;

// This project reads a set of integers from a file
// and finds the largest sum of any subarray
public class Project3 {

    public static void main(String[] args) {
        int[] inputArray = getInput();
        System.out.println(findMaxSum(inputArray, 0, inputArray.length - 1));
    }

    // Reading from input.txt and filling inputArray
    private static int[] getInput() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Did you forget the input file?");
            System.exit(1);
        }
        int pos = 0, size = sc.nextInt();
        int[] inputArray = new int[size];
        while (sc.hasNextInt()) {
            inputArray[pos++] = sc.nextInt();
        }
        return inputArray;
    }

    // Recursive algorithm which finds max of left, max of right,
    // and the max that crosses over the middle
    // T(n) = 2T(n/2) + Big-Theta(n)  ->  T(n) = Big-Theta(nlog(n))
    private static int findMaxSum(int[] inputArray, int i, int j) {
        int maxLeft = 0, maxRight = 0, maxCross = 0;
        int maxCrossLeft = 0, maxCrossRight = 0;
        int leftSum = 0, rightSum = 0;
        int middle;
        if (i == j) { // base case: one element in array
            if (inputArray[i] >= 0)
                maxCross = inputArray[i];
            // if it's less than 0, then 0 will be returned
            // since empty array has larger sum than any negative value
        } else { // recursive case
            middle = i + (j - i) / 2;
            maxLeft = findMaxSum(inputArray, i, middle); // solve left
            maxRight = findMaxSum(inputArray, middle + 1, j); // solve right

            // possible that the largest sum has parts in both halves
            // find largest sum starting from middle going left
            for (int m = middle; m >= i; m--) {
                leftSum += inputArray[m];
                maxCrossLeft = Math.max(maxCrossLeft, leftSum);
            }
            // find largest sum starting from middle+1 going right
            for (int n = middle + 1; n <= j; n++) {
                rightSum += inputArray[n];
                maxCrossRight = Math.max(maxCrossRight, rightSum);
            }
            // combine max going left and max going right
            maxCross = maxCrossLeft + maxCrossRight;
        }
        // return whichever is the largest of the three solutions found
        return Math.max(Math.max(maxLeft, maxRight), maxCross);
    }
}