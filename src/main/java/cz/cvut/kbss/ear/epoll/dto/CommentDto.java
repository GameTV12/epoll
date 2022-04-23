package cz.cvut.kbss.ear.epoll.dto;

import java.time.LocalDateTime;

public class CommentDto {
    private Integer id;
    private String text;
    private LocalDateTime createdAt;
    private String createdBy;
    private Integer repliedTo;
    private Boolean visible;
    private String removedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getRepliedTo() {
        return repliedTo;
    }

    public void setRepliedTo(Integer repliedTo) {
        this.repliedTo = repliedTo;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getRemovedBy() {
        return removedBy;
    }

    public void setRemovedBy(String removedBy) {
        this.removedBy = removedBy;
    }
}
