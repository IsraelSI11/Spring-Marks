package com.uniovi.sdi2223312spring.controllers;

import com.uniovi.sdi2223312spring.entities.Mark;
import com.uniovi.sdi2223312spring.services.MarksService;
import com.uniovi.sdi2223312spring.services.UsersService;
import com.uniovi.sdi2223312spring.validators.MarksValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MarksController {

    @Autowired //Inyectar el servicio
    private MarksService marksService;

    @Autowired
    private MarksValidator marksValidator;

    @Autowired
    private UsersService usersService;

    @RequestMapping("/mark/list")
    public String getList(Model model) {
        model.addAttribute("markList", marksService.getMarks());
        return "mark/list";

    }
    @RequestMapping("/mark/list/update")
    public String updateList(Model model){
        model.addAttribute("markList", marksService.getMarks() );
        return "mark/list :: tableMarks";
    }

    @RequestMapping(value="/mark/add")
    public String getMark(Model model){
        model.addAttribute("mark",new Mark());
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/add";
    }

    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@ModelAttribute Mark mark,Model model, BindingResult result) {
        marksValidator.validate(mark,result);
        if(result.hasErrors()){
            model.addAttribute("mark",mark);
            model.addAttribute("usersList",usersService.getUsers());
            return "mark/add";
        }
        marksService.addMark(mark);
        return "redirect:/mark/list";
    }

    @RequestMapping("/mark/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        return "mark/details";
    }

    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id){
        marksService.deleteMark(id);
        return "redirect:/mark/list";
    }

    @RequestMapping(value = "/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/edit";
    }

    @RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Mark mark,Model model, @PathVariable Long id,BindingResult result){
        marksValidator.validate(mark,result);
        if(result.hasErrors()){
            model.addAttribute("usersList", usersService.getUsers());
            return "mark/edit";
        }
        Mark m = marksService.getMark(id);
        m.setUser(mark.getUser());
        m.setDescription(mark.getDescription());
        m.setScore(mark.getScore());
        m.setId(mark.getId());
        marksService.addMark(m);
        return "redirect:/mark/details/"+id;
    }

}
