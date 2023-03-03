package com.uniovi.sdi2223312spring.repositories;

import com.uniovi.sdi2223312spring.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByDni(String dni);

    @Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE LOWER(?1) OR LOWER(u.lastName) LIKE LOWER(?1))")
    Page<User> searchUserByNameOrSurname(Pageable pageable, String searchText);
}
