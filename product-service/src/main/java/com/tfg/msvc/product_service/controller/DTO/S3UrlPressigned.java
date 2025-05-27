package com.tfg.msvc.product_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class S3UrlPressigned {
    private String directory;
    private List<String> urls;
}
