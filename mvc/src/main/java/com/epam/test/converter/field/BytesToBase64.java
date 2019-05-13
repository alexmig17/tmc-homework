package com.epam.test.converter.field;

import java.util.Base64;

public interface BytesToBase64 {

    default String Base64ToBytes(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    default byte[] bytesToBase64(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
