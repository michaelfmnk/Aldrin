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
import com.michaelfmnk.aldrin.utils.ConverterService;
import com.michaelfmnk.aldrin.utils.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final ConverterService converterService;
    private final CommentRepository commentRepository;
    private final MessagesService messagesService;

    public PostDto findPostById(Integer postId) {
        return converterService.toDto(getValidPost(postId));
    }

    public PostDto updatePost(Integer postId, PostDto postDto) {
        Post post = getValidPost(postId);
        post.update(postDto);
        post = postRepository.save(post);
        return converterService.toDto(post);
    }

    public void addLikeForPost(Integer postId, Integer userId) {
        User user = userService.findValidUserById(userId);
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

    public CommentDto addCommentToPost(Integer postId, CommentDto commentDto) {
        Comment comment = converterService.toEntity(commentDto);
        if (Objects.nonNull(comment.getRepliedCommentId()) &&
                commentRepository.existsById(comment.getRepliedCommentId())) {
            throw new EntityNotFoundException(messagesService.getMessage("comment.not.found"));
        }
        failIfPostIdNotValid(postId);
        comment = commentRepository.save(comment);
        return converterService.toDto(comment);
    }

    public Pagination<CommentDto> getCommentsForPost(Integer id, PageSortParams params) {
        Pageable pageable = PageSortRequest
                .builder()
                .limit(params.getLimit())
                .offset(params.getOffset())
                .sort(Comment.SORTING_INFO.getDefaultSort(params.isAsc()))
                .build();

        failIfPostIdNotValid(id);
        Page<Comment> commentsPage = commentRepository.findByPostId(id, pageable);
        List<CommentDto> data = commentsPage.getContent()
                .stream()
                .map(converterService::toDto)
                .collect(Collectors.toList());
        return new Pagination<>(data, commentsPage.getTotalElements());
    }

    private Post getValidPost(Integer postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("post.not.found")));
    }

    private void failIfPostIdNotValid(Integer postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("post.not.found")));
    }

    @Transactional
    public void deletePost(Integer postId) {
        Post postToDelete = getValidPost(postId);
        postRepository.delete(postToDelete);
    }

    // TRASH
    public List<PostDto> getFeed(String name, PageSortParams params) {
        List<Post> posts = postRepository.findPostByAuthor_FollowersUserId(1);
        return posts.stream()
                .map(converterService::toDto)
                .collect(Collectors.toList());
    }

}
