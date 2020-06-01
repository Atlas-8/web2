package service;

import model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {

    private static UserService userService;     // здесь храним единственный экземпляр класса
    private UserService () {};                  // запрещаем пользоваться публичным конструктором (но это не точно)

    /* хранилище данных - список зарегистрированных пользователей */
    private Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    /* счетчик id */
    private AtomicLong maxId = new AtomicLong(0);
    /* список авторизованных пользователей - участников текущей сессии */
    private Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());


    public static UserService getUserService() {    // реализуем синглтон
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public List<User> getAllUsers() {
        return new ArrayList<User>(dataBase.values());
    }

    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public boolean addUser(User user) {
        if (isExistsThisUser(user)) {
            return false;
        } else {
            dataBase.put(user.getId(), user);
        }
        return true;
    }

    public void deleteAllUser() {
        dataBase.clear();
    }

    public boolean isExistsThisUser(User user) {
        return dataBase.containsValue(user);
    }

    public List<User> getAllAuth() {
        return new ArrayList<User>(authMap.values());
    }

    public boolean authUser(User user) {
        if (dataBase.containsValue(user)) {
            authMap.put(user.getId(), user);
            return true;
        }
        return false;
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        return authMap.containsKey(id);
    }

}
