package cz.cvut.kbss.ear.epoll.service;

import cz.cvut.kbss.ear.epoll.dao.UserDao;
import cz.cvut.kbss.ear.epoll.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private final UserDao userDao;

    @Autowired
    public CurrentUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDao.findByUsername(userDetails.getUsername());
    }
}
