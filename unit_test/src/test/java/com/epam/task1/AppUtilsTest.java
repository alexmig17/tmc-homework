package com.epam.task1;

import static java.text.Normalizer.Form.NFD;
import static java.text.Normalizer.isNormalized;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Unit tests for AppUtils class
 */
class AppUtilsTest {

    @Nested
    @DisplayName("computeFactorial tests")
    static class ComputeFactorial {

        /**
         * calculate expected nFactorial
         *
         * @param n input data
         * @return nFactorial
         */
        private static long calculateExpectedNFactorial(int n) {
            long expected = 1L;
            for (long i = 2; i <= n; i++) {
                expected *= i;
            }
            return expected;
        }

        /**
         * valid data for nFactorial with expected result
         *
         * @return Stream of Arguments.
         */
        private static Stream<Arguments> validInputData() {
            int randomN = randomFrom1To19();
            int randomN2 = randomFrom1To19();
            int randomN3 = randomFrom1To19();
            return Stream.of(
                    Arguments.of(0, 1L),
                    Arguments.of(1, 1L),
                    Arguments.of(10, 3628800L),
                    Arguments.of(20, calculateExpectedNFactorial(20)),
                    Arguments.of(randomN, calculateExpectedNFactorial(randomN)),
                    Arguments.of(randomN2, calculateExpectedNFactorial(randomN2)),
                    Arguments.of(randomN3, calculateExpectedNFactorial(randomN3))
            );
        }

        /**
         * invalid data for nFactorial
         *
         * @return Arguments. not valid 'n' for calculate nFactorial.
         */
        private static Stream<Arguments> notValidInputData() {
            return Stream.of(
                    Arguments.of(-1),
                    Arguments.of(21),
                    Arguments.of(randomNegative()),
                    Arguments.of(randomNegative()),
                    Arguments.of(randomNegative()),
                    Arguments.of(random22AndHigher()),
                    Arguments.of(random22AndHigher())
            );
        }

        /**
         * generate random value from 1 to 19
         *
         * @return int
         */
        private static int randomFrom1To19() {
            return new Random().nextInt(19) + 1;
        }

        /**
         * generate random negative int
         *
         * @return int
         */
        private static int randomNegative() {
            return randomPositive() * -1;
        }

        /**
         * generate random positive int
         *
         * @return int
         */
        private static int randomPositive() {
            return new Random().nextInt(Integer.MAX_VALUE) + 1;
        }

        /**
         * generate random int from 22 and higher
         *
         * @return int
         */
        private static int random22AndHigher() {
            return new Random().nextInt(Integer.MAX_VALUE - 21) + 22;
        }

        @ParameterizedTest(name = "run #{index} with [{arguments}]")
        @MethodSource("validInputData")
        @DisplayName("should return n factorial when input data is valid")
        void should_returnNFactorial_when_inputDataIsValid(int n, long expected) {
            long actual = AppUtils.computeFactorial(n);
            assertThat(Long.valueOf(actual)).isEqualTo(Long.valueOf(expected));
        }

        @Test()
        @DisplayName("should return result in 1 Millisecond timeout")
        void should_returnResult_in_1MillisecondTimeOut() {
            assertTimeout(Duration.ofMillis(1), () -> AppUtils.computeFactorial(20));
        }

        @ParameterizedTest(name = "run #{index} with [{arguments}]")
        @MethodSource("notValidInputData")
        @DisplayName("should throw IllegalArgumentException when input data is not valid")
        void should_throwIllegalArgumentException_when_inputIntDataIsNotValid(int n) {
            Executable executable = () -> AppUtils.computeFactorial(n);
            assertThrows(IllegalArgumentException.class, executable);
        }
    }

    @Nested
    @DisplayName("concatenateTwoStrings tests")
    class ConcatenateTwoStrings {

        private static final String FIRST_STRING = "firstString";
        private static final String SECOND_STRING = "SecondString";
        private static final String CONCATENATED_VALID_RESULT = "firstStringSecondString";

        @DisplayName("should return concatenated string when two parameters are not blank")
        @Test
        void should_ReturnConcatenatedString_when_twoParametersNotBlank() {
            String actual = AppUtils.concatenateTwoStrings(FIRST_STRING, SECOND_STRING);
            assertThat(actual).isEqualTo(CONCATENATED_VALID_RESULT);
        }

        @DisplayName("should return empty string when two parameters are null")
        @Test
        void should_ReturnEmptyString_when_twoParametersAreNull() {
            String actual = AppUtils.concatenateTwoStrings(null, null);
            assertThat(actual).isEmpty();
        }

        @DisplayName("should return first string when second string is null")
        @Test
        void should_ReturnFirstString_when_secondStringIsNull() {
            String actual = AppUtils.concatenateTwoStrings(FIRST_STRING, null);
            assertThat(actual).isEqualTo(FIRST_STRING);
        }

        @DisplayName("should return second string when first string is null")
        @Test
        void should_ReturnSecondString_when_firstStringIsNull() {
            String actual = AppUtils.concatenateTwoStrings(null, SECOND_STRING);
            assertThat(actual).isEqualTo(SECOND_STRING);
        }
    }

    @Nested
    @DisplayName("normalizeToNFD tests")
    class NormalizeToNFD {

        private static final String NOT_NORMALIZED = "schön";

        @Test
        @Disabled
        @DisplayName("should normalized input string")
        void should_beNFDNormalized_when_InputDataIsNotNormalized() {
            assertThat(isNormalized(NOT_NORMALIZED, NFD)).isFalse();
            String actual = AppUtils.normalizeToNFD(NOT_NORMALIZED);
            assertThat(isNormalized(actual, NFD)).isTrue();
        }

        @Test
        @DisplayName("should throw NullPointerException when input data is null")
        void should_throwNullPointerException_when_inputDataIsNull() {
            Executable executable = () -> AppUtils.normalizeToNFD(null);
            assertThrows(NullPointerException.class, executable);
        }
    }
}