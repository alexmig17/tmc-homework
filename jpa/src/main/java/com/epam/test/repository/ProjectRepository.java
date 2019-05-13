package com.epam.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.test.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
