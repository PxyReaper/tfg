package com.teide.tfg.msvc.userservice.service;

import com.teide.tfg.msvc.userservice.dto.UserDto;

public interface UserService {
    public UserDto findById(String id);
    public void save(UserDto userDto);
    public void update(UserDto userDto,String id);
    public void delete(String id);
}
