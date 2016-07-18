package com.exam;

/**
 * Used to check if a word or a phrase reads the same backward as forward.
 */
public class Palindrome {

    private static String[] words = new String[] { "civic", "hello", "hannah", "rotator", "decaffeinated", "dish", "madam", "level", "dish" };

    public static void main(String... args) {
        for (String word : words){
            System.out.printf("%s : %b \n", word, isPalindrome(word));
        }
    }

    /**
     * Checks if the word is a palindrome.
     * @param str
     * @return
     */
    private static boolean isPalindrome(String str) {

        int left = 0;
        int right = str.length() - 1;
        int half = right / 2;

        //Reads the character from left and right simultaneously, then compare.
        for (int i = 0; i < half; i++, left++, right--) {

            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
        }

        return true;
    }
}
