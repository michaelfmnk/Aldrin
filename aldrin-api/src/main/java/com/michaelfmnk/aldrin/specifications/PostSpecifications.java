package com.michaelfmnk.aldrin.specifications;

import com.michaelfmnk.aldrin.entities.Post;
import com.michaelfmnk.aldrin.entities.User;
import org.springframework.data.jpa.domain.Specification;


public class PostSpecifications {

    public static Specification<Post> getById(Integer postId) {
        return (root, criteriaQuery, cb) -> cb.equal(root.get(Post.POST_ID), postId);
    }

    public static Specification<Post> getByAuthorId(Integer authorId) {
        return (root, criteriaQuery, cb) -> cb.equal(root.get(Post.AUTHOR).get(User.USER_ID), authorId);
    }

}
