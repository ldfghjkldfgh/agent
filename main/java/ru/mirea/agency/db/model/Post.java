package ru.mirea.agency.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private Property property;

    @NonNull
    @ManyToOne
    private Plan plan;

    @Column(length = 50)
    private String name;

    @Column(length = 5000)
    private String description;

    @NonNull
    private Double price;

    private Long pledge = 0L;

    private Boolean allowedChildren;

    private Boolean allowedAnimals;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdateAt;

    @ManyToOne
    private User owner;
}
