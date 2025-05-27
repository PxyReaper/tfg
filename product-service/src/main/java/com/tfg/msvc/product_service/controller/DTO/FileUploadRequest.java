package com.tfg.msvc.product_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadRequest {
    private byte[] file;
    private String fileName;
}
