package test;

import static test.RandomUtils.randomPositive;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.params.provider.Arguments;

/**
 * utils for storage method for generate faction test cases
 */
public final class FunctionTest {

    private FunctionTest() {
    }

    /**
     * generate max 15 random test cases
     * One test cases for each  generated data:
     * <code>first <T> Function argument</code> hereinafter <code>f</code>,
     * <code>expected <E> Function argument </code> hereinafter <code>expected</code>
     *
     * @param function function input one T type and return E expected data for test
     * @param random   function for generate random data for <code>f</code> type
     * @param <T>      input types for function. It is input data for test case
     * @param <E>      return type for function. It is expected result
     * @return test cases
     */
    public static <T extends Number, E extends Number> List<Arguments> randomTests(Function<T, E> function, Supplier<T> random) {
        int count = randomPositive(15);
        List<Arguments> arguments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            T random1 = random.get();
            E expected = function.apply(random1);
            arguments.add(Arguments.of(random1, expected));
        }
        return arguments;
    }

    /**
     * additive inverse for <code>f</code>
     * See {@link #randomTests(Function, Supplier, Function)}
     *
     * @param function function input one T type and return E expected data for test
     * @param random   function for generate random data for T type
     * @param <E>      return type for function. It is expected result
     * @return test cases
     */
    public static <E extends Number> List<Arguments> randomCombinedTestsInvertFirstArgumentBigDecimal(Function<BigDecimal, E> function, Supplier<BigDecimal> random) {
        return randomTests(function, random, AdditiveInverse::additiveInverse);
    }

    /**
     * additive inverse for <code>f</code>
     * See {@link #randomTests(Function, Supplier, Function)}
     *
     * @param function function input one T type and return E expected data for test
     * @param random   function for generate random data for T type
     * @param <E>      return type for function. It is expected result
     * @return test cases
     */
    public static <E extends Number> List<Arguments> randomCombinedTestsInvertFirstArgumentInteger(Function<Integer, E> function, Supplier<Integer> random) {
        return randomTests(function, random, AdditiveInverse::additiveInverse);
    }

    /**
     * generate two test case for one input data
     * first test case with data as it is
     * second test case additive inverse for <code>number</>. <code>expected</code> stay as it is
     *
     * @param number   input data for test case
     * @param expected expected data for test case
     * @param <E>      Number
     * @return test cases
     */
    public static <E extends Number> List<Arguments> combinedTestChangeFirstArgument(final BigDecimal number, final E expected) {
        return combinedTestChangeFirstArgument(number, expected, AdditiveInverse::additiveInverse);
    }

    /**
     * generate two test case for one input data
     * first test case with data as it is
     * second test case additive inverse for <code>number</>. <code>expected</code> stay as it is
     *
     * @param number   input data for test case
     * @param expected expected data for test case
     * @param <E>      Number
     * @return test cases
     */
    public static <E extends Number> List<Arguments> combinedTestChangeFirstArgument(final Integer number, final E expected) {
        return combinedTestChangeFirstArgument(number, expected, AdditiveInverse::additiveInverse);
    }

    /**
     * generate max 15 pair random test cases(it mean max 15*2).
     * Pair test cases mean that cratering two test caress for each  generated data:
     * <code>first <T> function argument</code> hereinafter <code>f</code>,
     * <code>expected <E> function argument </code> hereinafter <code>expected</code>
     * First test crated as it is.
     * Second test apply <code>changeNumber</> function on <code>f</code> data. Expected stay as it is
     *
     * @param function     function input one T type and return E expected data for test
     * @param random       function for generate random data for <code>f</code> type
     * @param <T>          input types for function. It is input data for test case
     * @param <E>          return type for function. It is expected result
     * @param changeNumber function for change <code>f</code> result for second test case
     * @return test cases
     */
    private static <T extends Number, E extends Number> List<Arguments> randomTests(Function<T, E> function, Supplier<T> random, Function<T, T> changeNumber) {
        int count = randomPositive(15);
        List<Arguments> arguments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            T random1 = random.get();
            E expected = function.apply(random1);
            arguments.addAll(combinedTestChangeFirstArgument(random1, expected, changeNumber));
        }
        return arguments;
    }

    /**
     * generate two test case for one input data
     * first test case with data as it is
     * second test case change <code>number</> using <code>changeNumber</> function. <code>expected</code> stay as it is
     *
     * @param number       input data for test case
     * @param expected     expected data for test case
     * @param changeNumber function for change <code>number</code> during generate second test case
     * @param <E>          Number
     * @return test cases
     */
    private static <T extends Number, E extends Number> List<Arguments> combinedTestChangeFirstArgument(final T number, final E expected, Function<T, T> changeNumber) {
        return Arrays.asList(Arguments.of(number, expected), Arguments.of(changeNumber.apply(number), expected));
    }
}
