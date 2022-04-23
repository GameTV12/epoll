package cz.cvut.kbss.ear.epoll.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractModerated extends AbstractAuditable {
    @Basic
    @Column
    private Boolean visible = true;
    @ManyToOne
    private User removedBy = null;

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean removed) {
        this.visible = removed;
    }

    public User getRemovedBy() {
        return removedBy;
    }

    public void setRemovedBy(User removedBy) {
        this.removedBy = removedBy;
    }
}
