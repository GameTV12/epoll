package cz.cvut.kbss.ear.epoll.dto;

import javax.validation.constraints.Size;
import java.util.List;

public class EditPostDto {
    @Size(min = 1, max = 64)
    private String title;

    @Size(min = 1, max = 1024)
    private String content;

    @Size(max = 9)
    private List<AttachedPhotoDto> photos;

    private AttachedVideoDto video;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<AttachedPhotoDto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<AttachedPhotoDto> photos) {
        this.photos = photos;
    }

    public AttachedVideoDto getVideo() {
        return video;
    }

    public void setVideo(AttachedVideoDto video) {
        this.video = video;
    }
}
