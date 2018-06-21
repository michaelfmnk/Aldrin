package com.michaelfmnk.aldrin.services;

import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.dtos.PostDto;
import com.michaelfmnk.aldrin.dtos.params.PageSortParams;
import com.michaelfmnk.aldrin.dtos.params.PageSortRequest;
import com.michaelfmnk.aldrin.entities.Post;
import com.michaelfmnk.aldrin.repositories.CommentRepository;
import com.michaelfmnk.aldrin.repositories.PostRepository;
import com.michaelfmnk.aldrin.services.converters.PostConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    //private final CommentRepository commentRepository;
    private final PostConverter postConverter;



    public PostDto findPostById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("no post was found with postId=%s", id)));
        return postConverter.toDto(post);
    }

    public PostDto updatePost(Integer id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("no post was found with post_id=%s", id)));
        post.merge(postDto);
        post = postRepository.save(post);
        return postConverter.toDto(post);
    }

    public List<CommentDto> getCommentsForPost(Integer id, PageSortParams params) {
        return null;
    }

    public List<PostDto> getFeed(String name, PageSortParams params) {
      //  PageSortRequest pageable = new PageSortRequest(params.getOffset(), params.getLimit(), );
        return null;
    }
}
