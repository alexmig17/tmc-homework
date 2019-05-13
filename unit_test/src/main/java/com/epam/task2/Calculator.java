package com.epam.task2;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * basic calculator method
 */
final class Calculator {

    private static final int SCALE = 5;
    private static final int ROUND_MODE = ROUND_HALF_UP;

    private Calculator() {
    }

    /**
     * mathematics method for calculate sum of int values
     *
     * @param firstAddend  first addend
     * @param secondAddend second addend
     * @return sum
     */
    static long addition(int firstAddend, int secondAddend) {
        return ((long) firstAddend) + secondAddend;
    }

    /**
     * mathematics method for calculate sum of double values
     *
     * @param firstAddend  first addend
     * @param secondAddend second addend
     * @return sum
     */
    static BigDecimal addition(BigDecimal firstAddend, BigDecimal secondAddend) {
        requireNonNull(firstAddend);
        requireNonNull(secondAddend);
        return firstAddend.add(secondAddend);
    }

    /**
     * mathematics method for subtraction int values
     *
     * @param minuend    minuend
     * @param subtrahend subtrahend
     * @return residual
     */
    static long subtraction(int minuend, int subtrahend) {
        return ((long) minuend) - subtrahend;
    }

    /**
     * mathematics method for subtraction double values
     *
     * @param minuend    minuend
     * @param subtrahend subtrahend
     * @return residual
     */
    static BigDecimal subtraction(BigDecimal minuend, BigDecimal subtrahend) {
        requireNonNull(minuend);
        requireNonNull(subtrahend);
        return minuend.subtract(subtrahend);
    }

    /**
     * mathematics method for multiplication int
     *
     * @param firstMultiplier  first Multiplier
     * @param secondMultiplier second Multiplier
     * @return composition
     */
    static long multiplication(int firstMultiplier, int secondMultiplier) {
        return ((long) firstMultiplier) * secondMultiplier;
    }

    /**
     * mathematics method for multiplication double
     *
     * @param firstMultiplier  first Multiplier
     * @param secondMultiplier second Multiplier
     * @return composition
     */
    static BigDecimal multiplication(BigDecimal firstMultiplier, BigDecimal secondMultiplier) {
        requireNonNull(firstMultiplier);
        requireNonNull(secondMultiplier);
        return firstMultiplier.multiply(secondMultiplier);
    }

    /**
     * mathematics method for division int
     *
     * @param dividend dividend
     * @param divider  divider
     * @return quotient
     */
    static BigDecimal division(int dividend, int divider) {
        if (divider == 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(dividend).divide(new BigDecimal(divider), SCALE, ROUND_MODE);
    }

    /**
     * mathematics method for division double
     *
     * @param dividend dividend
     * @param divider  divider
     * @return quotient
     */
    static BigDecimal division(BigDecimal dividend, BigDecimal divider) {

        requireNonNull(dividend);
        requireNonNull(divider);
        if (divider.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException();
        }
        return dividend.divide(divider, SCALE, ROUND_MODE);
    }

    /**
     * mathematics power square for int
     *
     * @param input int
     * @return square
     */
    static long power(int input) {
        return Integer.valueOf(input).longValue() * input;
    }

    /**
     * mathematics power square for BigDecimal
     *
     * @param input BigDecimal
     * @return square
     */
    static BigDecimal power(BigDecimal input) {
        requireNonNull(input);
        return input.multiply(input);
    }

    /**
     * mathematics method for root input int
     *
     * @param input input int
     * @return calculated root result
     */
    static BigDecimal root(int input) {
        if (input < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(Math.sqrt(input)).setScale(SCALE, ROUND_MODE);
    }

    /**
     * mathematics method for root input double
     *
     * @param input input int
     * @return calculated power result
     */
    static BigDecimal root(BigDecimal input) {
        if (input.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        requireNonNull(input);

        return new BigDecimal(Math.sqrt(input.doubleValue())).setScale(SCALE, ROUND_MODE);
    }

    /**
     * check does input int is prime
     *
     * @param input input int
     * @return boolean
     */
    static boolean isPrime(int input) {
        if (input < 1) {
            throw new IllegalArgumentException("is not natural number");
        }
        if (input % 2 == 0) return false;
        for (int i = 3; i * i <= input; i += 2) {
            if (input % i == 0)
                return false;
        }
        return true;
    }

    /**
     * return Fibonacci sequence
     * sequence is limited by max Integer value
     *
     * @return boolean
     */
    static List<Integer> fibonacci() {
        List<Integer> fibonacciList = new ArrayList<>();
        int first = 0;
        int next = 1;
        fibonacciList.add(first);
        fibonacciList.add(next);
        int fibonacci;
        while (Integer.MAX_VALUE - first > next) {
            fibonacci = first + next;
            first = next;
            next = fibonacci;
            fibonacciList.add(fibonacci);
        }
        return fibonacciList;
    }
}
