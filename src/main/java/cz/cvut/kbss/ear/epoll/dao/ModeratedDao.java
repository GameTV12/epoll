package cz.cvut.kbss.ear.epoll.dao;

import cz.cvut.kbss.ear.epoll.model.*;
import cz.cvut.kbss.ear.epoll.model.User_;
import cz.cvut.kbss.ear.epoll.model.AbstractModerated_;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class ModeratedDao<T extends AbstractModerated> extends BaseDao<T> {
    protected ModeratedDao(Class<T> type) {
        super(type);
    }

    public List<T> findAllVisible() {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
            Root<T> u = query.from(type);

            query.where(
                    criteriaBuilder.equal(u.get(AbstractModerated_.visible), true),
                    criteriaBuilder.equal(u.join(AbstractModerated_.createdBy).get(User_.visible), true)
            );

            return em.createQuery(query).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<T> findAllVisibleByUsername(String username) {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
            Root<T> u = query.from(type);

            query.where(
                    criteriaBuilder.equal(u.join(AbstractModerated_.createdBy).get(User_.username), username),
                    criteriaBuilder.equal(u.join(AbstractModerated_.createdBy).get(User_.visible), true),
                    criteriaBuilder.equal(u.get(AbstractModerated_.visible), true)
            );

            return em.createQuery(query).getResultList();

        } catch (NoResultException e) {
            return null;
        }
    }
}
