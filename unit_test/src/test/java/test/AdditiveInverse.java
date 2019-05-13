package test;

import java.math.BigDecimal;

/**
 * utils for storage additive inverse for diff number type
 */
public final class AdditiveInverse {

    private AdditiveInverse() {

    }

    /**
     * additive Inverse
     *
     * @param number number for inverse
     * @return inverted number
     */
    public static Long additiveInverse(Long number) {
        return number * -1;
    }

    /**
     * additive Inverse
     *
     * @param number number for inverse
     * @return inverted number
     */
    public static BigDecimal additiveInverse(BigDecimal number) {
        return number.multiply(new BigDecimal(-1));
    }

    /**
     * additive Inverse
     *
     * @param number number for inverse
     * @return inverted number
     */
    static Integer additiveInverse(Integer number) {
        return number * -1;
    }
}
