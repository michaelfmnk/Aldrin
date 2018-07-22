package com.michaelfmnk.aldrin.services;

import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.dtos.PostDto;
import com.michaelfmnk.aldrin.dtos.UserDto;
import com.michaelfmnk.aldrin.entities.Comment;
import com.michaelfmnk.aldrin.entities.Post;
import com.michaelfmnk.aldrin.entities.User;
import com.michaelfmnk.aldrin.validation.IfNullReturnNull;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Service
public class ConverterService {

    @IfNullReturnNull
    public CommentDto toDto(Comment entity) {
        return CommentDto.builder()
                .id(entity.getCommentId())
                .content(entity.getContent())
                .date(entity.getDate())
                .postId(entity.getPostId())
                .userId(entity.getUserId())
                .build();
    }

    @IfNullReturnNull
    public PostDto toDto(Post entity) {
        return PostDto.builder()
                .id(entity.getPostId())
                .title(entity.getTitle())
                .date(entity.getDate())
                .authorId(entity.getAuthor().getUserId())
                .comments(
                        emptyIfNull(entity.getComments())
                                .stream()
                                .map(this::toDto)
                                .collect(Collectors.toList())
                )
                .likes(emptyIfNull(entity.getLikes()).size())
                .build();
    }

    @IfNullReturnNull
    public UserDto toDto(User entity) {
        return UserDto.builder()
                .id(entity.getUserId())
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .build();
    }

    @IfNullReturnNull
    public Comment toEntity(CommentDto dto) {
        return Comment.builder()
                .commentId(dto.getId())
                .repliedCommentId(dto.getRepliedCommentId())
                .content(dto.getContent())
                .userId(dto.getUserId())
                .postId(dto.getPostId())
                .build();
    }
}
