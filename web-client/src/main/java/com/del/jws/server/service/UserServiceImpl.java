package com.del.jws.server.service;

import com.del.jws.server.db.Meeting;
import com.del.jws.server.db.MeetingDao;
import com.del.jws.server.db.User;
import com.del.jws.server.db.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by dodolinel
 * date: 27.03.2017
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MeetingDao meetingDao;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User findByLogin(String sso) {
        return userDao.findByLogin(sso);
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
    public void updateUser(User user) {
        User entity = userDao.findById(user.getId());
        if (entity != null) {
            entity.setLogin(user.getLogin());
            if (!user.getPassword().equals(entity.getPassword())) {
                entity.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setEmail(user.getEmail());
            entity.setRole(user.getRole());
            if (!StringUtils.isEmpty(user.getLastIp())) {
                entity.setLastIp(user.getLastIp());
            }
        }
    }


    public void deleteUserByLogin(String login) {
        userDao.deleteByLogin(login);
    }

    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    public boolean isUserLoginUnique(Integer id, String login) {
        User user = findByLogin(login);
        return (user == null || ((id != null) && (user.getId().equals(id))));
    }

    public Meeting findMeetingById(long id) {
        return meetingDao.findById(id);
    }

    public List<Meeting> listActiveMeetings(Integer initiatorId) {
        if (initiatorId == null) {
            return meetingDao.listActive();
        }
        return meetingDao.listNotEnded(initiatorId);
    }

    public Meeting beginMeeting(int initiatorId) {
        return meetingDao.beginMeeting(initiatorId);
    }

    public Meeting endMeeting(long id) {
        return meetingDao.endMeeting(id);
    }

    public void saveMeeting(Meeting meeting) {
        meetingDao.save(meeting);
    }
}
