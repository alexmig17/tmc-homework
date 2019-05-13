package com.epam.test.controller.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.test.dto.ProgramEventDto;
import com.epam.test.entity.ProgramEvent.ProgramEvents;
import com.epam.test.service.PersonService;
import com.epam.test.service.ProgramEventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/program/event")
@RequiredArgsConstructor
public class ProgramEventController {

    private final ProgramEventService programEventService;
    private final PersonService personService;

    @GetMapping("{programId}")
    public ProgramEventDto getProgramEvent(@PathVariable Integer programId) {
        return programEventService.get(programId);
    }

    @GetMapping
    public Page<ProgramEventDto> getProgramEvents(Pageable pageable) {
        return programEventService.getAll(pageable);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Integer id) {
        programEventService.delete(id);
    }

    @PutMapping("{programId}")
    public ProgramEventDto saveProduct(@PathVariable Integer programId) {
        ProgramEvents event = ProgramEvents.JOIN;
        ProgramEventDto programEventDto = new ProgramEventDto();
        Integer personId = personService.getPersonFormAuthContext().getId();
        programEventDto.setProgramEvent(event);
        programEventDto.setPersonId(personId);
        programEventDto.setProgramId(programId);
        ProgramEventDto dto = programEventService.update(programEventDto);
        return dto;
    }
}
