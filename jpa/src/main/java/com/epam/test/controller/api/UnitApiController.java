package com.epam.test.controller.api;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.test.dto.IdDto;
import com.epam.test.dto.UnitDto;
import com.epam.test.service.UnitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/unit")
public class UnitApiController {

    private final UnitService unitService;

    @GetMapping("{id}")
    public UnitDto getUnitById(@PathVariable Integer id) {
        return unitService.get(id);
    }

    @GetMapping
    public Page<UnitDto> getAll(Pageable pageable) {
        return unitService.getAll(pageable);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        unitService.delete(id);
    }

    @PostMapping
    public UnitDto save(@Valid @RequestBody UnitDto unit) {
        unit.setEmployees(null);
        return unitService.update(unit);
    }

    @PostMapping("/{id}/employee")
    public void assign(@PathVariable Integer id, @RequestBody IdDto employeeId) {
        unitService.assign(id, employeeId.getId());
    }
}
