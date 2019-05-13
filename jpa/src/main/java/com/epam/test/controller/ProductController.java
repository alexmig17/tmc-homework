package com.epam.test.controller;

import static com.epam.test.dto.ProductDto.MODEL;
import static com.epam.test.dto.ProductDto.MODEL_LIST;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.test.dto.ProductDto;
import com.epam.test.dto.ProductFormDto;
import com.epam.test.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("{id}")
    public String product(@PathVariable Integer id, Model model) {
        model.addAttribute(MODEL, productService.get(id));
        return "product";
    }

    @GetMapping
    public String productList(Model model,
                              Pageable pageable,
                              @RequestParam(defaultValue = "", required = false) String search) {
        model.addAttribute("searchPlaceholder", search);
        if (isBlank(search)) {
            model.addAttribute(MODEL_LIST, productService.getAll(pageable));

        } else {
            model.addAttribute(MODEL_LIST, productService.search(search, pageable));
        }
        return "products";
    }

    @GetMapping(path = "{id}", params = "edit")
    public String productEdit(@PathVariable Integer id, Model model) {
        ProductDto product = productService.get(id);
        model.addAttribute(MODEL, product);
        return "productForm";
    }

    @GetMapping(path = "{id}", params = "delete")
    public String productDelete(@PathVariable Integer id) {
        productService.delete(id);
        return format("redirect:/%s", "product");
    }

    @GetMapping(params = "new")
    public String productNew(Model model) {
        model.addAttribute(MODEL, new ProductDto());
        return "productForm";
    }

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    public String productUpdate(@Valid ProductFormDto form) {
        Integer id = productService.update(form);
        return format("redirect:/product/%d", id);
    }
}
