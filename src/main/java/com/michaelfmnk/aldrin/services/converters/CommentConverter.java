package com.michaelfmnk.aldrin.services.converters;

import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.entities.Comment;
import com.michaelfmnk.aldrin.validation.IfNullReturnNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Service
public class CommentConverter {
    private CommentConverter() {}

//    @IfNullReturnNull
//    public CommentDto toDto(Comment entity) {
//        return CommentDto.builder()
//                .commentId(entity.getCommentId())
//                .content(entity.getContent())
//                .date(entity.getDate())
//                .postId(entity.getPostId())
//                .userId(entity.getUserId())
//                .build();
//    }

//    public List<CommentDto> toDto(List<Comment> entities){
//        return emptyIfNull(entities).stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }
}
