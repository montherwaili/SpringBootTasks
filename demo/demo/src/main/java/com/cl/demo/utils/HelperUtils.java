package com.cl.demo.utils;

public class HelperUtils {


    public static <T> T compare(T oldVal, T newVal) {

        if (newVal == null) {
            return oldVal;
        }

        if (newVal instanceof String && ((String) newVal).trim().isEmpty()) {
            return oldVal;
        }
        return newVal;
    }
}
