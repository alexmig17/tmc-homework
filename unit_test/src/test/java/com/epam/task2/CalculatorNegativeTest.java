package com.epam.task2;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * negative tests for Calculator class
 */
public class CalculatorNegativeTest {

    /**
     * get all null cases for biFunction with input BigDecimal type
     *
     * @return test case
     */
    private static Stream<Arguments> getNullForBigDecimalNegativeTests() {
        return Stream.of(
                Arguments.of(null, new BigDecimal(1)),
                Arguments.of(new BigDecimal(1), null),
                Arguments.of(null, null)
        );
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("negative randomTests for addition operation with big decimal input type")
    class AdditionNegativeTestsBigDecimalType {

        @ParameterizedTest
        @MethodSource("testNotValidData")
        @DisplayName("should trow NullPointerException when any input is null")
        void should_trowNullPointerException_whenAnyInputIsNull(BigDecimal f, BigDecimal s) {
            Executable executable = () -> Calculator.addition(f, s);
            assertThrows(NullPointerException.class, executable);
        }

        /**
         * test data for negative cases
         *
         * @return test data
         */
        Stream<Arguments> testNotValidData() {
            return getNullForBigDecimalNegativeTests();
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("negative randomTests for subtraction operation with big decimal input type")
    class SubtractionNegativeTestsBigDecimalType {

        @ParameterizedTest
        @MethodSource("testNotValidData")
        @DisplayName("should trow NullPointerException when any input is null")
        void should_trowNullPointerException_whenAnyInputIsNull(BigDecimal f, BigDecimal s) {
            Executable executable = () -> Calculator.subtraction(f, s);
            assertThrows(NullPointerException.class, executable);
        }

        /**
         * test data for negative test
         *
         * @return test data
         */
        Stream<Arguments> testNotValidData() {
            return getNullForBigDecimalNegativeTests();
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("negative randomTests for multiplication operation with BigDecimal input data")
    class MultiplicationNegativeTestsBigDecimalType {

        @ParameterizedTest
        @MethodSource("testNotValidData")
        @DisplayName("should trow NullPointerException when any input is null")
        void should_trowNullPointerException_whenAnyInputIsNull(BigDecimal f, BigDecimal s) {
            Executable executable = () -> Calculator.multiplication(f, s);
            assertThrows(NullPointerException.class, executable);
        }

        /**
         * test data for negative randomTests
         *
         * @return test data
         */
        Stream<Arguments> testNotValidData() {
            return getNullForBigDecimalNegativeTests();
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("negative test for division operation")
    class DivisionNegativeTest {

        @ParameterizedTest
        @MethodSource("testNotValidData")
        @DisplayName("should trow NullPointerException when any input is null")
        void should_trowNullPointerException_whenAnyInputIsNull(BigDecimal f, BigDecimal s) {
            Executable executable = () -> Calculator.division(f, s);
            assertThrows(NullPointerException.class, executable);
        }

        /**
         * test data for negative test
         *
         * @return test data
         */
        Stream<Arguments> testNotValidData() {
            return getNullForBigDecimalNegativeTests();
        }

        @Test
        @DisplayName("should trow IllegalArgumentException when divider is zero")
        void should_trowIllegalArgumentException_whenInputDividerBigDecimalIsZero() {
            Executable executable = () -> Calculator.division(new BigDecimal(1), new BigDecimal(0));
            assertThrows(IllegalArgumentException.class, executable);
        }

        @Test
        @DisplayName("should trow IllegalArgumentException when divider is zero")
        void should_trowIllegalArgumentException_whenInputDividerIntegerIsZero() {
            Executable executable = () -> Calculator.division(1, 0);
            assertThrows(IllegalArgumentException.class, executable);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("negative test for root operation")
    class RootNegativeTest {

        @Test
        @DisplayName("should trow NullPointerException when input is null")
        void should_trowNullPointerException_whenInputIsNull() {
            Executable executable = () -> Calculator.root(null);
            assertThrows(NullPointerException.class, executable);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-1", "-2.05", "-3.0000000000000001", "-1000.45", "-23.0054"})
        @DisplayName("should trow IllegalArgumentException when input less then zero")
        void should_trowIllegalArgumentException_whenInputLessThenZero(BigDecimal input) {
            Executable executable = () -> Calculator.root(input);
            assertThrows(IllegalArgumentException.class, executable);
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -2, -3, -1000, -23})
        @DisplayName("should trow IllegalArgumentException when input less then zero")
        void should_trowIllegalArgumentException_whenInputLessThenZero(int input) {
            Executable executable = () -> Calculator.root(input);
            assertThrows(IllegalArgumentException.class, executable);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("negative test for isPrime operation")
    class IsPrimeNegativeTest {

        @ParameterizedTest
        @ValueSource(ints = {-1, 0, -5, -7, -7918})
        @DisplayName("should trow IllegalArgumentException when input type is not natural")
        void should_trowIllegalArgumentException_whenInputTypeIsNotNatural(int input) {
            Executable executable = () -> Calculator.isPrime(input);
            assertThrows(IllegalArgumentException.class, executable);
        }
    }
}
