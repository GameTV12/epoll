package cz.cvut.kbss.ear.epoll.model;

import javax.persistence.*;
import java.util.List;

@Table
@Entity
public class Variant extends AbstractModerated {
    @Basic(optional = false)
    @Column(nullable = false)
    private int votes;

    @Basic(optional = false)
    @Column(nullable = false)
    private String text;

    @OneToMany(mappedBy = "variant", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<VariantComment> comments;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Poll poll;

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public List<VariantComment> getComments() {
        return comments;
    }

    public void setComments(List<VariantComment> comments) {
        this.comments = comments;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}