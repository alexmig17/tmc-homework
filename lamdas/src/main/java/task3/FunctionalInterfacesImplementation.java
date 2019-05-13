package task3;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import task3.beans.Student;
import task3.interfaces.ListGroupBy;

/**
 * class with implemented task3:
 * (10 points) Functional Interface Sandbox
 * 1. Implement each of main Java Standard Library functional interfaces (supplier, predicate etc.) using lambda expressions.
 * 2. Create your own functional interface and add several implementations using both lambda expressions and inner anonymous classes.
 * 3. Add few default methods to it and use them.
 * 4. Add few static methods to it and use them.
 */
public class FunctionalInterfacesImplementation {

    private static final String FN_FIRST_NAME = "firstName";
    private static final String FN_EXCLUDE_LIST_1 = "ExcludeList1";
    private static final String FN_EXCLUDE_LIST_2 = "ExcludeList2";
    private static final String FN_EXCLUDE_LIST_3 = "ExcludeList3";
    private static final String FN_ALEXEY = "Alexey";
    private static final String FN_ALEXANDER = "Alexander";
    private static final String FN_KIRIL = "kiril";
    private static final String LN_LAST_NAME = "lastName";
    private static final String LN_DRAPUN = "Drapun";
    private static final String LN_VOITOV = "Voitov";
    private static final String LN_BOGOMOLOV = "Bogomolov";

    /**
     * main method for run task3
     *
     * @param args no custom args
     */
    public static void main(String[] args) {

        functionExample();
        BiFunctionExample();
        SupplierExample();
        PredicateExample();
        ConsumerExample();
        customFunctionByLambdaExampleOne();
        customFunctionByLambdaExampleTwo();
        customFunctionWithStaticAndDefaultMethodsUsage();
        customFunctionByAnonymousClassExample();
    }

    private static void customFunctionWithStaticAndDefaultMethodsUsage() {
        System.out.println();
        System.out.println("customFunctionWithStaticAndDefaultMethodsUsage");
        ListGroupBy.newInstance(Student::getFirstName)
                .excludeKey(FN_FIRST_NAME)
                .excludeListOfKeys(Arrays.asList(FN_EXCLUDE_LIST_1, FN_EXCLUDE_LIST_2, FN_EXCLUDE_LIST_3))
                .print()
                .applyOn(getNewListOfStudents());
        System.out.println();
    }

    /**
     * example with anonymous class and custom interface - ListGroupBy
     * list grouped by first name.
     */
    private static void customFunctionByAnonymousClassExample() {
        List<Student> students = getNewListOfStudents();
        ListGroupBy<String, Student> groupByFirstNameAnonymous = new ListGroupBy<String, Student>() {
            @Override
            public Map<String, List<Student>> applyOn(List<Student> list) {
                return list.stream().collect(Collectors.groupingBy(Student::getFirstName));
            }
        };
        System.out.println();
        System.out.println("customFunctionByAnonymousClassExample");
        System.out.println("students grouped by first name");
        groupByFirstNameAnonymous.applyOn(students).entrySet().forEach(System.out::println);
        System.out.println();
    }

    /**
     * example with lambda and custom interface - ListGroupBy
     * list grouped by first name.
     */
    private static void customFunctionByLambdaExampleOne() {
        List<Student> students = getNewListOfStudents();
        ListGroupBy<String, Student> groupByFirstName = (list) -> list.stream().collect(Collectors.groupingBy(Student::getFirstName));
        System.out.println();
        System.out.println("customFunctionByLambdaExampleOne");
        System.out.println("students grouped by first name");
        groupByFirstName.applyOn(students).entrySet().forEach(System.out::println);
        System.out.println();
    }

    /**
     * example with lambda and custom interface - ListGroupBy
     * list grouped by last name.
     */
    private static void customFunctionByLambdaExampleTwo() {
        List<Student> students = getNewListOfStudents();
        System.out.println();
        System.out.println("customFunctionByLambdaExampleTwo");
        System.out.println("students grouped by last name");
        ListGroupBy<String, Student> groupByLastName = (list) -> list.stream().collect(Collectors.groupingBy(Student::getLastName));
        groupByLastName.applyOn(students).entrySet().forEach(System.out::println);
        System.out.println();
    }

    /**
     * example with java Consumer interface
     */
    private static void ConsumerExample() {
        Consumer<Object> printObject = System.out::println;
        System.out.println();
        System.out.println("ConsumerExample");
        System.out.println("print Object:");
        printObject.accept("Hello world!!!");
        System.out.println();
    }

    /**
     * example with java Predicate interface
     */
    private static void PredicateExample() {
        Predicate<Object> objectNotNull = Objects::nonNull;
        System.out.println();
        System.out.println("PredicateExample");
        System.out.println("check that object is not null:");
        System.out.println(objectNotNull.test(""));
        System.out.println();
    }

    /**
     * example with java Supplier interface
     */
    private static void SupplierExample() {
        Supplier<Integer> randomBound100 = () -> new Random().nextInt(100);
        System.out.println();
        System.out.println("SupplierExample");
        System.out.println("random value to 100:");
        System.out.println(randomBound100.get());
        System.out.println();
    }

    /**
     * example with java BiFunction interface
     */
    private static void BiFunctionExample() {
        BiFunction<String, Character, List<String>> splitByCharacter = (s, character) -> Arrays.asList(s.split(character.toString()));
        System.out.println();
        System.out.println("BiFunctionExample");
        String inputString = "one;two;empty;test;any;word";
        Character splitCharacter = ';';
        System.out.println(String.format("split string %s By %c character", inputString, splitCharacter));
        System.out.println(splitByCharacter.apply(inputString, splitCharacter));
        System.out.println();
    }

    /**
     * example with java Functional interface
     */
    private static void functionExample() {
        Function<Integer, String> integerToStringConverter = String::valueOf;
        System.out.println();
        System.out.println("functionExample");
        System.out.println("Integer to string converter");
        System.out.println("string value is");
        System.out.println(integerToStringConverter.apply(100));
        System.out.println();
    }

    /**
     * get new not modified list of Students
     *
     * @return List<Student>
     */
    private static List<Student> getNewListOfStudents() {
        return Arrays.asList(
                new Student(FN_FIRST_NAME, LN_LAST_NAME),
                new Student(FN_EXCLUDE_LIST_1, LN_LAST_NAME),
                new Student(FN_EXCLUDE_LIST_2, LN_LAST_NAME),
                new Student(FN_EXCLUDE_LIST_3, LN_LAST_NAME),
                new Student(FN_ALEXEY, LN_DRAPUN),
                new Student(FN_ALEXANDER, LN_VOITOV),
                new Student(FN_KIRIL, LN_DRAPUN),
                new Student(FN_KIRIL, LN_BOGOMOLOV)
        );
    }
}
