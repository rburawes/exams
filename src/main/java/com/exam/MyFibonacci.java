package com.exam;

import java.util.Scanner;
import java.util.stream.IntStream;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * Sample fibonacci implementation.
 *
 * @author rburawes
 */
public class MyFibonacci {

    public static void main(String... args) throws NoSuchFieldException {

        out.print("Enter the max number of fibonacci series: ");
        int number = new Scanner(in).nextInt();
        out.print("Here we go, the number series is: ");

        //This can be done using the old for-loop.
        //for (int i = 0; i <= number; i++){
        //    out.print(getNextNumber(i) + " ");
        //}
        IntStream.range(0, number).forEach(n -> out.print(getNextNumber(n) + " "));
    }

    /**
     * Uses tail recursion to get the next number in the series.
     */
    private static int getNextNumber(int number) {

        if (number == 0 || number == 1) {
            return number;
        }

        return getNextNumber(number - 1) + getNextNumber(number - 2);
    }
}
