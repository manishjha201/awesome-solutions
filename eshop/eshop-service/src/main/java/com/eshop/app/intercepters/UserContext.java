package com.eshop.app.intercepters;

import com.eshop.app.common.entities.rdbms.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserContext {
    private static final ThreadLocal<String> userFullName = new ThreadLocal<>();
    private static final ThreadLocal<User> user = new ThreadLocal<>();

    public static void setUserFullName(final String userFullName) {
        log.info("setting user full name : {} to thread context ", userFullName);
        UserContext.userFullName.set(userFullName);
    }

    public static String getUserFullName() {
        return UserContext.userFullName.get();
    }

    public static void setUserDetails(final User user) {
        log.info("setting user details : {} to thread context ", user);
        UserContext.user.set(user);
    }

    public static User getUserDetail() {
        return UserContext.user.get();
    }

    public static void removeUserFullName() {
        UserContext.userFullName.remove();
    }

    public static void removeUserDetails(final User user) {
        UserContext.user.remove();
    }

}
