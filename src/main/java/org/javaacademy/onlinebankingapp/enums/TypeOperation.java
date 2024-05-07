package org.javaacademy.onlinebankingapp.enums;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TypeOperation {
    INCOME, OUTCOME
}
