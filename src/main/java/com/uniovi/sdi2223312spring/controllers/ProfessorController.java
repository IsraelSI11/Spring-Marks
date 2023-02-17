package com.uniovi.sdi2223312spring.controllers;

import com.uniovi.sdi2223312spring.entities.Professor;
import com.uniovi.sdi2223312spring.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfessorController {

    @Autowired //Inyectar el servicio
    private ProfessorService professorService;

    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@ModelAttribute Professor professor) {
        professor.setId((long) (professorService.getProfessors().size()+1));
        professorService.addProfessor(professor);
        return "Profesor: " + professor.getNombre() + " " + professor.getApellidos()+ " añadido correctamente";
    }

    @RequestMapping(value = "/professor/list")
    public String getProfessors(@ModelAttribute Professor professor) {
        return professorService.getProfessors().toString();
    }

    @RequestMapping(value = "/professor/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute Professor professor) {
        Professor prof=professorService.getProfessor(professor.getId());
        if(prof!=null){
            prof.setDni(professor.getDni());
            prof.setNombre(professor.getNombre());
            prof.setApellidos(professor.getApellidos());
            prof.setCategoria(professor.getCategoria());
            return professorService.getProfessors().toString();
        }
        return "No hay un profesor con ese ID";
    }

    @RequestMapping("/professor/details/{id}")
    public String getDetail(@PathVariable Long id) {
        return professorService.getProfessor(id).toString();
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@PathVariable Long id){
        professorService.deleteProfessor(id);
        return professorService.getProfessors().toString();
    }

}
