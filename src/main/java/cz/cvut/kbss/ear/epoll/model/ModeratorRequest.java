package cz.cvut.kbss.ear.epoll.model;

import javax.persistence.*;

@Table
@Entity
public class ModeratorRequest extends AbstractAuditable {
    @Basic(optional = false)
    @Column(nullable = false)
    private String reason;

    @ManyToOne
    private User acceptor;

    @Enumerated(EnumType.STRING)
    private ModeratorRequestStatus status;

    public User getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(User acceptor) {
        this.acceptor = acceptor;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ModeratorRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ModeratorRequestStatus status) {
        this.status = status;
    }
}