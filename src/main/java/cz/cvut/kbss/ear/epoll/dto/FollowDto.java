package cz.cvut.kbss.ear.epoll.dto;

import javax.validation.constraints.NotEmpty;

public class FollowDto {
    @NotEmpty
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
