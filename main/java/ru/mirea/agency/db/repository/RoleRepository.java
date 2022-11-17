package ru.mirea.agency.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mirea.agency.db.model.Role;

public interface RoleRepository extends CrudRepository<Role, String> {
}
