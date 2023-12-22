package org.in.com.impl.randomstring;

import java.util.Random;

public class RandomString {
	private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }	
}
