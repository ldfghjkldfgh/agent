package ru.mirea.agency.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mirea.agency.db.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    User findByEmail(String email);
}
