package cz.cvut.kbss.ear.epoll.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table
@Entity
public class VariantComment extends AbstractComment {
    @ManyToOne
    private VariantComment repliedTo;

    @ManyToOne
    private Variant variant;

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public VariantComment getRepliedTo() {
        return repliedTo;
    }

    public void setRepliedTo(VariantComment repliedTo) {
        this.repliedTo = repliedTo;
    }
}