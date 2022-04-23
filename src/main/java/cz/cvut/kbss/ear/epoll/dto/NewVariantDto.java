package cz.cvut.kbss.ear.epoll.dto;

import javax.validation.constraints.NotEmpty;

public class NewVariantDto {
    @NotEmpty
    private String text;

    private Integer poll_id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(Integer poll_id) {
        this.poll_id = poll_id;
    }
}
