package com.del.jws.server.db;

import java.util.List;

/**
 * Created by DodolinEL
 * date: 15.05.2017
 */
public interface MeetingDao {

    Meeting findById(long id);

    List<Meeting> listActive();

    List<Meeting> listNotEnded(Integer initiatorId);

    void save(Meeting meeting);

    Meeting beginMeeting(int initiatorId);

    Meeting endMeeting(long id);

}
