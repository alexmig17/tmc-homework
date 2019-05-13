package task5.beans;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Author {

    private String name;
    private short age;
    private Set<Book> books;

    public Author(String name, short age, List<Book> books) {
        this.name = name;
        this.age = age;
        this.books = new TreeSet<>(Comparator.comparing(Book::getTitle)
                .thenComparing(Book::getNumberOfPage));
        this.books.addAll(books);
        this.books.forEach(book -> book.addAuthor(this));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, age, books);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return age == author.age &&
                Objects.equals(name, author.name);
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", books=" + Optional.ofNullable(books)
                .map(books1 -> books1.stream()
                        .map(this::bookToString)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList()) +
                '}';
    }

    private String bookToString(Book book) {
        return "Book{" +
                "title='" + book.getTitle() + '\'' +
                ", numberOfPage=" + book.getNumberOfPage() +
                '}';
    }
}
