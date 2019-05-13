package com.epam.task2;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.time.Duration.ofMillis;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static test.BiFunctionTest.combinedTest;
import static test.BiFunctionTest.combinedTestInvertExpected;
import static test.BiFunctionTest.randomCombinedTests;
import static test.BiFunctionTest.randomTests;
import static test.FunctionTest.combinedTestChangeFirstArgument;
import static test.FunctionTest.randomCombinedTestsInvertFirstArgumentBigDecimal;
import static test.FunctionTest.randomCombinedTestsInvertFirstArgumentInteger;
import static test.FunctionTest.randomTests;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import test.AdditiveInverse;
import test.FunctionTest;
import test.RandomUtils;

/**
 * randomTests for Calculator class
 * <p>
 * (15 points) Test your crazy calculator
 * task description:
 * Implement Calculator class and cover its methods with JUnit randomTests with both positive and
 * negative scenarios. Group negative and positive randomTests cases into separate test suites.
 * Add following functionality:
 * 1. Addition, subtraction, multiplication, division, root/power function for int and double values.
 * 2. Division, root should check parameters and throw IllegalArgumentException
 * (division by zero etc.).
 * 3. isPrime function and design test parameters for negative timeout scenario.
 * 4. Fibonacci sequence function. Use Java Hamcrest matchers to validate result.
 * 5. Handle all edge cases.
 * You should follow TDD approach: red - green – refactor
 */
class CalculatorTest {

    private static final int SCALE = 5;
    private static final int ROUND_MODE = ROUND_HALF_UP;

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("randomTests for addition operations with int input type")
    class AdditionIntType {

        @ParameterizedTest
        @MethodSource("testData")
        void should_returnAdditionResult_whenInputIntData(int f, int s, long expected) {
            long actual = Calculator.addition(f, s);
            assertThat(new Long(actual)).isEqualTo(expected);
        }

        /**
         * test data
         *
         * @return test data
         */
        Stream<Arguments> testData() {
            return Stream.of(
                    singletonList(Arguments.of(0, 0, 0L)),
                    singletonList(Arguments.of(1, 1, 2L)),
                    combinedTest(1, 0, 1L),
                    combinedTest(-10, 0, -10L),
                    combinedTest(25, -25, 0L),
                    combinedTest(20, -40, -20L),
                    combinedTest(-15, -40, -55L),
                    combinedTest(100, -30, 70L),
                    randomCombinedTests(this::calculateExpected, RandomUtils::random),
                    singletonList(Arguments.of(Integer.MIN_VALUE, Integer.MIN_VALUE, calculateExpected(Integer.MIN_VALUE, Integer.MIN_VALUE))),
                    singletonList(Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, calculateExpected(Integer.MAX_VALUE, Integer.MAX_VALUE)))
            ).flatMap(Collection::stream);
        }

