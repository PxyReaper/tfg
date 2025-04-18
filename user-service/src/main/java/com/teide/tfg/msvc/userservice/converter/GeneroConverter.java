package com.teide.tfg.msvc.userservice.converter;

import com.teide.tfg.msvc.userservice.enums.UserGenero;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;
@Converter(autoApply = true)
public class GeneroConverter implements AttributeConverter<UserGenero,String> {
    @Override
    public String convertToDatabaseColumn(UserGenero userGenero) {
        if(userGenero == null){
            return null;
        }
        return userGenero.getGenero();
    }

    @Override
    public UserGenero convertToEntityAttribute(String s) {
       if(s == null){
           return null;
       }
       return Stream.of(UserGenero.values())
               .filter(ug -> ug.getGenero().equals(s))
               .findFirst()
               .orElseThrow(IllegalArgumentException::new);
    }
}
