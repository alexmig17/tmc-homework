package com.epam.test.dto;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.epam.test.enums.Sort;
import com.google.common.math.IntMath;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginationResult<T> {

    private List<T> content;
    private List<Integer> totalPages;
    private Integer currentPage;
    private Integer elementCount;
    private String sort;
    private String search;

    public PaginationResult(List<T> content, Integer totalCount, Integer elementCount, Sort sort, String search) {
        notNull(elementCount, "element count can not be null");
        isTrue(elementCount > 0, "element count should be more than 0");
        this.content = content;
        Integer pages = IntMath.divide(totalCount, elementCount, RoundingMode.UP);
        this.totalPages = IntStream.range(0, pages).boxed().collect(Collectors.toList());
        if (currentPage != null) {
            this.currentPage = pages / elementCount;
        } else {
            currentPage = 0;
        }
        this.elementCount = elementCount;
        this.sort = sort.name().toUpperCase();
        this.search = search;
    }
}
