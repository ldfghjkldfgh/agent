package ru.mirea.agency.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mirea.agency.db.model.Plan;

import java.util.List;

public interface PlanRepository extends CrudRepository<Plan, Long> {
    List<Plan> findAll();
}
