package com.michaelfmnk.aldrin.services;

import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.dtos.Pagination;
import com.michaelfmnk.aldrin.dtos.PostDto;
import com.michaelfmnk.aldrin.dtos.params.PageSortParams;
import com.michaelfmnk.aldrin.dtos.params.PageSortRequest;
import com.michaelfmnk.aldrin.entities.Comment;
import com.michaelfmnk.aldrin.entities.Post;
import com.michaelfmnk.aldrin.entities.User;
import com.michaelfmnk.aldrin.repositories.CommentRepository;
import com.michaelfmnk.aldrin.repositories.PostRepository;
import com.michaelfmnk.aldrin.repositories.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ConverterService converterService;
    private final CommentRepository commentRepository;

    //

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
        User user = userService.findUserById(userId);
        Post post = getValidPost(postId);

        user = post.getLikes()
                .stream()
                .filter(whoLiked -> Objects.equals(whoLiked.getUserId(), userId))
                .findFirst()
                .orElse(user);

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

    public Pagination<CommentDto> getCommentsForPost(Integer id, PageSortParams params) {
        Pageable pageable = new PageSortRequest(params.getOffset(), params.getLimit(),
                Comment.SORTING_INFO.getDefaultSort(false));
        failIfPostIdNotValid(id);
        Page<Comment> commentsPage = commentRepository.findByPostId(id, pageable);
        List<CommentDto> data = commentsPage.getContent()
                .stream()
                .map(converterService::toDto)
                .collect(Collectors.toList());
        return new Pagination<>(data, commentsPage.getTotalElements());
    }

    // TRASH
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

    private void failIfPostIdNotValid(Integer postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(format("no post was found with post_id=%s", postId)));
    }
}
