package cz.cvut.kbss.ear.epoll.dao;

import cz.cvut.kbss.ear.epoll.model.User_;
import cz.cvut.kbss.ear.epoll.model.VariantComment;
import cz.cvut.kbss.ear.epoll.model.VariantComment_;
import cz.cvut.kbss.ear.epoll.model.Variant_;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class VariantCommentDao extends ModeratedDao<VariantComment> {
    public VariantCommentDao() {
        super(VariantComment.class);
    }

    public List<VariantComment> findAllReplies(int commentId) {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<VariantComment> query = criteriaBuilder.createQuery(VariantComment.class);
            Root<VariantComment> u = query.from(VariantComment.class);

            query.where(
                    criteriaBuilder.equal(u.get(VariantComment_.repliedTo).get(VariantComment_.id), commentId),
                    criteriaBuilder.equal(u.get(VariantComment_.repliedTo).get(VariantComment_.visible), true),
                    criteriaBuilder.equal(u.get(VariantComment_.repliedTo).get(VariantComment_.createdBy).get(User_.visible), true),
                    criteriaBuilder.equal(u.get(VariantComment_.createdBy).get(User_.visible), true),
                    criteriaBuilder.equal(u.get(VariantComment_.visible), true)
            );

            query.orderBy(
                    criteriaBuilder.asc(u.get(VariantComment_.createdAt))
            );

            return em.createQuery(query).getResultList();

        } catch (NoResultException e) {
            return null;
        }
    }

    public List<VariantComment> findAllVisibleByVariant(int variantId) {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<VariantComment> query = criteriaBuilder.createQuery(VariantComment.class);
            Root<VariantComment> u = query.from(VariantComment.class);

            query.where(
                    criteriaBuilder.equal(u.join(VariantComment_.variant).get(Variant_.id), variantId),
                    criteriaBuilder.equal(u.get(VariantComment_.visible), true),
                    criteriaBuilder.equal(u.join(VariantComment_.createdBy).get(User_.visible), true)
            );

            query.orderBy(
                    criteriaBuilder.asc(u.get(VariantComment_.createdAt))
            );

            return em.createQuery(query).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
