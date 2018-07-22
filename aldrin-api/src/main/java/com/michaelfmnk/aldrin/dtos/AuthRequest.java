package com.michaelfmnk.aldrin.dtos;

import com.michaelfmnk.aldrin.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Email
    private String login;
    @ValidPassword
    private String password;
}
