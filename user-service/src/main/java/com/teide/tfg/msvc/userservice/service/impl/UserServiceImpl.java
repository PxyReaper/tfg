package com.teide.tfg.msvc.userservice.service.impl;

import com.teide.tfg.msvc.userservice.converter.UserEntityConverter;
import com.teide.tfg.msvc.userservice.dto.UserDto;
import com.teide.tfg.msvc.userservice.exception.UserNameNotFoundException;
import com.teide.tfg.msvc.userservice.model.UserEntity;
import com.teide.tfg.msvc.userservice.repository.UserRepository;
import com.teide.tfg.msvc.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Transactional
    @Override
    public UserDto findById(String id) {
        Optional<UserEntity>  findUser = this.userRepository.findById(id);
        if(findUser.isEmpty()) {
            throw new UserNameNotFoundException("Usuario con el id " + id + " no encontrado");
        }
        return UserEntityConverter.convertUserEntityToUserDto(findUser.get());
    }

    @Override
    public void save(UserDto userDto) {

    }

    @Override
    public void update(UserDto userDto) {

    }

    @Override
    public void delete(UserDto userDto) {

    }
}
