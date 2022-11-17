package ru.mirea.agency.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
public class Property {
    @Id
    @GeneratedValue
    private Long id;

    private Long lat;
    private Long lon;

    @NonNull
    @ManyToOne
    private User owner;

    @NonNull
    @ManyToOne
    private PropertyType type;
    private Double area;
}
