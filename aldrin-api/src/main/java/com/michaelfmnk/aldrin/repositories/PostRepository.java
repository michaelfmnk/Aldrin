package com.michaelfmnk.aldrin.repositories;

import com.michaelfmnk.aldrin.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
