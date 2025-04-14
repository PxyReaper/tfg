package com.teide.tfg.msvc.userservice.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserGenero {
    MASCULINO("masculino"),
    FEMENINO("femenino");
    private String genero;
    private UserGenero(String genero) {
        this.genero = genero;
    }
    @JsonValue
    public String getGenero() {
        return genero;
    }
}
