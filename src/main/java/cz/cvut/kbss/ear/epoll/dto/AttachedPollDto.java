package cz.cvut.kbss.ear.epoll.dto;

import javax.validation.constraints.Size;
import java.util.List;

public class AttachedPollDto {

    @Size(min = 1, max = 10)
    private List<AttachedPollVariantDto> variants;

    private boolean frozen;

    public List<AttachedPollVariantDto> getVariants() {
        return variants;
    }

    public void setVariants(List<AttachedPollVariantDto> variants) {
        this.variants = variants;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }
}
