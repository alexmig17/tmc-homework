package task5;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import task5.beans.Author;
import task5.beans.Book;

/**
 * class with implemented task4:
 * 1.	Create the following classes:
 * a.	Author with fields:
 * 	String name
 * 	short age
 * 	List<Book> books
 * b.	Book with fields
 * •	String title
 * •	List<Author> authors
 * •	int numberOfPages
 * 2.	Create two arrays: Author[] authors and Book[] books. Their elements must use elements from the neighboring array.
 * 3.	Create a stream of books and then:
 * I.	check if some/all book(s) have more than 200 pages;
 * II.	find the books with max and min number of pages;
 * III.	filter books with only single author;
 * IV.	sort the books by number of pages/title;
 * V.	get list of all titles;
 * VI.	print them using forEach method;
 * VII.	get distinct list of all authors
 * 4.	Use peek method for debugging intermediate streams during execution the previous task.
 * 5.	Use parallel stream with subtask #3.
 * 6.	Analyze with mentor results of previous subtask execution, explain him the difference between stream and parallel stream.
 * 7.	Use the Optional type for determining the title of the biggest book of some author.
 */
public class BooksAuthorsStreamOperations {

    private static final Author[] AUTHORS;
    private static final Book[] BOOKS;
    private static Boolean USE_PARALLEL = Boolean.FALSE;

    static {
        Book book1 = new Book("C#", 500);
        Book book2 = new Book("java", 600);
        Book book3 = new Book("java script", 700);
        Book book4 = new Book("angular", 700);
        Book book5 = new Book("java 8", 400);
        Book book6 = new Book("jpa", 550);
        Book book7 = new Book("MySql", 450);
        Book book8 = new Book("Python", 150);
        BOOKS = new Book[]{book1, book2, book3, book4, book5, book6, book7, book8};

        AUTHORS = new Author[]{
                new Author("Nikita", (short) 30, Arrays.asList(book1, book3)),
                new Author("Vladimir", (short) 58, Arrays.asList(book2, book3, book5, book6)),
                new Author("Mercedes", (short) 19, Arrays.asList(book4, book3, book1)),
                new Author("Pikachu", (short) 35, Arrays.asList(book7, book6)),
                new Author("Pokemon", (short) 46, Collections.singletonList(book1)),
                new Author("Pakiman", (short) 22, Collections.singletonList(book8)),
                new Author("Pakiman", (short) 21, Collections.singletonList(book2))
        };
    }

    /**
     * main method for run task5
     *
     * @param args no custom args
     */
    public static void main(String[] args) {
        //change to FALSE when need parallel stream
        //Also you can find out parallelDifference folder with difference )))
        USE_PARALLEL = Boolean.TRUE;
        checkIfSomeBooksHasMoreThan200Pages();
        checkIfAllBooksHasMoreThan200Page();
        findTheBookWithMinNumberOfPages();
        findTheBookWithMaxNumberOfPages();
        filterBooksWithOnlySingleAuthor();
        sortTheBooksByNumberOfPages();
        sortTheBooksByTitle();
        getListOfAllTitles();
        getDistinctListOfAllAuthors();
        usePeekMethodForDebuggingIntermediateStream();
        optionalTitleOfTheBiggestBook();

        //additional example for show difference between parrnell stream.
        Stream<String> wordsStream = streamOf("мама", "мыла", "раму");
        String sentence = wordsStream.reduce("Результат:", (x, y) -> x + " " + y);
        System.out.println(sentence);
        System.out.println();
        sleep();
    }

    private static void optionalTitleOfTheBiggestBook() {
        System.out.println();
        System.out.println("Use the Optional type for determining the title of the biggest book of some author:");
        Optional<Book> optionalBook = streamOf(BOOKS)
                .max(Comparator.comparingInt(Book::getNumberOfPage));
        Optional<String> optionalTitle = optionalBook.map(Book::getTitle);
        System.out.println(String.format("optionalTitle %s", optionalTitle));
    }

    private static void usePeekMethodForDebuggingIntermediateStream() {
        System.out.println();
        System.out.println("Use peek method for debugging intermediate streams:");
        System.out.println(":");
        streamOf(BOOKS)
                .peek(System.out::println)
                .max(Comparator.comparingInt(Book::getNumberOfPage))
                .ifPresent(book -> System.out.println(String.format("Found book with maximum pages: \n %s", book)));
    }

    private static void getDistinctListOfAllAuthors() {
        System.out.println();
        System.out.println("get distinct list of all authors:");
        streamOf(AUTHORS)
                .map(Author::getName)
                .distinct()
                .forEach(System.out::println);
    }

    private static void getListOfAllTitles() {
        System.out.println();
        System.out.println("get list of all titles:");
        streamOf(BOOKS)
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private static void sortTheBooksByNumberOfPages() {
        System.out.println();
        System.out.println("sort the books by number of pages:");
        streamOf(BOOKS)
                .sorted(Comparator.comparingInt(Book::getNumberOfPage))
                .forEach(System.out::println);
    }

    private static void sortTheBooksByTitle() {
        System.out.println();
        System.out.println("sort the books by title:");
        streamOf(BOOKS)
                .sorted(Comparator.comparing(Book::getTitle))
                .forEach(System.out::println);
    }

    private static void filterBooksWithOnlySingleAuthor() {
        System.out.println();
        System.out.println("filter books with only single author:");
        streamOf(BOOKS)
                .filter(book -> book.getAuthors().size() == 1)
                .forEach(System.out::println);
    }

    private static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void findTheBookWithMaxNumberOfPages() {
        System.out.println();
        streamOf(BOOKS)
                .max(Comparator.comparingInt(Book::getNumberOfPage))
                .ifPresent(book -> System.out.println(String.format("Found book with maximum pages: \n %s", book)));
    }

    private static void findTheBookWithMinNumberOfPages() {
        System.out.println();
        streamOf(BOOKS)
                .min(Comparator.comparingInt(Book::getNumberOfPage))
                .ifPresent(book -> System.out.println(String.format("Found book with minimum pages: \n %s", book)));
    }

    private static void checkIfAllBooksHasMoreThan200Page() {
        System.out.println();
        Optional.of(streamOf(BOOKS).allMatch(book -> book.getNumberOfPage() > 200))
                .ifPresent(bool -> System.out.println(String.format("check if all books have more than 200 pages: %b", bool)));
    }

    private static void checkIfSomeBooksHasMoreThan200Pages() {
        System.out.println();
        Optional.of(streamOf(BOOKS).anyMatch(book -> book.getNumberOfPage() > 200))
                .ifPresent(bool -> System.out.println(String.format("check if some book have more than 200 pages: %b", bool)));
    }

    /**
     * common method for create stream.
     * work in two mode : parallel and sequential
     * for quick switch between mode use USE_PARALLEL variable
     *
     * @param values input values for stream
     * @param <T>    Type for Stream
     * @return Stream
     */
    @SafeVarargs
    private static <T> Stream<T> streamOf(T... values) {
        if (USE_PARALLEL) {
            return Stream.of(values).parallel();
        } else {
            return Stream.of(values);
        }
    }
}
