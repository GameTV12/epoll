package cz.cvut.kbss.ear.epoll.dao;


import cz.cvut.kbss.ear.epoll.model.Post;
import org.springframework.stereotype.Repository;

@Repository
public class PostDao extends ModeratedDao<Post> {
    public PostDao() {
        super(Post.class);
    }
}