        /**
         * calculate expected result based on two arguments
         *
         * @param f first argument
         * @param s second argument
         * @return expected result
         */
        private Long calculateExpected(Integer f, Integer s) {
            return f.longValue() + s.longValue();
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("test addition operation for big decimal input type")
    class AdditionBigDecimalType {

        @ParameterizedTest
        @MethodSource("testData")
        void should_returnAdditionResult_whenInputBigDecimalData(BigDecimal f, BigDecimal s, BigDecimal expected) {
            BigDecimal actual = Calculator.addition(f, s);
            assertThat(actual).isEqualByComparingTo(expected);
        }

        /**
         * test data
         *
         * @return test data
         */
        Stream<Arguments> testData() {
            return Stream.of(
                    singletonList(Arguments.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)),
                    singletonList(Arguments.of(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.valueOf(2))),
                    combinedTest("-1.0000001", "0", "-1.0000001"),
                    combinedTest("0.0000001", "0.0000002", "0.0000003"),
                    combinedTest("-0.0000001", "-0.0000002", "-0.0000003"),
                    randomCombinedTests(this::calculateExpected, RandomUtils::randomBigDecimal)
            ).flatMap(Collection::stream);
        }

        /**
         * calculate expected result based on two arguments
         *
         * @param f first argument
         * @param s second argument
         * @return expected result
         */
        private BigDecimal calculateExpected(BigDecimal f, BigDecimal s) {
            return f.add(s);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("test subtraction operation for int input type")
    class SubtractionIntType {

        @ParameterizedTest
        @MethodSource("testData")
        @DisplayName("should return subtraction result when input int data")
        void should_returnSubtractionResult_whenInputIntData(int f, int s, long expected) {
            long actual = Calculator.subtraction(f, s);
            assertThat(new Long(actual)).isEqualTo(new Long(expected));
        }

        Stream<Arguments> testData() {
            return Stream.of(
                    singletonList(Arguments.of(0, 0, 0L)),
                    singletonList(Arguments.of(1, 1, 0L)),
                    combinedTestInvertExpected(1, 0, 1L),
                    combinedTestInvertExpected(-10, 0, -10L),
                    combinedTestInvertExpected(25, -25, 50L),
                    combinedTestInvertExpected(20, -40, 60L),
                    combinedTestInvertExpected(-15, -40, 25L),
                    combinedTestInvertExpected(100, -30, 130L),
                    combinedTestInvertExpected(100, 30, 70L),
                    randomCombinedTests(this::calculateExpected, RandomUtils::random, AdditiveInverse::additiveInverse),
                    singletonList(Arguments.of(Integer.MAX_VALUE, -100, 100L + Integer.MAX_VALUE)),
                    singletonList(Arguments.of(Integer.MIN_VALUE, Integer.MIN_VALUE, 0L)),
                    singletonList(Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, 0L))
            ).flatMap(Collection::stream);
        }

        private long calculateExpected(Number random1, Number random2) {
            return random1.longValue() - random2.longValue();
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("test subtraction operation for big decimal input type")
    class SubtractionBigDecimalType {

        @ParameterizedTest
        @MethodSource("testData")
        @DisplayName("should return subtraction result when input big decimal data")
        void should_returnSubtractionResult_whenInputBigDecimalData(BigDecimal f, BigDecimal s, BigDecimal expected) {
            BigDecimal actual = Calculator.subtraction(f, s);
            assertThat(actual).isEqualByComparingTo(expected);
        }

        /**
         * test data
         *
         * @return test data
         */
        Stream<Arguments> testData() {
            return Stream.of(
                    singletonList(Arguments.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)),
                    singletonList(Arguments.of(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ZERO)),
                    combinedTestInvertExpected("-1.0000001", "0.0", "-1.0000001"),
                    combinedTestInvertExpected("0.0000001", "0.0000002", "-0.0000001"),
                    combinedTestInvertExpected("-0.0000001", "-0.0000002", "0.0000001"),
                    randomCombinedTests(this::calculateExpected, RandomUtils::randomBigDecimal, AdditiveInverse::additiveInverse)

            ).flatMap(Collection::stream);
        }

        /**
         * calculate expected result based on two arguments
         *
         * @param f first argument
         * @param s second argument
         * @return expected result
         */
        private BigDecimal calculateExpected(BigDecimal f, BigDecimal s) {
            return f.subtract(s);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("test multiplication operation for input int data")
    class MultiplicationIntType {

        @ParameterizedTest
        @MethodSource("testData")
        @DisplayName("should return multiplication result when input int data")
        void should_returnMultiplicationResult_whenInputIntData(int f, int s, long expected) {
            long actual = Calculator.multiplication(f, s);
            assertThat(new Long(actual)).isEqualTo(new Long(expected));
        }

        /**
         * test data
         *
         * @return test data
         */
        Stream<Arguments> testData() {
            return Stream.of(
                    singletonList(Arguments.of(0, 0, 0L)),
                    singletonList(Arguments.of(1, 1, 1L)),
                    singletonList(Arguments.of(1, -1, -1L)),
                    singletonList(Arguments.of(-1, -1, 1L)),
                    combinedTest(1, 0, 0L),
                    combinedTest(50, 0, 0L),
                    combinedTest(25, -25, -625L),
                    combinedTest(20, -40, -800L),
                    combinedTest(-10, -40, 400L),
                    randomCombinedTests(this::calculateExpected, RandomUtils::random),
                    singletonList(Arguments.of(Integer.MIN_VALUE, Integer.MIN_VALUE, 4611686018427387904L)),
                    singletonList(Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, 4611686014132420609L))
            ).flatMap(Collection::stream);
        }

        /**
         * calculate expected result based on two arguments
         *
         * @param f first argument
         * @param s second argument
         * @return expected result
         */
        private long calculateExpected(Integer f, Integer s) {
            return f.longValue() * s.longValue();
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("test multiplication operation when input BigDecimal type")
    class MultiplicationBigDecimalType {

        @ParameterizedTest
        @MethodSource("testData")
        @DisplayName("should return multiplication result when input BigDecimal data")
        void should_returnMultiplicationResult_whenInputBigDecimalData(BigDecimal f, BigDecimal s, BigDecimal expected) {
            BigDecimal actual = Calculator.multiplication(f, s);
            assertThat(actual).isEqualByComparingTo(expected);
        }

        /**
         * test data
         *
         * @return test data
         */
        Stream<Arguments> testData() {
            return Stream.of(
                    singletonList(Arguments.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)),
                    singletonList(Arguments.of(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE)),
                    combinedTest("-1.0", "0", "0"),
                    combinedTest("0.01", "-0.2", "-0.002"),
                    combinedTest("-1.01", "-1.05", "1.0605"),
                    randomCombinedTests(this::calculateExpected, RandomUtils::randomBigDecimal)
            ).flatMap(Collection::stream);
        }

        /**
         * calculate expected result based on two arguments
         *
         * @param f first argument
         * @param s second argument
         * @return expected result
         */
        private BigDecimal calculateExpected(BigDecimal f, BigDecimal s) {
            return f.multiply(s);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("test division operation when input int data")
    class DivisionIntType {

        @ParameterizedTest
        @MethodSource("testData")
        @DisplayName("should return division result when input int data")
        void should_returnDivisionResult_whenInputIntData(int f, int s, BigDecimal expected) {
            BigDecimal actual = Calculator.division(f, s);
            assertThat(actual).isEqualByComparingTo(expected);
        }

        /**
         * test data
         *
         * @return test data
         */
        Stream<Arguments> testData() {
            return Stream.of(
                    singletonList(Arguments.of(0, 1, "0")),
                    singletonList(Arguments.of(1, 1, "1")),
                    singletonList(Arguments.of(1, -1, "-1")),
                    singletonList(Arguments.of(-1, -1, "1")),
                    singletonList(Arguments.of(25, -25, "-1")),
                    singletonList(Arguments.of(-25, 25, "-1")),
                    singletonList(Arguments.of(20, -40, "-0.5")),
                    singletonList(Arguments.of(-40, 20, "-2")),
                    singletonList(Arguments.of(-10, -40, "0.25")),
                    singletonList(Arguments.of(-40, -10, "4")),
                    singletonList(Arguments.of(1, 3, "0.33333")),
                    singletonList(Arguments.of(3, 1, "3")),
                    randomTests(this::calculateExpected, RandomUtils::random),
                    singletonList(Arguments.of(Integer.MIN_VALUE, Integer.MIN_VALUE, "1")),
                    singletonList(Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, "1"))
            ).flatMap(Collection::stream);
        }

        /**
         * calculate expected result based on two arguments
         *
         * @param f first argument
         * @param s second argument
         * @return expected result
         */
        private BigDecimal calculateExpected(Integer f, Integer s) {
            return new BigDecimal(f).divide(new BigDecimal(s), SCALE, ROUND_MODE);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("test division operation for BigDecimal data")
    class DivisionBigDecimalType {

        @ParameterizedTest
        @MethodSource("testData")
        @DisplayName("should return division result when input BigDecimal data")
        void should_returnDivisionResult_whenInputBigDecimalData(BigDecimal f, BigDecimal s, BigDecimal expected) {
            BigDecimal actual = Calculator.division(f, s);
            assertThat(actual).isEqualByComparingTo(expected);
        }

        /**
         * test data
         *
         * @return test data
         */
        Stream<Arguments> testData() {
            return Stream.of(
                    singletonList(Arguments.of("0", "1", "0")),
                    singletonList(Arguments.of("1", "1", "1")),
                    singletonList(Arguments.of("1", "-1", "-1")),
                    singletonList(Arguments.of("-1", "-1", "1")),
                    singletonList(Arguments.of("25", "-25", "-1")),
                    singletonList(Arguments.of("-25", "25", "-1")),
                    singletonList(Arguments.of("20", "-40", "-0.5")),
                    singletonList(Arguments.of("-40", "20", "-2")),
                    singletonList(Arguments.of("-10", "-40", "0.25")),
                    singletonList(Arguments.of("-40", "-10", "4")),
                    singletonList(Arguments.of("1", "3", "0.33333")),
                    singletonList(Arguments.of("3", "1", "3")),
                    randomTests(this::calculateExpected, RandomUtils::randomBigDecimal)
            ).flatMap(Collection::stream);
        }

        /**
         * calculate expected result based on two arguments
         *
         * @param f first argument
         * @param s second argument
         * @return expected result
         */
        private BigDecimal calculateExpected(BigDecimal f, BigDecimal s) {
            return f.divide(s, 5, ROUND_HALF_UP);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("test power operation with input int")
    class PowerSquareForInt {

        @ParameterizedTest
        @MethodSource("testData")
        @DisplayName("should return power result when input int data")
        void should_returnDivisionResult_whenInputIntData(int f, long expected) {
            long actual = Calculator.power(f);
            assertThat(actual).isEqualTo(expected);
        }

        /**
         * test data
         *
         * @return test data
         */
        Stream<Arguments> testData() {
            return Stream.of(
                    singletonList(Arguments.of(0, 0L)),
                    combinedTestChangeFirstArgument(1, 1L),
                    combinedTestChangeFirstArgument(5, 25L),
                    randomCombinedTestsInvertFirstArgumentInteger(this::calculateExpected, RandomUtils::random),
                    singletonList(Arguments.of(Integer.MIN_VALUE, 4611686018427387904L)),
                    singletonList(Arguments.of(Integer.MAX_VALUE, 4611686014132420609L))
            ).flatMap(Collection::stream);
        }

        /**
         * calculate expected result based on one argument
         *
         * @param f first argument
         * @return expected result
         */
        private long calculateExpected(Integer f) {
            return f.longValue() * f.longValue();
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("test power operation with BigDecimal data")
    class PowerSquareForBigDecimal {

        @ParameterizedTest
        @MethodSource("testData")
        @DisplayName("should return power result when input bigDecimal data")
        void should_returnDivisionResult_whenInputTypeIsBigDecimal(BigDecimal f, BigDecimal expected) {
            BigDecimal actual = Calculator.power(f);
            assertThat(actual).isEqualByComparingTo(expected);
        }

        /**
         * test data
         *
         * @return test data
         */
        Stream<Arguments> testData() {
            return Stream.of(
                    singletonList(Arguments.of("0", "0")),
                    inverseBigDecimalExpectedTheSame("1", "1"),
                    inverseBigDecimalExpectedTheSame("5", "25"),
                    randomCombinedTestsInvertFirstArgumentBigDecimal(this::calculateExpected, RandomUtils::randomBigDecimal)
            ).flatMap(Collection::stream);
        }

        /**
         * create two test cases
         * first test case stay data as it is
         * second test case inverse first argument additive. And stay expected result as it is
         *
         * @param f        first argument. String format should be able convert to BigDecimal
         * @param expected expected result. String format should be able convert to BigDecimal
         * @return test cases
         */
        private List<Arguments> inverseBigDecimalExpectedTheSame(String f, String expected) {
            return FunctionTest.combinedTestChangeFirstArgument(new BigDecimal(f), new BigDecimal(expected));
        }

        /**
         * calculate expected result based on first argument
         *
         * @param f first argument
         * @return expected result
         */
        private BigDecimal calculateExpected(BigDecimal f) {
            return f.multiply(f);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("test root operation for int data")
    class RootForInt {

        @ParameterizedTest
        @MethodSource("testData")
        @DisplayName("should return root square result when input int data")
        void should_returnRootResult_whenInputTypeIsInt(int f, BigDecimal expected) {

            BigDecimal actual = Calculator.root(f);
            assertThat(actual).isEqualByComparingTo(expected);
        }

        /**
         * test data
         *
         * @return test data
         */
        Stream<Arguments> testData() {
            return Stream.of(
                    singletonList(Arguments.of(0, "0")),
                    singletonList(Arguments.of(1, "1")),
                    singletonList(Arguments.of(25, "5")),
                    singletonList(Arguments.of(3, "1.73205")),
                    randomTests(this::calculateExpected, RandomUtils::randomPositive)
            ).flatMap(Collection::stream);
        }

        /**
         * calculate expected result based on first argument
         *
         * @param f first argument
         * @return expected result
         */
        private BigDecimal calculateExpected(Integer f) {
            return new BigDecimal(Math.sqrt(f)).setScale(SCALE, ROUND_MODE);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("test root square operation for BigDecimal data")
    class RootForBigDecimal {

        @ParameterizedTest
        @MethodSource("testData")
        @DisplayName("should return root square result when input bigDecimal data")
        void should_returnRootResult_whenInputBigDecimalData(BigDecimal f, BigDecimal expected) {
            BigDecimal actual = Calculator.root(f);
            assertThat(actual).isEqualByComparingTo(expected);
        }

        /**
         * test data
         *
         * @return test data
         */
        Stream<Arguments> testData() {
            return Stream.of(
                    singletonList(Arguments.of("0", "0")),
                    singletonList(Arguments.of("1", "1")),
                    singletonList(Arguments.of("25", "5")),
                    singletonList(Arguments.of("3", "1.73205")),
                    randomTests(this::calculateExpected, RandomUtils::randomPositiveBigDecimal)
            ).flatMap(Collection::stream);
        }

        /**
         * calculate expected result based on first argument
         *
         * @param f first argument
         * @return expected result
         */
        private BigDecimal calculateExpected(BigDecimal f) {
            return new BigDecimal(Math.sqrt(f.doubleValue())).setScale(SCALE, ROUND_MODE);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("test isPrime operation")
    class IsPrime {

        @ParameterizedTest
        @ValueSource(ints = {1, 3, 5, 7, 7919})
        @DisplayName("should return true when input type is prime")
        void should_returnTrue_whenInputTypeIsPrime(int input) {
            boolean actual = Calculator.isPrime(input);
            assertThat(actual).isTrue();
        }

        @ParameterizedTest
        @ValueSource(ints = {4, 6, 8, 9, 7918})
        @DisplayName("should return false when input type is not prime")
        void should_returnFalse_whenInputTypeIsNotPrime(int input) {
            boolean actual = Calculator.isPrime(input);
            assertThat(actual).isFalse();
        }

        @Test
        @DisplayName("should calculate in 1 millisecond")
        void should_returnResult_whenOneMillisecondPassed() {
            assertTimeout(ofMillis(1), () -> Calculator.isPrime(10001));
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("test fibonacci sequence")
    class Fibonacci {

        @Test
        @DisplayName("should calculate correct fibonacci sequence")
        void should_returnValidFibonacciSequence() {
            MatcherAssert.assertThat(Calculator.fibonacci(), Matchers.contains(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89,
                    144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418,
                    317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169,
                    63245986, 102334155, 165580141, 267914296, 433494437, 701408733, 1134903170, 1836311903));
        }
    }
}
