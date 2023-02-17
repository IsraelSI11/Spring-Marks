package com.uniovi.sdi2223312spring.services;

import com.uniovi.sdi2223312spring.entities.Professor;
import com.uniovi.sdi2223312spring.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professors;
    public List<Professor> getProfessors() {
        List<Professor> professor = new ArrayList<Professor>();
        professors.findAll().forEach(professor::add);
        return professor;
    }
    public Professor getProfessor(Long id) {
        return professors.findById(id).get();
    }
    public void addProfessor(Professor professor) {
        professors.save(professor);
    }
    public void deleteProfessor(Long id) {
        professors.deleteById(id);
    }

}
