package cz.cvut.kbss.ear.epoll.dao;

import cz.cvut.kbss.ear.epoll.model.Video;
import org.springframework.stereotype.Repository;

@Repository
public class VideoDao extends BaseDao<Video> {
    public VideoDao() {
        super(Video.class);
    }
}
