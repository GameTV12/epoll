package cz.cvut.kbss.ear.epoll.dao;

import cz.cvut.kbss.ear.epoll.model.Poll;
import org.springframework.stereotype.Repository;

@Repository
public class PollDao extends BaseDao<Poll> {
    public PollDao() {
        super(Poll.class);
    }
}
