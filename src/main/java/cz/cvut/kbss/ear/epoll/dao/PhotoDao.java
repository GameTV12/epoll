package cz.cvut.kbss.ear.epoll.dao;

import cz.cvut.kbss.ear.epoll.model.Photo;
import org.springframework.stereotype.Repository;

@Repository
public class PhotoDao extends BaseDao<Photo> {
    public PhotoDao() {
        super(Photo.class);
    }
}
