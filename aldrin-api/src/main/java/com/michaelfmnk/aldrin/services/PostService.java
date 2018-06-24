package com.michaelfmnk.aldrin.services;

import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.dtos.PostDto;
import com.michaelfmnk.aldrin.dtos.params.PageSortParams;
import com.michaelfmnk.aldrin.dtos.params.PageSortRequest;
import com.michaelfmnk.aldrin.entities.Post;
import com.michaelfmnk.aldrin.entities.User;
import com.michaelfmnk.aldrin.repositories.CommentRepository;
import com.michaelfmnk.aldrin.repositories.PostRepository;
import com.michaelfmnk.aldrin.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    //private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ConverterService converterService;



    public PostDto findPostById(Integer id) {
        return postRepository.findById(id)
                .map(converterService::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format("no post was found with postId=%s", id)));

    }

    public PostDto updatePost(Integer id, PostDto postDto) {
        return postRepository.findById(id)
                .map(x -> {
                    x.merge(postDto);
                    return x;
                })
                .map(postRepository::save)
                .map(converterService::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format("no post was found with post_id=%s", id)));
    }

    public List<CommentDto> getCommentsForPost(Integer id, PageSortParams params) {
        return null;
    }

    public List<PostDto> getFeed(String name, PageSortParams params) {
      //  PageSortRequest pageable = new PageSortRequest(params.getOffset(), params.getLimit(), );
        return null;
    }

    public void deleteLikeForPost(Integer postId, Integer userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(format("no post was found with post_id=%s", postId)));
        post.getLikes().removeIf(x -> Objects.equals(userId, x.getUserId()));
        postRepository.save(post);
    }

    public void addLikeForPost(Integer postId, Integer userId) {
        User user = userRepository.getOne(userId);
        postRepository.findById(postId)
                .map(x -> {
                    x.getLikes().add(user);
                    return x;
                })
                .map(postRepository::save)
                .orElseThrow(() -> new EntityNotFoundException());
    }
}
