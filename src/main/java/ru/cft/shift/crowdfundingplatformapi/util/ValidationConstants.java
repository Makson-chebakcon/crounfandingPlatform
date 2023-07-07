package ru.cft.shift.crowdfundingplatformapi.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationConstants {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9\\._]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+$";
    public static final String NAME_REGEX = "^[А-Я][а-я]*$";
}
