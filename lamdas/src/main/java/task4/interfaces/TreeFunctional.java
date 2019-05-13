package task4.interfaces;

/**
 * Represents a function that accepts tree arguments and produces a result.
 *
 * @param <F> the type of the first argument to the function
 * @param <S> the type of the second argument to the function
 * @param <T> the type of the third argument to the function
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface TreeFunctional<F, S, T, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param f the first function argument
     * @param s the second function argument
     * @param t the third function argument
     * @return the function result
     */
    R apply(F f, S s, T t);
}
