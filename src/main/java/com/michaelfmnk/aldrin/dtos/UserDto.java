package com.michaelfmnk.aldrin.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
public class UserDto implements Serializable {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
