package cz.cvut.kbss.ear.epoll.dto;

import cz.cvut.kbss.ear.epoll.model.ModeratorRequestStatus;

public class UpdateModeratorRequestStatusDto {
    private int id;
    private ModeratorRequestStatus newStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModeratorRequestStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(ModeratorRequestStatus newStatus) {
        this.newStatus = newStatus;
    }
}
