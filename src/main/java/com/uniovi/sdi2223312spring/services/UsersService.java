package com.uniovi.sdi2223312spring.services;

import com.uniovi.sdi2223312spring.entities.User;
import com.uniovi.sdi2223312spring.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecurityService securityService;

    @PostConstruct
    public void init() {
    }
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }
    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }
    public void addUser(User user) {
        if(user.getPassword()!=null){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        usersRepository.save(user);
    }
    public User getUserByDni(String dni) {
        return usersRepository.findByDni(dni);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

}
