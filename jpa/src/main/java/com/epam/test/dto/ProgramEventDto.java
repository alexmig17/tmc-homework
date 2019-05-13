package com.epam.test.dto;

import java.time.LocalDate;

import com.epam.test.entity.ProgramEvent.ProgramEvents;

import lombok.Data;

@Data
public class ProgramEventDto implements Dto {

    private Integer programId;
    private Integer personId;
    private ProgramEvents programEvent;
    private LocalDate eventDate;
}
