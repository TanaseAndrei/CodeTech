package com.ucv.codetech.model;

import java.util.Arrays;

public enum Difficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;

    public static Difficulty getByName(String name) {
        return Arrays
                .stream(values())
                .filter(difficulty -> name.equals(difficulty.name()))
                .findFirst()
                .orElseThrow(() -> new EnumConstantNotPresentException(Difficulty.class, name));
    }
}
