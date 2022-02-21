// Cody Bradley
// Project 2
// 11/7/2021
// Greedy Algorithm for Point Game

import java.util.*;
import java.io.*;

// This project reads a set of integers from a file
// and finds the minimum points based on the ruleset:
// if the adjacent value is larger, it must have more points.
public class Project2 {

   public static void main(String[] args) {
      int[] inputData = getInput();

      // O(nlogn) algorithm since it requires a sort
      // preprocessing step: create 2d array
      // (first row is same, second row are indices)
      // then sort by first row values
      int[][] sortedData = copyWithIndex(inputData);
      mergeSort(sortedData, 0, sortedData[0].length - 1);
      int[] pointsArray = findMinPoints(inputData, sortedData);

      // O(n) algorithm since it goes through the unsorted array 3 times
      // int[] pointsArray = doubleScanMinPoints(inputData);

      outputSum(pointsArray);
   }

   // Reading from input.txt and filling inputData
   private static int[] getInput() {
      Scanner sc = null;
      try {
         sc = new Scanner(new File("input.txt"));
      } catch (FileNotFoundException e) {
         System.out.println("Did you forget the input file?");
         System.exit(1);
      }
      int pos = 0, size = sc.nextInt();
      int[] inputData = new int[size];
      while (sc.hasNextInt()) {
         inputData[pos++] = sc.nextInt();
      }
      return inputData;
   }

   private static int[][] copyWithIndex(int[] inputData) {
      int[][] copiedData = new int[2][inputData.length];
      for (int i = 0; i < inputData.length; i++) {
         copiedData[1][i] = i;//row 1 holds the original index
         copiedData[0][i] = inputData[i];//row 0 holds the original value
      }
      return copiedData;
   }

   // greedy algorithm to find minimum points
   // update points for each value in order from smallest value to largest value
   private static int[] findMinPoints(int[] inputData, int[][] sortedData) {
      int curIndex;
      int size = inputData.length;
      int[] pointsArray = new int[size];
      // all points should start at 1
      Arrays.fill(pointsArray, 1);

      // loop through the values from smallest to largest
      for (int i = 0; i < size; i++) {
         curIndex = sortedData[1][i];

         // can't check to the left if it's the first element
         // can't update points if it's smaller than left neighbor
         // currently has 1 point, don't worry about if it has more than left neighbor's + 1
         if (curIndex != 0
                 && inputData[curIndex] > inputData[curIndex - 1])
            //
            pointsArray[curIndex] = pointsArray[curIndex - 1] + 1;

         // can't check to the right if it's the last element
         // can't update points if it's smaller than right neighbor
         // can't update points if it already has more points than right neighbor's + 1
         if (curIndex != size - 1
                 && inputData[curIndex] > inputData[curIndex + 1]
                 && pointsArray[curIndex] < pointsArray[curIndex + 1] + 1)
            pointsArray[curIndex] = pointsArray[curIndex + 1] + 1;
      }

      return pointsArray;
   }

   // merge sort that has been altered to work for 2d array with 2 rows,
   // where the array is sorted by the first row's values
   private static void mergeSort(int[][] arr, int l, int r) {
      if (l < r) {
         int m = l + (r - l) / 2;

         mergeSort(arr, l, m);
         mergeSort(arr, m + 1, r);

         merge(arr, l, m, r);
      }
   }

   // Helper function for mergeSort
   private static void merge(int[][] arr, int l, int m, int r) {
      int n1 = m - l + 1;
      int n2 = r - m;

      int[][] L = new int[2][n1];
      int[][] R = new int[2][n2];

      for (int i = 0; i < n1; ++i) {
         L[0][i] = arr[0][l + i];
         L[1][i] = arr[1][l + i];
      }
      for (int j = 0; j < n2; ++j) {
         R[0][j] = arr[0][m + 1 + j];
         R[1][j] = arr[1][m + 1 + j];
      }

      int i = 0, j = 0;
      int k = l;
      while (i < n1 && j < n2) {
         if (L[0][i] <= R[0][j]) {
            arr[0][k] = L[0][i];
            arr[1][k] = L[1][i];
            i++;
         } else {
            arr[0][k] = R[0][j];
            arr[1][k] = R[1][j];
            j++;
         }
         k++;
      }

      while (i < n1) {
         arr[0][k] = L[0][i];
         arr[1][k] = L[1][i];
         i++;
         k++;
      }

      while (j < n2) {
         arr[0][k] = R[0][j];
         arr[1][k] = R[1][j];
         j++;
         k++;
      }
   }

   // Sums and outputs ints in an array
   public static void outputSum(int[] pointsArray) {
      int sum = 0;
      for (int i = 0; i < pointsArray.length; i++) {
         sum += pointsArray[i];
      }
      System.out.println(sum);
   }

   // O(n) solution that I have yet to prove gives the optimal solution
   // Scan left to right. If the number is larger than previous,
   // make its points 1 larger than previous.
   // Repeat the process with a separate points array,
   // this time, going right to left.
   // Take the larger points at each position in the arrays
   public static int[] doubleScanMinPoints(int[] inputData) {
      int size = inputData.length;
      int[] leftRightArray = new int[size];
      int[] rightLeftArray = new int[size];
      int[] pointsArray = new int[size];

      leftRightArray[0] = 1;//leftmost starts as 1
      for (int i = 1; i < size; i++) {
         if (inputData[i] > inputData[i - 1])
            leftRightArray[i] = leftRightArray[i - 1] + 1;
         else //inputData[i] <= inputData[i - 1]
            leftRightArray[i] = 1;
      }
      rightLeftArray[size - 1] = 1;//rightmost starts at 1
      for (int i = size - 2; i >= 0; i--) {
         if(inputData[i]>inputData[i+1])
            rightLeftArray[i]=rightLeftArray[i+1]+1;
         else //inputData[i] <= inputData[i + 1]
            rightLeftArray[i]=1;
      }
      //could combine this for loop with one of the previous two
      for (int i = 0; i < size; i++) {
         if(leftRightArray[i]>=rightLeftArray[i])
            pointsArray[i]=leftRightArray[i];
         else
            pointsArray[i]=rightLeftArray[i];
      }

      return pointsArray;
   }
}
