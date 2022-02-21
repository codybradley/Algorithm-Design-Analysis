// Cody Bradley
// Project 1
// 10/7/2021
// Read in a Stable Marriage preference table and create a new table
// sorted by man number, where each value is the man's rank

import java.util.*;
import java.io.*;

// This project reads a Stable Marriage preference table and creates a
// table sorted by man number, where each value is the man's rank
public class Project1 {

   public static void main(String[] args) {
      int[][] inputData = getInput();
      int[][] sortedData = sortByManNum(inputData);
      output(sortedData);
   }

   // Reading from input.txt and filling inputData
   private static int[][] getInput() {
      Scanner sc = null;
      try {
         sc = new Scanner(new File("input.txt"));
      } catch (FileNotFoundException e) {
         System.out.println("Did you forget the input file?");
         System.exit(1);
      }
      int size = sc.nextInt();
      int[][] inputData = new int[size][size];
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            inputData[i][j] = sc.nextInt();
         }
      }
      return inputData;
   }

   // Create a new 2D array sorted by man number instead of man rank
   // where each element is the man's rank
   private static int[][] sortByManNum(int[][] inputData) {
      int size = inputData.length;
      int manIndex;
      int[][] sortedData = new int[size][size];
      for (int i = 0; i < size; i++) {//in inputData, i = woman number - 1
         for (int j = 0; j < size; j++) {//in inputData j = man rank - 1
            //each element in inputData is man number
            //get the index of the man we want to fill in (man number - 1)
            manIndex = inputData[i][j] - 1;
            //put the man's rank into his index (j = rank - 1 || rank = j + 1)
            sortedData[i][manIndex] = j + 1;
         }
      }
      return sortedData;
   }

   // Output formatted 2D array
   public static void output(int[][] array2D) {
      int size = array2D.length;
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            System.out.print(array2D[i][j] + "\t");
         }
         //no blank line after last row
         if (i != size - 1)
            System.out.println();
      }
   }
}
