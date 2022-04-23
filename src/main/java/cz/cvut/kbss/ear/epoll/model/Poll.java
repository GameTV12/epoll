package cz.cvut.kbss.ear.epoll.model;

import javax.persistence.*;
import java.util.List;

@Table
@Entity
public class Poll extends AbstractPersistable {
    @Basic(optional = false)
    @Column(nullable = false)
    private boolean frozen;

    @OneToMany(mappedBy = "poll", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Variant> variants;

    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    private Post post;

    @ManyToMany
    @JoinTable(
            name = "voters",
            joinColumns = @JoinColumn(name = "poll_id"),
            inverseJoinColumns = @JoinColumn(name = "voter_id"))
    private List<User> voters;

    public Post getPost() {
        return post;
    }

    public void setPost(Post sourcePost) {
        this.post = sourcePost;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public List<User> getVoters() {
        return voters;
    }

    public void setVoters(List<User> voters) {
        this.voters = voters;
    }
}