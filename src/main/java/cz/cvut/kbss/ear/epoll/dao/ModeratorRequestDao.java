package cz.cvut.kbss.ear.epoll.dao;

import cz.cvut.kbss.ear.epoll.model.ModeratorRequest;
import cz.cvut.kbss.ear.epoll.model.ModeratorRequestStatus;
import cz.cvut.kbss.ear.epoll.model.ModeratorRequest_;
import cz.cvut.kbss.ear.epoll.model.User_;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ModeratorRequestDao extends BaseDao<ModeratorRequest> {

    public ModeratorRequestDao() {
        super(ModeratorRequest.class);
    }

    public ModeratorRequest getWaitingModeratorRequestByUserId(int userId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<ModeratorRequest> query = criteriaBuilder.createQuery(ModeratorRequest.class);
        Root<ModeratorRequest> r = query.from(ModeratorRequest.class);

        query.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(r.join(ModeratorRequest_.createdBy).get(User_.id), userId),
                        criteriaBuilder.equal(r.get(ModeratorRequest_.status), ModeratorRequestStatus.WAITING))
        );

        try {
            return em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<ModeratorRequest> getAllModeratorRequestsByStatus(ModeratorRequestStatus status) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<ModeratorRequest> query = criteriaBuilder.createQuery(ModeratorRequest.class);
        Root<ModeratorRequest> r = query.from(ModeratorRequest.class);

        query.where(
                criteriaBuilder.equal(r.get(ModeratorRequest_.status), status)
        );

        query.orderBy(
                criteriaBuilder.desc(r.get(ModeratorRequest_.createdAt))
        );

        return em.createQuery(query).getResultList();
    }

    public List<ModeratorRequest> getAllModeratorRequestsByUserId(int userId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<ModeratorRequest> query = criteriaBuilder.createQuery(ModeratorRequest.class);
        Root<ModeratorRequest> r = query.from(ModeratorRequest.class);

        query.where(
                criteriaBuilder.equal(r.join(ModeratorRequest_.createdBy).get(User_.id), userId)
        );

        query.orderBy(
                criteriaBuilder.desc(r.get(ModeratorRequest_.createdAt))
        );

        return em.createQuery(query).getResultList();
    }
}
