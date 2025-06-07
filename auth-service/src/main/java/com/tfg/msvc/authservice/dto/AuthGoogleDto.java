package com.tfg.msvc.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthGoogleDto {
    private String email;
    private String family_name;
    private String given_name;
}
