package ru.mirea.agency.db.converter;

import ru.mirea.agency.db.model.Post;
import ru.mirea.agency.db.dto.PostForm;

public class PostConverter {
    public static Post formToPost(PostForm postForm) {
        Post post = new Post();
        post.setName(postForm.getName());
        post.setDescription(postForm.getDescription());
        post.setPrice(postForm.getPrice());
        post.setPledge(postForm.getPledge());
        post.setAllowedAnimals(postForm.getAllowedAnimals());
        post.setAllowedChildren(postForm.getAllowedChildren());
        return post;
    }
}
