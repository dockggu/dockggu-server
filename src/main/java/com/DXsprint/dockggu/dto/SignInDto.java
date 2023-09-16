package com.DXsprint.dockggu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    @NotBlank   // validation gradle에서 추가해야해
    private String userEmail;
    private String userPassword;
}
