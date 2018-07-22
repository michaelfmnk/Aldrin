package com.michaelfmnk.aldrin.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

@UtilityClass
public class CodeGenerator {
    public static Integer DEFAULT_CODE_LEN = 7;

    public static String generateCode(int len) {
        return RandomStringUtils.randomAlphabetic(len).toUpperCase();
    }

    public static String generateCode() {
        return RandomStringUtils.randomAlphabetic(DEFAULT_CODE_LEN).toUpperCase();
    }
}
