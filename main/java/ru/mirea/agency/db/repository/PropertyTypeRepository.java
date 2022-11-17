package ru.mirea.agency.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mirea.agency.db.model.PropertyType;

public interface PropertyTypeRepository extends CrudRepository<PropertyType, Long> {
}
