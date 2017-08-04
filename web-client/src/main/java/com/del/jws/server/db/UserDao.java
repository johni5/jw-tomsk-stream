package com.del.jws.server.db;

import java.util.List;

/**
 * Created by dodolinel
 * date: 27.03.2017
 */
public interface UserDao {

    User findById(int id);

    User findByLogin(String login);

    void save(User user);

    void deleteByLogin(String login);

    List<User> findAllUsers();

}
