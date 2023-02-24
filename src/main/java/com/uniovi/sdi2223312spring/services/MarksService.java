package com.uniovi.sdi2223312spring.services;

import com.uniovi.sdi2223312spring.entities.Mark;
import com.uniovi.sdi2223312spring.entities.User;
import com.uniovi.sdi2223312spring.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class MarksService {
    @Autowired
    private MarksRepository marksRepository;

    /* Inyección de dependencias basada en constructor*/
    private final HttpSession httpSession;

    @Autowired
    public MarksService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public Page<Mark> getMarks(Pageable pageable) {
        Page<Mark> marks = marksRepository.findAll(pageable);
        return marks;
    }
    public Mark getMark(Long id){
        return marksRepository.findById(id).get();
    }
    public void addMark(Mark mark) {
        // Si en Id es null le asignamos el último + 1 de la Pagea
        marksRepository.save(mark);
    }

    public void setMarkResend(boolean revised, Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        Mark mark = marksRepository.findById(id).get();
        if(mark.getUser().getDni().equals(dni) ) {
            marksRepository.updateResend(revised, id);
        }
    }

    public Page<Mark> getMarksForUser(Pageable pageable,User user) {
        Page<Mark> marks = new PageImpl<>(new LinkedList<Mark>());
        if (user.getRole().equals("ROLE_STUDENT")) {
            marks = marksRepository.findAllByUser(pageable,user);
        }
        if (user.getRole().equals("ROLE_PROFESSOR")) {
            marks = getMarks(pageable);
        }
        return marks;
    }

    public void deleteMark(Long id) {
        marksRepository.deleteById(id);
    }

    public Page<Mark> searchMarksByDescriptionAndNameForUser(Pageable pageable,String searchText, User user) {
        Page<Mark> marks = new PageImpl<Mark>(new LinkedList<Mark>());
        searchText = "%"+searchText+"%";
        if (user.getRole().equals("ROLE_STUDENT")) {
            marks = marksRepository.searchByDescriptionNameAndUser(pageable,searchText, user);
        }
        if (user.getRole().equals("ROLE_PROFESSOR")) {
            marks = marksRepository.searchByDescriptionAndName(pageable,searchText);
        }
        return marks;
    }

}
