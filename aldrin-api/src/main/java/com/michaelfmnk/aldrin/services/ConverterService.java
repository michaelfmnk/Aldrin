package com.michaelfmnk.aldrin.services;

import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.dtos.PostDto;
import com.michaelfmnk.aldrin.dtos.UserDto;
import com.michaelfmnk.aldrin.entities.Comment;
import com.michaelfmnk.aldrin.entities.Post;
import com.michaelfmnk.aldrin.entities.User;
import com.michaelfmnk.aldrin.validation.IfNullReturnNull;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {

    @IfNullReturnNull
    public CommentDto toDto(Comment entity) {
        return CommentDto.builder()
                .commentId(entity.getCommentId())
                .content(entity.getContent())
                .date(entity.getDate())
                .postId(entity.getPostId())
                .userId(entity.getUserId())
                .build();
    }

    @IfNullReturnNull
    public PostDto toDto(Post entity) {
        return PostDto.builder()
                .postId(entity.getPostId())
                .title(entity.getTitle())
                .date(entity.getDate())
                .author(toDto(entity.getAuthor()))
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


}
