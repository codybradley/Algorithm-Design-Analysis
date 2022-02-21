// Cody Bradley
// Project 4
// 11/19/2021
// Dynamic Programming for Largest
// Non-Consecutive Subset of Array

import java.util.*;
import java.io.*;

// This project reads a set of integers from a file
// and finds the largest sum of any subset where
// no two numbers are consecutive in the array
public class Project4 {

    public static void main(String[] args) {
        int[] inputArray = getInput();
        System.out.println(findMaxSubset(inputArray));
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

    // Calculates max sum by calculating max sum using less elements
    private static int findMaxSubset(int[] inputArray) {
        int[] maxSubset = new int[inputArray.length];

        // base cases
        maxSubset[0] = Math.max(inputArray[0], 0);
        if (maxSubset.length >= 2) {
            maxSubset[1] = Math.max(inputArray[1], maxSubset[0]);
            // this can't be included in recursive case because
            // inputArray[1] + maxSubset[-1] doesn't exist
        }
        // at each iteration, either take value at position i and not position i-1
        // (inputArray[i] + maxSubset[i-2])
        // or don't take value at position i, but possibly take i-1
        // (maxSubset[i-1])
        for (int i = 2; i < maxSubset.length; i++) {
            maxSubset[i] = Math.max(inputArray[i] + maxSubset[i - 2], maxSubset[i - 1]);
        }
        // last element is what we care about
        return maxSubset[maxSubset.length - 1];
    }
}