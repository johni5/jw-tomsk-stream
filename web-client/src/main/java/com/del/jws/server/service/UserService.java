package com.del.jws.server.service;


import com.del.jws.server.db.Meeting;
import com.del.jws.server.db.User;

import java.util.List;

/**
 * Created by dodolinel
 * date: 27.03.2017
 */
public interface UserService {

    User findByLogin(String login);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserByLogin(String login);

    List<User> findAllUsers();

    boolean isUserLoginUnique(Integer id, String login);

    Meeting findMeetingById(long id);

    List<Meeting> listActiveMeetings(Integer initiatorId);

    Meeting beginMeeting(int initiatorId);

    Meeting endMeeting(long id);

    void saveMeeting(Meeting meeting);

}
