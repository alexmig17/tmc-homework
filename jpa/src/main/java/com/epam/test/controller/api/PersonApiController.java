package com.epam.test.controller.api;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.test.dto.PersonDto;
import com.epam.test.service.PersonService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonApiController {

    private final PersonService personService;

    @GetMapping
    public Page<PersonDto> getPerson(Pageable pageable) {
        return personService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public PersonDto getPerson(@PathVariable Integer id) {
        return personService.get(id);
    }

    @PostMapping
    public PersonDto savePerson(@Valid @RequestBody PersonDto person) {
        return personService.update(person);
    }

    @DeleteMapping("{id}")
    public void deleteDto(@PathVariable Integer id) {
        personService.delete(id);
    }

    @GetMapping(params = "basket")
    public List<PersonDto> findPersonWithBasket() {
        return personService.findPersonsWithBasket();
    }

    @GetMapping(params = "registeredDate")
    public List<PersonDto> findPersonsRegisteredOnDay(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate registeredDate) {
        return personService.findPersonsRegisteredOnDay(registeredDate);
    }

    @GetMapping(params = "orderCount")
    public List<PersonDto> findPersonWithMiOrders(@RequestParam Integer orderCount) {
        return personService.findPersonWithMiOrders(orderCount);
    }
}
