package ru.mirea.agency.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Plan {
    @Id
    @GeneratedValue
    private Long id;

    private final String name;
}
