package ru.mirea.agency.service;

import ru.mirea.agency.db.dto.PostForm;
import ru.mirea.agency.db.dto.PropertyForm;
import ru.mirea.agency.db.model.Post;
import ru.mirea.agency.db.model.User;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> findAll();
    List<Post> findAllByOwner(Long id);
    Optional<Post> findById(Long postId);
    Post createPost(PropertyForm propertyForm, PostForm postForm, User user);
    Post updatePost(Long postId, Post post);
    public List<Post> searchThroughFilter(String partOfText, Double priceMin, Double priceMax,
                                          Double pledgeMin, Double pledgeMax,
                                          Boolean allowedChildren, Boolean allowedAnimals,
                                          Long planId, Long propertyId, Boolean sortPriceDesc, Boolean sortPriceAsc,
                                          Boolean sortNew, Long ownerId);
    void delete(Long postId);
}
