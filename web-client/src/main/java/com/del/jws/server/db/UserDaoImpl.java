package com.del.jws.server.db;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dodolinel
 * date: 27.03.2017
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    static final Logger logger = Red5LoggerFactory.getLogger(UserDaoImpl.class);

    public User findById(int id) {
        return getByKey(id);
    }

    public User findByLogin(String login) {
        logger.info("Login : {}", login);
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("login", login));
        return (User) crit.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        return (List<User>) criteria.list();
    }

    public void save(User user) {
        persist(user);
    }

    public void deleteByLogin(String login) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("login", login));
        User user = (User) crit.uniqueResult();
        delete(user);
    }


}
