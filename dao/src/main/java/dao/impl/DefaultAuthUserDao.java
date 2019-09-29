package dao.impl;

import app.entities.AuthUser;
import app.entities.Role;
import dao.AuthUserDao;

import java.util.HashMap;
import java.util.Map;

public class DefaultAuthUserDao implements AuthUserDao {
    Map<String, AuthUser> userByLogin;

    public DefaultAuthUserDao() {
        this.userByLogin = new HashMap<>();
        this.userByLogin.putIfAbsent("admin",
                new AuthUser("admin", "admin", Role.ADMINISTRATOR, null));
        this.userByLogin.putIfAbsent("user",
                new AuthUser("user", "user", Role.USER, null));
    }

    private static volatile AuthUserDao instance;

    public static AuthUserDao getInstance() {
        AuthUserDao localInstance = instance;
        if (localInstance == null) {
            synchronized (AuthUserDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DefaultAuthUserDao();
                }
            }
        }
        return localInstance;
    }

    @Override
    public AuthUser getByLogin(String login) {
        return userByLogin.get(login);
    }

    @Override
    public void saveAuthUser(AuthUser user) {
        userByLogin.putIfAbsent(user.getLogin(), user);
    }
}
