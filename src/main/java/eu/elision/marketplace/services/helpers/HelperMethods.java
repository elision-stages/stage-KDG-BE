package eu.elision.marketplace.services.helpers;

import java.security.SecureRandom;
import java.util.Random;

/**
 * This class contains helper methods to be used all through the project
 */
public class HelperMethods {
    private static final Random random = new SecureRandom();

    private HelperMethods() {
    }

    /**
     * This method generates a random string.
     * @param length the length of the string that needs to be generated
     * @return a random string with the given length
     */
    public static String randomString(int length) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            final int nextInt = random.nextInt(24);

            builder.append(alphabet[nextInt]);
        }

        return builder.toString();
    }

    /**
     * Returns a random int value
     * @param limit the upper bound of the int
     * @return a random integer
     */
    public static int randomInt(int limit) {
        return random.nextInt(limit);
    }

    /**
     * This method generates a random integer between 0 and 10
     * @return a random integer with upper bound 10
     */
    public static int randomInt() {
        return random.nextInt(10);
    }

    /**
     * This method returns a random double between 0 an 1.0
     * @return a random double
     */
    public static double randomDouble() {
        return random.nextDouble();
    }
}
