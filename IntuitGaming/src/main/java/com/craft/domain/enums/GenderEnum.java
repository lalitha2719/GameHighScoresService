package com.craft.domain.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {
    FEMALE("FEMALE"),
    MALE("MALE");

    private final String value;

    GenderEnum(String value) {
        this.value = value;
    }

}
