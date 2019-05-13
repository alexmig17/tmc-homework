package com.epam.test.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.test.converter.ProgramEventConverter;
import com.epam.test.dto.ProgramEventDto;
import com.epam.test.entity.Person;
import com.epam.test.entity.ProgramEvent;
import com.epam.test.entity.ProgramEvent.ProgramEventId;
import com.epam.test.exception.NotFoundException;
import com.epam.test.repository.ProgramEventRepository;
import com.epam.test.service.PersonService;
import com.epam.test.service.ProgramEventService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramEventServiceImpl implements ProgramEventService {

    private final ProgramEventRepository programEventRepository;
    private final PersonService personService;
    private final ProgramEventConverter programEventConverter;

    @Override
    public ProgramEventDto update(ProgramEventDto dto) {
        ProgramEvent programEvent = programEventConverter.toEntity(dto);
        ProgramEvent savedEvent = programEventRepository.save(programEvent);
        return programEventConverter.toDto(savedEvent);
    }

    @Override
    public void delete(Integer programId) {
        Person person = personService.getPersonFormAuthContext();
        programEventRepository.deleteById(new ProgramEventId(person.getId(), programId));
    }

    @Override
    public ProgramEventDto get(Integer programId) {
        Person person = personService.getPersonFormAuthContext();
        ProgramEvent programEvent = programEventRepository.findById(new ProgramEventId(person.getId(), programId)).orElseThrow(NotFoundException::new);
        return programEventConverter.toDto(programEvent);
    }

    @Override
    public Page<ProgramEventDto> getAll(Pageable pageable) {
        return programEventRepository.findAll(pageable).map(programEventConverter::toDto);
    }
}
