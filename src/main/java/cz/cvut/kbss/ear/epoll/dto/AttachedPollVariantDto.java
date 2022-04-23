package cz.cvut.kbss.ear.epoll.dto;

import javax.validation.constraints.NotEmpty;

public class AttachedPollVariantDto {

    @NotEmpty
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
