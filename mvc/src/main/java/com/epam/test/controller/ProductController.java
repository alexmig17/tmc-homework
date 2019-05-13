package com.epam.test.controller;

import static com.epam.test.dto.ProductDto.MODEL;
import static com.epam.test.dto.ProductDto.MODEL_LIST;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import javax.validation.constraints.Min;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.test.dto.ProductDto;
import com.epam.test.dto.ProductFormDto;
import com.epam.test.enums.Sort;
import com.epam.test.service.ProductService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("{id}")
    public String product(@PathVariable Integer id, Model model) {
        model.addAttribute(MODEL, productService.get(id));
        return "product";
    }

    @GetMapping
    public String productList(Model model, @RequestParam(defaultValue = "0", required = false) @Min(0) Integer offset,
                              @RequestParam(defaultValue = "5", required = false) @Min(5) Integer elementCount,
                              @RequestParam(defaultValue = "ASC", required = false) Sort sort,
                              @RequestParam(defaultValue = "", required = false) String search) {
        if (isBlank(search)) {
            model.addAttribute(MODEL_LIST, productService.getAll(offset, elementCount, sort));
        } else {
            model.addAttribute(MODEL_LIST, productService.search(offset, elementCount, sort, search));
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
        String page;
        if (productService.deleteProduct(id)) {
            page = "product";
        } else {
            page = "error";
        }
        return format("redirect:/%s", page);
    }

    @GetMapping(params = "new")
    public String productNew(Model model) {
        model.addAttribute(MODEL, new ProductDto());
        return "productForm";
    }

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    public String productUpdate(ProductFormDto form) {
        Integer id = productService.update(form);
        return format("redirect:/product/%d", id);
    }
}
