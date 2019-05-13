package com.epam.task1;

import static java.lang.String.format;
import static java.text.Normalizer.Form.NFD;
import static java.text.Normalizer.normalize;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * application utils class
 */
final class AppUtils {

    private AppUtils() {
    }

    /**
     * Until method for concatenate two strings
     *
     * @param first  string
     * @param second second string will be concatenated by the end of the first string
     * @return concatenated String
     */
    static String concatenateTwoStrings(final String first, final String second) {
        String notNullFirst = first == null ? EMPTY : first;
        String notNullSecond = second == null ? EMPTY : second;
        return notNullFirst.concat(notNullSecond);
    }

    /**
     * calculate n factorial
     *
     * @param n input data
     * @return long
     */
    static long computeFactorial(int n) {
        Long nFactorial = 1L;
        if (validNFactorialRange(n)) {
            for (long i = 2; i <= n; i++) {
                nFactorial *= i;
            }
        } else {
            throw new IllegalArgumentException(format("n is not valid : '%d'. Valid 'n' values from '0' to 20", n));
        }
        return nFactorial;
    }

    /**
     * narmalize input string go NFD
     * @param s input string
     * @return normalized String in NFD
     */
    static String normalizeToNFD(String s) {
        requireNonNull(s);
        return normalize(s, NFD);
    }

    /**
     * check if n is valid value for calculate NFactorial
     *
     * @param n int for validation
     * @return true if valid
     */
    private static boolean validNFactorialRange(int n) {
        return n >= 0 && n <= 20;
    }
}
