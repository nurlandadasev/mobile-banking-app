package com.ma.mobilebankingapp.utilities;

import java.security.SecureRandom;
import java.util.Random;

public class RandomNumber {

    private static final Random RANDOM = new SecureRandom();
    private static final String NUMBERS = "0123456789";



    public static String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
        }
        return new String(returnValue);
    }


}
