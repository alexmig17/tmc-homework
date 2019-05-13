package task3.interfaces;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents a function that grouping List to Map.
 *
 * @param <K> the type of key in Map
 * @param <V> the input List type
 */
@FunctionalInterface
public interface ListGroupBy<K, V> {

    /**
     * create new instance
     *
     * @param function function mapping input elements to keys
     * @param <K>      Key Type
     * @param <V>      Value Type
     * @return ListGroupBy instance
     */
    static <K, V> ListGroupBy<K, V> newInstance(Function<V, K> function) {
        return (list) -> list.stream().collect(Collectors.groupingBy(function));
    }

    /**
     * applies function for grouping list to map
     *
     * @param list List
     * @return Map
     */
    Map<K, List<V>> applyOn(List<V> list);

    /**
     * exclude key in result map
     *
     * @param key key for exclude
     * @return a composed function that first applies this function and than exclude input key in result map
     */
    default ListGroupBy<K, V> excludeKey(String key) {
        return list ->
                applyOn(list).entrySet()
                        .stream()
                        .filter(entry -> !entry.getKey().equals(key))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * exclude keys in result map
     *
     * @param keys keys for exclude
     * @return a composed function that first applies this function and than exclude input keys in result map
     */
    default ListGroupBy<K, V> excludeListOfKeys(List<String> keys) {
        return list ->
                applyOn(list).entrySet().stream()
                        .filter(entry -> !keys.contains(entry.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * print result map
     *
     * @return a composed function that first applies this function and than print result map
     */
    default ListGroupBy<K, V> print() {
        return list -> applyOn(list).entrySet().stream()
                .peek(System.out::println)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
