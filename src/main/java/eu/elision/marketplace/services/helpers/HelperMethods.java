package eu.elision.marketplace.services.helpers;

import java.util.Random;

public class HelperMethods {
    private static final Random random = new Random();

    private HelperMethods() {
    }

    public static String randomString(int length) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            final int nextInt = random.nextInt(24);

            builder.append(alphabet[nextInt]);
        }

        return builder.toString();
    }

    public static int randomInt(int limit) {
        return random.nextInt(limit);
    }

    public static int randomInt() {
        return random.nextInt(10);
    }

    public static double randomDouble() {
        return Math.random() * 10 + Math.random();
    }
}
