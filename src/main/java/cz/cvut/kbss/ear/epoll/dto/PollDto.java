package cz.cvut.kbss.ear.epoll.dto;

import java.util.List;

public class PollDto {

    private Integer id;
    private boolean frozen;
    private List<VariantDto> variants;
    private Integer postId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public List<VariantDto> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantDto> variants) {
        this.variants = variants;
    }

    public Integer getpostId() {
        return postId;
    }

    public void setpostId(Integer postId) {
        this.postId = postId;
    }
}
