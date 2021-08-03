package com.ucv.codetech.model;

import java.util.Arrays;

public enum Role {
    STUDENT,
    INSTRUCTOR;

    public static Role getByName(String name) {
        return Arrays
                .stream(values())
                .filter(role -> name.equals(role.name()))
                .findFirst()
                .orElseThrow(() -> new EnumConstantNotPresentException(Role.class, name));
    }
}
