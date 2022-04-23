package cz.cvut.kbss.ear.epoll.model;

import javax.persistence.*;

@Table
@Entity
public class Video extends AbstractPersistable {
    @Basic(optional = false)
    @Column(nullable = false)
    private String url;

    @OneToOne
    private Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String source) {
        this.url = source;
    }
}