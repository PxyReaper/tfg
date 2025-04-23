package com.teide.tfg.msvc.userservice.controller;

import com.teide.tfg.msvc.userservice.dto.ResponseDTO;
import com.teide.tfg.msvc.userservice.dto.UserDto;
import com.teide.tfg.msvc.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseDTO<UserDto>> findById(@PathVariable String id) {
        UserDto userDto = userService.findById(id);
        ResponseDTO<UserDto> responseDTO = new ResponseDTO<>(userDto, HttpStatus.OK.value(),"Usuario encontrado exitosamente");
        return ResponseEntity.ok(responseDTO);

    }
    @GetMapping("/mail/{mail}")
    public ResponseEntity<ResponseDTO<UserDto>> findByMail(@PathVariable String mail){
        UserDto userDto = userService.findUserByEmail(mail);
        ResponseDTO<UserDto> response = new ResponseDTO<>(userDto,HttpStatus.OK.value(),"Usuario encontrado exitosamente");
        return  ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<UserDto>> save(@RequestBody UserDto userDto) throws URISyntaxException {
        userService.save(userDto);
        ResponseDTO<UserDto> response = new ResponseDTO<>(userDto,HttpStatus.CREATED.value(), "Usuario registrado exittosamente");
        return ResponseEntity.created( new URI("/api/users")).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDto>> update(@RequestBody UserDto userDto,@PathVariable String id)  {
        userService.update(userDto,id);
        ResponseDTO<UserDto> response = new ResponseDTO<>(null,HttpStatus.OK.value(),"Usuario actualizado exittosamente");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDto>> delete(@PathVariable String id) {
        userService.delete(id);
        ResponseDTO<UserDto> response = new ResponseDTO<>(null,HttpStatus.OK.value(),"Usuario removido exittosamente");
        return ResponseEntity.ok(response);
    }

}
