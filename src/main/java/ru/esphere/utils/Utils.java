package ru.esphere.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public final class Utils {
    private Utils(){}

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static Optional<String> currentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getName);
    }
}
