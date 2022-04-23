package cz.cvut.kbss.ear.epoll.security;

import cz.cvut.kbss.ear.epoll.dao.UserDao;
import cz.cvut.kbss.ear.epoll.model.Role;
import cz.cvut.kbss.ear.epoll.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username '" + username + "' was not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user.isVisible()) {
            switch (user.getRole()) {
                case ADMIN:
                    authorities.add(new SimpleGrantedAuthority(Role.ADMIN.name()));
                    break;
                case MODERATOR:
                    authorities.add(new SimpleGrantedAuthority(Role.MODERATOR.name()));
                    break;
                case USER:
                    authorities.add(new SimpleGrantedAuthority(Role.USER.name()));
                    break;
            }
        }

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }
}
