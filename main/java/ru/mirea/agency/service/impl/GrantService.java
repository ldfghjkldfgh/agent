package ru.mirea.agency.service.impl;

import org.springframework.stereotype.Component;
import ru.mirea.agency.db.model.Post;
import ru.mirea.agency.db.model.Role;
import ru.mirea.agency.db.model.User;
import ru.mirea.agency.db.repository.UserRepository;
import ru.mirea.agency.service.PostService;

import java.util.Objects;
import java.util.Optional;

@Component
public class GrantService {

    private final UserRepository userRepository;
    private final PostService postService;

    public GrantService(UserRepository userRepository, PostService postService) {
        this.userRepository = userRepository;
        this.postService = postService;
    }

    public boolean canUserManagePost(Long userId, Long postId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return false;
        }
        Optional<Post> post = postService.findById(postId);

        if (post.isEmpty()) {
            return false;
        }

        if (Objects.equals(post.get().getOwner().getId(), userId)) {
            return true;
        }
        for (Role role : user.get().getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    public boolean canUserManageUser(Long userIdActor, Long userIdTarget) {

        Optional<User> user1 = userRepository.findById(userIdActor);
        Optional<User> user2 = userRepository.findById(userIdTarget);

        if (user1.isEmpty() || user2.isEmpty()) {
            return false;
        }
        if (userIdActor.equals(userIdTarget)) {
            return true;
        }
        for (Role role : user1.get().getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }
}
