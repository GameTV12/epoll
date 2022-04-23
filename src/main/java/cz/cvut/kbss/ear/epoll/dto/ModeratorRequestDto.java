package cz.cvut.kbss.ear.epoll.dto;

import cz.cvut.kbss.ear.epoll.model.ModeratorRequestStatus;

public class ModeratorRequestDto {
    private Integer id;

    private String reason;

    private ModeratorRequestStatus status;

    private UserDto sender;

    private UserDto acceptor;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDto getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(UserDto acceptor) {
        this.acceptor = acceptor;
    }

    public UserDto getSender() {
        return sender;
    }

    public void setSender(UserDto sender) {
        this.sender = sender;
    }
}
