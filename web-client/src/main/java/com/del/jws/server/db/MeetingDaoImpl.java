package com.del.jws.server.db;

import com.del.jws.utils.Unchecked;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by DodolinEL
 * date: 15.05.2017
 */
@Repository("meetingDao")
public class MeetingDaoImpl extends AbstractDao<Long, Meeting> implements MeetingDao {

    static final Logger logger = Red5LoggerFactory.getLogger(MeetingDaoImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Meeting findById(long id) {
        return getByKey(id);
    }

    @Override
    public List<Meeting> listActive() {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.isNull("endDate"));
        crit.add(Restrictions.isNotNull("startDate"));
        return Unchecked.cast(crit.list());
    }

    @Override
    public List<Meeting> listNotEnded(Integer initiatorId) {
        cleanupMeetings(initiatorId);
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.isNull("endDate"));
        if (initiatorId != null) {
            crit.createAlias("initiator", "i").
                    add(Restrictions.eq("i.id", initiatorId));
        }
        return Unchecked.cast(crit.list());
    }

    private void cleanupMeetings(Integer initiatorId) {
        getSession().createQuery("delete from Meeting m " +
                "                   where m.endDate is null " +
                "                       and m.startDate is null " +
                "                       and m.initiator.id=:initiatorId").
                setParameter("initiatorId", initiatorId).executeUpdate();
    }

    @Override
    public void save(Meeting meeting) {
        update(meeting);
    }

    @Override
    public Meeting beginMeeting(int initiatorId) {
        Meeting meeting = new Meeting();
        User initiator = userDao.findById(initiatorId);
        meeting.setInitiator(initiator);
        meeting.setBeginDate(new Date());
        persist(meeting);
        return meeting;
    }

    @Override
    public Meeting endMeeting(long id) {
        Meeting meeting = findById(id);
        meeting.setEndDate(new Date());
        return meeting;
    }
}
