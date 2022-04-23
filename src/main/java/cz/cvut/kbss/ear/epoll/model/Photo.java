package cz.cvut.kbss.ear.epoll.model;

import javax.persistence.*;

@Table
@Entity
public class Photo extends AbstractPersistable {
    @Basic(optional = false)
    @Column(nullable = false)
    private String url;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false)
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