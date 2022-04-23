package cz.cvut.kbss.ear.epoll.model;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractComment extends AbstractModerated {

    @Basic(optional = false)
    @Column(nullable = false)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}