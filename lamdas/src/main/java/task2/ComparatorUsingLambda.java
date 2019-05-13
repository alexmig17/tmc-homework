package task2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import task2.beans.Person;

/**
 * class with implemented task1:
 * Create:
 * 1. Person class with name and age fields
 * 2. A collection of Persons;
 * 3. Two instances of Comparator&lt;Person&gt; interface using lambda expressions: first one for
 * comparing by name, second one – by age
 * Then sort them using these comparators.
 * Use forEach method for printing information about all the persons.
 * Try to use method reference if it is possible.
 */
public class ComparatorUsingLambda {

    /**
     * main method for run task2
     *
     * @param args no custom args
     */
    public static void main(String[] args) {

        List<Person> persons = getNewPersonList();
        persons.forEach(System.out::println);

        sortByNameAndPrint(persons);
        sortByAgeAndPrint(persons);
        sortAndPrintPersons(persons, "sorted by name:", ComparatorUsingLambda::personNameComparator);
        sortAndPrintPersons(persons, "sorted by age:", ComparatorUsingLambda::personAgeComparator);
    }

    /**
     * sort by age and print result
     *
     * @param persons list of persons
     */
    private static void sortByAgeAndPrint(List<Person> persons) {
        System.out.println("sorted by age:");
        persons.stream()
                .sorted(Comparator.comparingInt(Person::getAge))
                .forEach(System.out::println);
    }

    /**
     * sort by name and print result
     *
     * @param persons list of persons
     */
    private static void sortByNameAndPrint(List<Person> persons) {
        System.out.println("sorted by name:");
        persons.stream()
                .sorted(Comparator.comparing(Person::getName))
                .forEach(System.out::println);
    }

    /**
     * @param persons          list of persons
     * @param title            title print before sorted list
     * @param personComparator person comparator
     */
    private static void sortAndPrintPersons(List<Person> persons, String title, Comparator<Person> personComparator) {
        persons.sort(personComparator);
        System.out.println(title);
        persons.forEach(System.out::println);
    }

    /**
     * get new not modified list of person
     *
     * @return List<Person>
     */
    private static List<Person> getNewPersonList() {
        return Arrays.asList(
                new Person("Alexey", 28),
                new Person("Alexander", 10),
                new Person("Grisha", 30),
                new Person("Misha", 50),
                new Person("Valera", 21),
                new Person("Olga", 19),
                new Person("Masha", 36),
                new Person("Dasha", 25),
                new Person("Kate", 24)
        );
    }

    /**
     * person comparator by name
     *
     * @param o1 Person one
     * @param o2 Person two
     * @return int
     */
    private static int personNameComparator(Person o1, Person o2) {
        return o1.getName().compareTo(o2.getName());
    }

    /**
     * person comparator by age
     *
     * @param o1 Person one
     * @param o2 Person two
     * @return int
     */
    private static int personAgeComparator(Person o1, Person o2) {
        return o1.getAge().compareTo(o2.getAge());
    }
}
