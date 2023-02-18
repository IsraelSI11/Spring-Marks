package com.uniovi.sdi2223312spring.controllers;

import com.uniovi.sdi2223312spring.entities.Professor;
import com.uniovi.sdi2223312spring.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfessorController {

    @Autowired //Inyectar el servicio
    private ProfessorService professorService;

    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@ModelAttribute Professor professor) {
        professor.setId((long) (professorService.getProfessors().size()+1));
        professorService.addProfessor(professor);
        return "redirect:/professor/list";
    }

    @RequestMapping(value = "/professor/add")
    public String getProfessor(@ModelAttribute Professor professor) {
        return "professor/add";
    }

    @RequestMapping(value = "/professor/list")
    public String getList(Model model) {
        model.addAttribute("professors", professorService.getProfessors());
        return "professor/list";
    }

    @RequestMapping(value = "/professor/edit")
    public String getEdit() {
        return "professor/edit";
    }

    @RequestMapping(value = "/professor/edit", method = RequestMethod.POST)
    public String setEdit(@ModelAttribute Professor professor) {
        Professor prof=professorService.getProfessor(professor.getId());
        if(prof!=null){
            professorService.addProfessor(professor);
            return "redirect:/professor/details/"+professor.getId();
        }
        return "professor/list";
    }

    @RequestMapping("/professor/details/{id}")
    public String getDetail(Model model,@PathVariable Long id) {
        model.addAttribute("professor", professorService.getProfessor(id));
        return "professor/details";
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@PathVariable Long id){
        professorService.deleteProfessor(id);
        return "redirect:/professor/list";
    }

}
