package cz.cvut.kbss.ear.epoll.dto;

import javax.validation.constraints.Size;

public class CreateCommentDto {
    @Size(min = 1, max = 256)
    private String text;

    private Integer reply;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getReply() {
        return reply;
    }

    public void setReply(Integer reply) {
        this.reply = reply;
    }
}
