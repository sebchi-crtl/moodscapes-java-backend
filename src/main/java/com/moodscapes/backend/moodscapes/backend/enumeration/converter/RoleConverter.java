package com.moodscapes.backend.moodscapes.backend.enumeration.converter;

import com.moodscapes.backend.moodscapes.backend.enumeration.Role;
import jakarta.persistence.AttributeConverter;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null)
            return null;
        return role.getAuthorities().toString();
    }

    @Override
    public Role convertToEntityAttribute(String code) {
        if (code == null)
            return null;
        return Stream
                .of(Role.values())
                .filter(role ->
                        role
                                .getAuthorities()
                                .equals(code)
                )
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
