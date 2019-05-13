package com.epam.test.service;

import com.epam.test.dto.UnitDto;

public interface UnitService extends CrudService<UnitDto, Integer> {

    void assign(Integer unitId, Integer employeeId);
}
