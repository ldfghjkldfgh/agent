package ru.mirea.agency.db.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.mirea.agency.db.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    List<Post> findAll();
    Optional<Post> findById(Integer postId);
    List<Post> findAllByOwner(Long id);
}
