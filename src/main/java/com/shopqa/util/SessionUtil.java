package com.shopqa.util;

import jakarta.servlet.http.HttpSession;

public final class SessionUtil {
    private SessionUtil() {
    }

    public static boolean isLoggedIn(HttpSession session) {
        return session != null && session.getAttribute("userId") != null;
    }

    public static boolean isAdmin(HttpSession session) {
        return isLoggedIn(session) && "admin".equals(session.getAttribute("userRole"));
    }

    public static Integer getCurrentUserId(HttpSession session) {
        if (!isLoggedIn(session)) {
            return null;
        }
        Object value = session.getAttribute("userId");
        return value instanceof Integer ? (Integer) value : Integer.valueOf(value.toString());
    }

    public static void invalidate(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }
}

