package com.exam;

/**
 * Used to find the K-com.exam.Complimentary for a given array of integers.
 */
public class Complimentary {

    private static int[] numbers = new int[] { 3, 4, 5, 7, 5, 9, 1, 2 };

    public static void main(String... args) {

        int[][] pairs = getComplimentary(5, numbers);
        System.out.println("com.exam.Complimentary pairs :");

        for (int[] pair : pairs) {
            System.out.printf("{%d,%d} \n", pair[0], pair[1]);
        }

    }

    /**
     * Finds the complimentary pair using the sum and numbers provided.
     * @param sum
     * @param intArray
     * @return
     */
    private static int[][] getComplimentary(int sum, int[] intArray) {

        int arraySize = intArray.length;

        if (intArray == null || arraySize < 2) {
            return new int[0][0];
        }

        int[][] pairs = new int[arraySize][2];
        int actualSize = 0;

        //Loop on the numbers in the array and subtract it with the sum to
        //to find one the pair.
        for (int i = 0; i < arraySize; i++) {

            int num = intArray[i];

            if ( num < sum) {

                int diff = sum - num;

                //Check if the difference is in the array.
                for (int m = 0; m < arraySize; m++) {
                    if (diff == intArray[m]) {
                        int[] pair = new int[] { num, diff };
                        pairs[i] = pair;
                        actualSize++;
                    }
                }
            }
        }

        int[][] finalPairs = new int[actualSize][2];
        int ctr = 0;

        //Remove pairs with zero values.
        for (int[] p : pairs) {

            if (p[0] != 0 & p[1] != 0) {
                finalPairs[ctr] = p;
                ctr++;
            }
        }

        return finalPairs;
    }
}
