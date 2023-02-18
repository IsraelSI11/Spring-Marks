package com.uniovi.sdi2223312spring.repositories;

import com.uniovi.sdi2223312spring.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
}
