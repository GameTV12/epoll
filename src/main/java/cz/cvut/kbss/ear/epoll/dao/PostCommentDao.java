package cz.cvut.kbss.ear.epoll.dao;

import cz.cvut.kbss.ear.epoll.model.*;
import cz.cvut.kbss.ear.epoll.model.PostComment_;
import cz.cvut.kbss.ear.epoll.model.Post_;
import cz.cvut.kbss.ear.epoll.model.User_;
import cz.cvut.kbss.ear.epoll.model.AbstractComment_;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PostCommentDao extends ModeratedDao<PostComment> {
    public PostCommentDao() {
        super(PostComment.class);
    }

    public List<PostComment> findAllReplies(int commentId) {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<PostComment> query = criteriaBuilder.createQuery(PostComment.class);
            Root<PostComment> u = query.from(PostComment.class);

            query.where(
                    criteriaBuilder.equal(u.get(PostComment_.repliedTo).get(AbstractComment_.id), commentId),
                    criteriaBuilder.equal(u.get(PostComment_.repliedTo).get(AbstractComment_.visible), true),
                    criteriaBuilder.equal(u.get(PostComment_.repliedTo).get(AbstractComment_.createdBy).get(User_.visible), true),
                    criteriaBuilder.equal(u.join(PostComment_.createdBy).get(User_.visible), true),
                    criteriaBuilder.equal(u.get(PostComment_.visible), true)
            );

            query.orderBy(
                    criteriaBuilder.asc(u.get(PostComment_.createdAt))
            );

            return em.createQuery(query).getResultList();

        } catch (NoResultException e) {
            return null;
        }
    }

    public List<PostComment> findAllVisibleByPost(int postId) {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<PostComment> query = criteriaBuilder.createQuery(PostComment.class);
            Root<PostComment> u = query.from(PostComment.class);

            query.where(
                    criteriaBuilder.equal(u.join(PostComment_.post).get(Post_.id), postId),
                    criteriaBuilder.equal(u.get(PostComment_.visible), true),
                    criteriaBuilder.equal(u.join(PostComment_.createdBy).get(User_.visible), true)
            );

            query.orderBy(
                    criteriaBuilder.asc(u.get(PostComment_.createdAt))
            );

            return em.createQuery(query).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
