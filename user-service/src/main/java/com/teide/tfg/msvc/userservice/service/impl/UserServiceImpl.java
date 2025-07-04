package com.teide.tfg.msvc.userservice.service.impl;

import com.teide.tfg.msvc.userservice.mappers.UserEntityConverter;
import com.teide.tfg.msvc.userservice.dto.UserDto;
import com.teide.tfg.msvc.userservice.exception.UserNameNotFoundException;
import com.teide.tfg.msvc.userservice.model.RoleEntity;
import com.teide.tfg.msvc.userservice.model.UserEntity;
import com.teide.tfg.msvc.userservice.repository.RoleRepository;
import com.teide.tfg.msvc.userservice.repository.UserRepository;
import com.teide.tfg.msvc.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
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
        RoleEntity role = this.roleRepository.findRoleEntitiesByTipoIgnoreCase("USER");
        UserEntity userEntity = UserEntityConverter.convertUserDtoToUserEntity(userDto);
        userEntity.getRoles().add(role);
        if(userDto.getContraseña() != null) {
            userEntity.setContraseña(passwordEncoder.encode(userDto.getContraseña()));
        }


        this.userRepository.save(userEntity);
    }

    @Override
    public void update(UserDto userDto,String id) {
        Optional<UserEntity> userEntity = this.userRepository.findById(id);
        if(!userEntity.isPresent()) {
            throw new UserNameNotFoundException("Usuario con el id " + id + " no encontrado");
        }

        UserEntity userUpdate =  UserEntityConverter.convertUserDtoToUserEntity(userDto);
        this.userRepository.save(userUpdate);

    }

    @Override
    public void delete(String id) {
        Optional<UserEntity> userEntity = this.userRepository.findById(id);
        if(!userEntity.isPresent()) {
            throw new UserNameNotFoundException("Usuario con el id " + id + " no encontrado");
        }

        this.userRepository.deleteById(id);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        Optional<UserEntity> userEntity = this.userRepository.findUserEntityByEmail(email);
        if(!userEntity.isPresent()){
            throw  new UserNameNotFoundException("Usuario con el correo  "+ email + " no encontrado");

        }

        return UserEntityConverter.convertUserEntityToUserDto(userEntity.get());
    }

}
