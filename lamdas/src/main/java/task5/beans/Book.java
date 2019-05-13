package task5.beans;

import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Book {

    private String title;
    private Set<Author> authors;
    private int numberOfPage;

    public Book(String title, int numberOfPage) {
        this.title = title;
        this.numberOfPage = numberOfPage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void addAuthor(Author author) {
        if (authors == null) {
            authors = new TreeSet<>(Comparator.comparing(Author::getName)
                    .thenComparing(Author::getAge));
        }
        authors.add(author);
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, authors, numberOfPage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return numberOfPage == book.numberOfPage &&
                Objects.equals(title, book.title);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + Optional.ofNullable(authors)
                .map(authors1 -> authors1.stream()
                        .map(this::authorToString)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList()) +
                ", numberOfPage=" + numberOfPage +
                '}';
    }

    private String authorToString(Author author) {
        return "Author{" +
                "name='" + author.getName() + '\'' +
                ", age=" + author.getAge() +
                '}';
    }
}
