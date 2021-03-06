package com.example.musicplayerapp.Random;

import java.util.Random;

public class RandomString {

    // function to generate a random string of length n
    public static String getNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "0123456789";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return "_" + sb.toString();
    }

    public static int getNumber(int number, int index) {
        Random rand = new Random();
        int result;
        do {
            result = rand.nextInt(number);
        } while (result == index);
        return result;
    }
}