package com.michaelfmnk.aldrin.services.converters;

import com.michaelfmnk.aldrin.dtos.PostDto;
import com.michaelfmnk.aldrin.entities.Post;
import com.michaelfmnk.aldrin.validation.IfNullReturnNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Service
@AllArgsConstructor
public class PostConverter {
    private final CommentConverter commentConverter;
    private final UserConverter userConverter;

    @IfNullReturnNull
    public PostDto toDto(Post entity) {
        return PostDto.builder()
                .postId(entity.getPostId())
                .title(entity.getTitle())
                .date(entity.getDate())
                .author(userConverter.toDto(entity.getAuthor()))
                .build();
    }

    public List<PostDto> toDto(List<Post> entities) {
        return emptyIfNull(entities).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
