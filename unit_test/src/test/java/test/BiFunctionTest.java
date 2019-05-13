package test;

import static test.RandomUtils.randomPositive;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.params.provider.Arguments;

/**
 * utils for storage method for generate biFaction test cases
 */
public final class BiFunctionTest {

    private BiFunctionTest() {
    }

    /**
     * generate max 15 random test cases
     * One test cases for each  generated data:
     * <code>first <T> BiFunction argument</code> hereinafter <code>f</code>,
     * <code>second <T> BiFunction argument</code> hereinafter <code>s</code>,
     * <code>expected <E> BiFunction argument </code> hereinafter <code>expected</code>
     *
     * @param biFunction function input one T type and return E expected data for test
     * @param random     function for generate random data for <code>f</code> type
     * @param <T>        input types for function. It is input data for test case
     * @param <E>        return type for function. It is expected result
     * @return test cases
     */
    public static <T extends Number, E extends Number> List<Arguments> randomTests(BiFunction<T, T, E> biFunction, Supplier<T> random) {
        int count = randomPositive(15);
        List<Arguments> arguments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            T random1 = random.get();
            T random2 = random.get();
            arguments.add(Arguments.of(random1, random2, biFunction.apply(random1, random2)));
        }
        return arguments;
    }

    /**
     * generate max 15 pair random test cases(it mean max 15*2).
     * Pair test cases mean that cratering two test caress for each  generated data:
     * <code>first <T> BiFunction argument</code> hereinafter <code>f</code>,
     * <code>second <T> BiFunction argument</code> hereinafter <code>s</code>,
     * <code>expected <E> BiFunction argument </code> hereinafter <code>expected</code>
     * First test crated as it is.
     * Second test swap <code>f</code> and <code>s</code> arguments and apply changeExpected function on <code>expected</code> data
     *
     * @param biFunction     function input two T type and return E expected data for test
     * @param random         function for generate random data for T type
     * @param <T>            input types for biFunction. It is input data for test case
     * @param <E>            return type for biFunction. It is expected result
     * @param changeExpected function for change <code>expected</code> result for second test case
     * @return test cases
     */
    public static <T extends Number, E extends Number> List<Arguments> randomCombinedTests(BiFunction<T, T, E> biFunction, Supplier<T> random, Function<E, E> changeExpected) {
        int count = randomPositive(15);
        List<Arguments> arguments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            T random1 = random.get();
            T random2 = random.get();
            arguments.addAll(combinedTest(random1, random2, biFunction.apply(random1, random2), changeExpected));
        }
        return arguments;
    }

    /**
     * generate random test cases (max 15*2) without changing executed result
     * See {@link #randomCombinedTests(BiFunction, Supplier, Function)}
     *
     * @param biFunction function input two T type and return E expected data for test
     * @param random     function for generate random data for T type
     * @param <T>        input types for biFunction. It is input data for test case
     * @param <E>        return type for biFunction. It is expected result
     * @return test cases
     */
    public static <T extends Number, E extends Number> List<Arguments> randomCombinedTests(BiFunction<T, T, E> biFunction, Supplier<T> random) {
        return randomCombinedTests(biFunction, random, number -> number);
    }

    /**
     * create combinedTest test case based on String data. String format should be able convert to BigDecimal
     * Second test case swaps first and second agreement. Expected result stay as it is.
     *
     * @param f        first argument
     * @param s        second argument
     * @param expected expected.
     * @return test cases
     */
    public static List<Arguments> combinedTest(String f, String s, String expected) {
        return combinedTest(new BigDecimal(f), new BigDecimal(s), new BigDecimal(expected));
    }

    /**
     * create combinedTest test case based on Number data.
     * Second test case swaps first and second agreement. Expected result stay as it is.
     *
     * @param f        first argument
     * @param s        second argument
     * @param expected expected.
     * @return test cases
     */
    public static <T extends Number, E extends Number> List<Arguments> combinedTest(final T f, final T s, final E expected) {
        return combinedTest(f, s, expected, number -> number);
    }

    /**
     * create combinedTest test case based on String data. String format should be able convert to BigDecimal
     * Second test case swaps first and second agreement. And invert additive in expected result.
     *
     * @param f        first argument
     * @param s        second argument
     * @param expected expected.
     * @return test cases
     */
    public static List<Arguments> combinedTestInvertExpected(String f, String s, String expected) {
        return combinedTestInvertExpected(new BigDecimal(f), new BigDecimal(s), new BigDecimal(expected));
    }

    /**
     * create combinedTest test case based on Number data.
     * Second test case swaps first and second agreement. And invert additive in expected result.
     *
     * @param f        first argument
     * @param s        second argument
     * @param expected expected.
     * @return test cases
     */
    public static List<Arguments> combinedTestInvertExpected(final Number f, final Number s, final Long expected) {
        return combinedTest(f, s, expected, AdditiveInverse::additiveInverse);
    }

    /**
     * create combinedTest test case based on BigDecimal data.
     * Second test case swaps first and second agreement. And invert additive in expected result.
     *
     * @param f        first argument
     * @param s        second argument
     * @param expected expected.
     * @return test cases
     */
    private static List<Arguments> combinedTestInvertExpected(final BigDecimal f, final BigDecimal s, final BigDecimal expected) {
        return combinedTest(f, s, expected, AdditiveInverse::additiveInverse);
    }

    /**
     * create combinedTest test case based on Number data.
     * Second test case swaps first and second agreement. And apply <code>expected</code> function on expected result.
     *
     * @param f        first argument
     * @param s        second argument
     * @param expected expected.
     * @return test cases
     */
    private static <T extends Number, E extends Number> List<Arguments> combinedTest(final T f, final T s, final E expected, Function<E, E> changeExpected) {
        return Arrays.asList(Arguments.of(f, s, expected), Arguments.of(s, f, changeExpected.apply(expected)));
    }
}
