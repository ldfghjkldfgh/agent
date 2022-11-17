package ru.mirea.agency.service.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.mirea.agency.db.model.*;
import ru.mirea.agency.db.converter.PostConverter;
import ru.mirea.agency.db.dto.PostForm;
import ru.mirea.agency.db.dto.PropertyForm;
import ru.mirea.agency.db.repository.PlanRepository;
import ru.mirea.agency.db.repository.PostRepository;
import ru.mirea.agency.db.repository.PropertyTypeRepository;
import ru.mirea.agency.service.PostService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PlanRepository planRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final PropertyServiceImpl propertyServiceImpl;

    public PostServiceImpl(PostRepository postRepository,
                           PropertyServiceImpl propertyServiceImpl,
                           PlanRepository planRepository,
                           PropertyTypeRepository propertyTypeRepository) {
        this.postRepository = postRepository;
        this.propertyServiceImpl = propertyServiceImpl;
        this.planRepository = planRepository;
        this.propertyTypeRepository = propertyTypeRepository;
    }

    public Post createPost(PropertyForm propertyForm, PostForm postForm, User user) {
        Property property = propertyServiceImpl.createProperty(propertyForm);
        if (property == null) {
            return null;
        }
        Optional<Plan> plan = planRepository.findById(postForm.getPlanId());
        if (plan.isEmpty()) {
            return null;
        }
        Post post = PostConverter.formToPost(postForm);
        post.setProperty(property);
        post.setPlan(plan.get());
        LocalDateTime timeNow = LocalDateTime.now();
        post.setCreatedAt(timeNow);
        post.setLastUpdateAt(timeNow);
        post.setOwner(user);
        postRepository.save(post);
        return post;
    }

    public Post updatePost(Long postId, Post post) {
        if (!postId.equals(post.getId())) {
            return null;
        }
        Optional<Post> previousPostOpt = postRepository.findById(postId);
        if (previousPostOpt.isEmpty()) {
            return null;
        }
        Post previousPost = previousPostOpt.get();
        if (!previousPost.getProperty().getId().equals(post.getProperty().getId())) {
            return null;
        }
        Optional<PropertyType> incomingPropertyType = propertyTypeRepository.findById(
                post.getProperty()
                        .getType()
                        .getId()
        );
        if (incomingPropertyType.isEmpty()) {
            return null;
        }
        previousPost.getProperty().setArea(post.getProperty().getArea());
        previousPost.getProperty().setType(post.getProperty().getType());
        previousPost.setProperty(propertyServiceImpl.updateProperty(previousPost.getProperty()));
        Optional<Plan> plan = planRepository.findById(post.getPlan().getId());
        if (plan.isEmpty()) {
            return null;
        }
        previousPost.setPlan(plan.get());
        previousPost.setName(post.getName());
        previousPost.setDescription(post.getDescription());
        previousPost.setPrice(post.getPrice());
        previousPost.setPledge(post.getPledge());
        previousPost.setAllowedAnimals(post.getAllowedAnimals());
        previousPost.setAllowedChildren(post.getAllowedChildren());
        previousPost.setLastUpdateAt(LocalDateTime.now());
        return postRepository.save(previousPost);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll(new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
            }
        });
    }

    @Override
    public List<Post> findAllByOwner(Long id) {
        return postRepository.findAllByOwner(id);
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId);
    }

    public List<Post> searchThroughFilter(String partOfText, Double priceMin, Double priceMax,
                                          Double pledgeMin, Double pledgeMax,
                                          Boolean allowedChildren, Boolean allowedAnimals,
                                          Long planId, Long propertyId, Boolean sortPriceDesc, Boolean sortPriceAsc,
                                          Boolean sortNew, Long ownerId) {
        return postRepository.findAll(new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (partOfText != null) {
                    predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + partOfText + "%"),
                            criteriaBuilder.like(root.get("description"), "%" + partOfText + "%")));
                }
                if (priceMin != null) {
                    predicates.add(criteriaBuilder.ge(root.get("price"), priceMin));
                }
                if (priceMax != null) {
                    predicates.add(criteriaBuilder.le(root.get("price"), priceMax));
                }
                if (pledgeMin != null) {
                    predicates.add(criteriaBuilder.ge(root.get("pledge"), pledgeMin));
                }
                if (pledgeMax != null) {
                    predicates.add(criteriaBuilder.le(root.get("pledge"), pledgeMax));
                }
                if (allowedChildren != null) {
                    predicates.add(criteriaBuilder.equal(root.get("allowedChildren"), allowedChildren));
                }
                if (allowedAnimals != null) {
                    predicates.add(criteriaBuilder.equal(root.get("allowedAnimals"), allowedAnimals));
                }
                if (planId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("plan").get("id"), planId));
                }
                if (propertyId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("property").get("id"), propertyId));
                }
                if (sortNew != null && sortNew) {
                    query.orderBy(criteriaBuilder.desc(root.get("lastUpdateAt")));
                } else if (sortPriceDesc != null && sortPriceDesc) {
                    query.orderBy(criteriaBuilder.desc(root.get("price")));
                } else if (sortPriceAsc != null && sortPriceAsc) {
                    query.orderBy(criteriaBuilder.asc(root.get("price")));
                }
                if (ownerId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("owner"), ownerId));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }
}
