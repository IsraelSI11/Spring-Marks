package com.uniovi.sdi2223312spring.repositories;

import com.uniovi.sdi2223312spring.entities.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository  extends CrudRepository<Professor, Long> {
}
