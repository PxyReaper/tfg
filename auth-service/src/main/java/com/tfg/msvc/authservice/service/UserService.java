package com.tfg.msvc.authservice.service;

import com.tfg.msvc.authservice.clients.UserClient;
import com.tfg.msvc.authservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserClient userClient;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto  user = this.userClient.findByMail(username).getResult();
        List<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream().map( r ->  new SimpleGrantedAuthority("ROLE_".concat(r.getTipo())))
                .collect(Collectors.toList());
       authorities.addAll(
               user.getRoles().stream()
                       .flatMap(r -> r.getPermissions().stream())
                       .map(p -> new SimpleGrantedAuthority(p.getTipo())).toList()
       );

       return new User(user.getEmail(),user.getContraseña(),authorities);

    }
}
