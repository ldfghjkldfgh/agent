package ru.mirea.agency.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mirea.agency.db.model.Property;

public interface PropertyRepository extends CrudRepository<Property, Long> {
}
