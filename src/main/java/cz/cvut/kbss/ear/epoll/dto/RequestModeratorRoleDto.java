package cz.cvut.kbss.ear.epoll.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RequestModeratorRoleDto {
    @NotEmpty
    @Size(min = 12, max = 256)
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
