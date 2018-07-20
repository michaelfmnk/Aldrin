package com.michaelfmnk.aldrin.services;

import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.dtos.PostDto;
import com.michaelfmnk.aldrin.dtos.params.PageSortParams;
import com.michaelfmnk.aldrin.entities.Comment;
import com.michaelfmnk.aldrin.entities.Post;
import com.michaelfmnk.aldrin.entities.User;
import com.michaelfmnk.aldrin.repositories.CommentRepository;
import com.michaelfmnk.aldrin.repositories.PostRepository;
import com.michaelfmnk.aldrin.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ConverterService converterService;
    private final CommentRepository commentRepository;

    // READY
    public PostDto findPostById(Integer id) {
        return postRepository.findById(id)
                .map(converterService::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format("no post was found with id=%s", id)));
    }

    public PostDto updatePost(Integer postId, PostDto postDto) {
        Post post = getValidPost(postId);
        post.merge(postDto);
        post = postRepository.save(post);
        return converterService.toDto(post);
    }

    public void addLikeForPost(Integer postId, Integer userId) {
        User user = userRepository.getOne(userId);
        Post post = getValidPost(postId);
        post.getLikes().add(user);
        postRepository.save(post);
    }

    public void deleteLikeForPost(Integer postId, Integer userId) {
        Post post = getValidPost(userId);
        post.getLikes().removeIf(x -> Objects.equals(userId, x.getUserId()));
        postRepository.save(post);
    }

    public PostDto addCommentToPost(Integer postId, Integer userId, CommentDto commentDto) {
        Comment comment = converterService.toEntity(commentDto, postId, userId);
        if (commentRepository.existsById(comment.getRepliedCommentId())) {
            throw new EntityNotFoundException(""); //todo message provider
        }
        Post post = getValidPost(postId);
        post.getComments().add(comment);
        post = postRepository.save(post);
        return converterService.toDto(post);
    }

    // TRASH
    public List<CommentDto> getCommentsForPost(Integer id, PageSortParams params) {
        return null;
    }

    public List<PostDto> getFeed(String name, PageSortParams params) {
        List<Post> posts = postRepository.findPostByAuthor_FollowersUserId(1);
        return posts.stream()
                .map(converterService::toDto)
                .collect(Collectors.toList());
    }

    private Post getValidPost(Integer postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(format("no post was found with post_id=%s", postId)));
    }
}
