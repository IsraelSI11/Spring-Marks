package com.uniovi.sdi2223312spring.controllers;

import com.uniovi.sdi2223312spring.entities.Mark;
import com.uniovi.sdi2223312spring.entities.User;
import com.uniovi.sdi2223312spring.services.RolesService;
import com.uniovi.sdi2223312spring.services.SecurityService;
import com.uniovi.sdi2223312spring.services.UsersService;
import com.uniovi.sdi2223312spring.validators.SignUpFormValidator;
import com.uniovi.sdi2223312spring.validators.UserAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SignUpFormValidator signUpFormValidator;

    @Autowired
    private UserAddValidator userAddValidator;

    @Autowired
    private RolesService rolesService;

    @RequestMapping("/user/list")
    public String getListado(Model model, Pageable pageable, @RequestParam(value = "", required = false) String searchText) {
        Page<User> users = new PageImpl<User>(new LinkedList<User>());
        if(searchText!=null && !searchText.isEmpty()){
            users = usersService.searchUserByNameOrSurname(pageable,searchText);
        }else{
            users = new PageImpl<User>( usersService.getUsers());
        }
        model.addAttribute("usersList",users);
        return "user/list";
    }

    @RequestMapping("/user/list/update")
    public String updateList(Model model){
        model.addAttribute("usersList", usersService.getUsers() );
        return "user/list :: tableUsers";
    }

    @RequestMapping(value = "/user/add")
    public String getUser(Model model) {
        model.addAttribute("rolesList", rolesService.getRoles());
        model.addAttribute("user",new User());
        return "user/add";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute @Validated User user,Model model,BindingResult result) {
        userAddValidator.validate(user,result);
        if(result.hasErrors()){
            model.addAttribute("rolesList", rolesService.getRoles());
            model.addAttribute("user",user);
            return "user/add";
        }
        usersService.addUser(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }

    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@PathVariable Long id, @ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/details/" + id;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByDni(dni);
        model.addAttribute("markList", activeUser.getMarks());
        return "home";
    }
}
