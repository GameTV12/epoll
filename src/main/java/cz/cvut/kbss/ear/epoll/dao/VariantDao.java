package cz.cvut.kbss.ear.epoll.dao;

import cz.cvut.kbss.ear.epoll.model.Variant;
import org.springframework.stereotype.Repository;

@Repository
public class VariantDao extends BaseDao<Variant> {
    public VariantDao() {
        super(Variant.class);
    }
}
