package cz.cvut.kbss.ear.epoll.model;

import javax.persistence.*;
import java.util.List;

@Table
@Entity
public class Post extends AbstractModerated {
    @Basic(optional = false)
    @Column(nullable = false)
    private String text;

    @Basic(optional = false)
    @Column(nullable = false)
    private String title;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "post")
    private Poll poll;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PostComment> comments;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Photo> photos;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "post")
    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}