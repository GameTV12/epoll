package cz.cvut.kbss.ear.epoll.dao;

import cz.cvut.kbss.ear.epoll.model.User;
import cz.cvut.kbss.ear.epoll.model.User_;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserDao extends BaseDao<User> {

    public UserDao() {
        super(User.class);
    }

    public User findByUsername(String username) {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
            Root<User> u = query.from(User.class);

            query.where(
                    criteriaBuilder.equal(u.get(User_.username), username)
            );

            return em.createQuery(query).getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
}
