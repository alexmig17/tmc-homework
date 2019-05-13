package com.epam.test.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.test.converter.UnitConverter;
import com.epam.test.dto.UnitDto;
import com.epam.test.entity.Employee;
import com.epam.test.entity.Unit;
import com.epam.test.exception.NotFoundException;
import com.epam.test.repository.EmployeeRepository;
import com.epam.test.repository.UnitRepository;
import com.epam.test.service.UnitService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final EmployeeRepository employeeRepository;
    private final UnitConverter unitConverter;

    @Override
    public UnitDto update(UnitDto dto) {
        Unit unit = unitConverter.toEntity(dto);
        return unitConverter.toDto(unitRepository.save(unit));
    }

    @Override
    public void delete(Integer id) {
        unitRepository.deleteById(id);
    }

    @Override
    public UnitDto get(Integer id) {
        return unitConverter.toDto(unitRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Page<UnitDto> getAll(Pageable pageable) {
        return unitRepository.findAll(pageable).map(unitConverter::toDto);
    }

    @Override
    public void assign(Integer unitId, Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(NotFoundException::new);
        Unit unit = unitRepository.findById(unitId).orElseThrow(NotFoundException::new);
        unit.getEmployees().add(employee);
        unitRepository.save(unit);
    }
}
