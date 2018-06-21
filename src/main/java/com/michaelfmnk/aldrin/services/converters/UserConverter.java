package com.michaelfmnk.aldrin.services.converters;

import com.michaelfmnk.aldrin.dtos.UserDto;
import com.michaelfmnk.aldrin.entities.User;
import com.michaelfmnk.aldrin.validation.IfNullReturnNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Service
public class UserConverter {

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

    public List<UserDto> toDto(List<User> entities) {
        return emptyIfNull(entities).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
