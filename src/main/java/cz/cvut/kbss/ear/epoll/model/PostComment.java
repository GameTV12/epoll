package cz.cvut.kbss.ear.epoll.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table
@Entity
public class PostComment extends AbstractComment {
    @ManyToOne
    private PostComment repliedTo;

    @ManyToOne
    private Post post;

    public PostComment getRepliedTo() {
        return repliedTo;
    }

    public void setRepliedTo(PostComment repliedTo) {
        this.repliedTo = repliedTo;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}