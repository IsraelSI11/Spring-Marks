package com.uniovi.sdi2223312spring.services;

import com.uniovi.sdi2223312spring.entities.Professor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ProfessorService {

    private CopyOnWriteArrayList<Professor> professors = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init() {
        professors.add(new Professor(1L,"dni1","nombre1","apellidos1","categoria1"));
        professors.add(new Professor(2L,"dni2","nombre2","apellidos2","categoria2"));
    }

    public List<Professor> getProfessors() {
        return new ArrayList<>(professors);
    }
    public Professor getProfessor(Long id) {
        for(Professor professor : professors){
            if(professor.getId().equals(id)){
                return professor;
            }
        }
        return null;
    }
    public void addProfessor(Professor professor) {
        professors.add(professor);
    }
    public void deleteProfessor(Long id) {
        for(Professor professor : professors){
            if(professor.getId().equals(id)){
                professors.remove(professor);
            }
        }
    }

}
