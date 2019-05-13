package com.epam.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.test.entity.ProgramEvent;
import com.epam.test.entity.ProgramEvent.ProgramEventId;

@Repository
public interface ProgramEventRepository extends JpaRepository<ProgramEvent, ProgramEventId> {

}
