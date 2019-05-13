package test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * utils for storage random generator methods
 */
public final class RandomUtils {

    private RandomUtils() {
    }

    /**
     * random big decimal
     *
     * @return BigDecimal
     */
    public static BigDecimal randomBigDecimal() {
        return new BigDecimal(random()).add(new BigDecimal(randomPositive(10000)).divide(new BigDecimal(100000 * randomPositive(10000)), 10, RoundingMode.HALF_UP)
        );
    }

    /**
     * random positive big decimal
     *
     * @return BigDecimal
     */
    public static BigDecimal randomPositiveBigDecimal() {
        BigDecimal bigDecimal = randomBigDecimal();
        if (bigDecimal.compareTo(BigDecimal.ZERO) < 0) {
            bigDecimal = additiveInverse(bigDecimal);
        }
        return bigDecimal;
    }

    /**
     * random int
     *
     * @return int
     */
    public static int random() {
        return new Random().nextInt();
    }

    /**
     * random positive int
     *
     * @return int
     */
    public static int randomPositive() {
        return randomPositive(Integer.MAX_VALUE);
    }

    /**
     * random positive int with max limit
     *
     * @param max limit
     * @return int
     */
    public static int randomPositive(int max) {
        return new Random().nextInt(max) + 1;
    }

    /**
     * additive Inverse
     *
     * @param number number for inverse
     * @return inverted number
     */
    private static BigDecimal additiveInverse(BigDecimal number) {
        return number.multiply(new BigDecimal(-1));
    }
}
