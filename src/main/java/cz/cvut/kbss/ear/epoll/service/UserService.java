package cz.cvut.kbss.ear.epoll.service;

import cz.cvut.kbss.ear.epoll.dao.UserDao;
import cz.cvut.kbss.ear.epoll.dto.FollowDto;
import cz.cvut.kbss.ear.epoll.dto.SignInDto;
import cz.cvut.kbss.ear.epoll.dto.SignUpDto;
import cz.cvut.kbss.ear.epoll.dto.UserDto;
import cz.cvut.kbss.ear.epoll.exception.HttpStatusException;
import cz.cvut.kbss.ear.epoll.model.Role;
import cz.cvut.kbss.ear.epoll.model.User;
import cz.cvut.kbss.ear.epoll.security.JwtTokenProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProviderService jwtTokenProviderService;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserService currentUserService;

    @Autowired
    public UserService(UserDao userDao, AuthenticationManager authenticationManager, JwtTokenProviderService jwtTokenProviderService, PasswordEncoder passwordEncoder, CurrentUserService currentUserService) {
        this.userDao = userDao;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProviderService = jwtTokenProviderService;
        this.passwordEncoder = passwordEncoder;
        this.currentUserService = currentUserService;
    }

    @Transactional
    public UserDto signUp(SignUpDto signUpDto) {
        User newUser = new User();
        newUser.setUsername(signUpDto.getUsername());
        newUser.setPassword(signUpDto.getPassword());
        newUser.encodePassword(passwordEncoder);
        newUser.setEmail(signUpDto.getEmail());
        newUser.setPhoneNumber(signUpDto.getPhoneNumber());
        newUser.setAge(signUpDto.getAge());
        newUser.setAvatarUrl("https://www.gravatar.com/avatar/default.jpg");
        newUser.setRole(Role.USER);
        newUser.setVisible(true);

        userDao.persist(newUser);

        UserDto userDto = new UserDto();
        userDto.setId(newUser.getId());
        userDto.setUsername(newUser.getUsername());
        userDto.setAvatarUrl(newUser.getAvatarUrl());
        userDto.setPhoneNumber(newUser.getPhoneNumber());
        userDto.setRole(newUser.getRole());
        userDto.setAge(newUser.getAge());
        userDto.setVisible(newUser.isVisible());

        return userDto;
    }

    @Transactional
    public UserDto signIn(SignInDto signInDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));
        } catch (AuthenticationException e) {
            throw new HttpStatusException("Invalid username/password", HttpStatus.BAD_REQUEST);
        }

        User user = userDao.findByUsername(signInDto.getUsername());

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        userDto.setAge(user.getAge());
        userDto.setVisible(user.isVisible());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setAvatarUrl(user.getAvatarUrl());
        userDto.setAccessToken(jwtTokenProviderService.createToken(user.getUsername(), user.getRole()));

        return userDto;
    }

    @Transactional
    public UserDto getCurrentUser() {
        User user = currentUserService.getCurrentUser();

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        userDto.setAge(user.getAge());
        userDto.setVisible(user.isVisible());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setAvatarUrl(user.getAvatarUrl());

        return userDto;
    }

    @Transactional
    public FollowDto follow(FollowDto followDto) {
        User user = currentUserService.getCurrentUser();

        if (user.getUsername().equals(followDto.getUsername())) {
            throw new HttpStatusException("Self follow not allowed", HttpStatus.BAD_REQUEST);
        }

        User followTo = userDao.findByUsername(followDto.getUsername());

        if (followTo == null) {
            throw new HttpStatusException("User not found", HttpStatus.NOT_FOUND);
        }

        if (user.getFollowed().stream().anyMatch(x -> Objects.equals(x.getId(), followTo.getId()))) {
            throw new HttpStatusException("Already followed", HttpStatus.BAD_REQUEST);
        }

        user.getFollowed().add(followTo);
        userDao.update(user);

        return followDto;
    }

    @Transactional
    public List<UserDto> getFollowedUsers() {
        User user = currentUserService.getCurrentUser();

        return user.getFollowed().stream().map(x -> {
            UserDto userDto = new UserDto();
            userDto.setId(x.getId());
            userDto.setUsername(x.getUsername());
            userDto.setAvatarUrl(x.getAvatarUrl());

            return userDto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<UserDto> getFollowers() {
        User user = currentUserService.getCurrentUser();

        return user.getFollowers().stream().map(x -> {
            UserDto userDto = new UserDto();
            userDto.setId(x.getId());
            userDto.setUsername(x.getUsername());
            userDto.setAvatarUrl(x.getAvatarUrl());

            return userDto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public UserDto deleteUser(String username) {
        User user = currentUserService.getCurrentUser();
        User willBeDeleted = userDao.findByUsername(username);

        if (willBeDeleted.getRole() == Role.ADMIN || willBeDeleted.getRole() == user.getRole()) {
            throw new HttpStatusException("Failed removing", HttpStatus.BAD_REQUEST);
        }

        willBeDeleted.setVisible(false);
        willBeDeleted.setRemovedBy(user);
        userDao.update(willBeDeleted);

        UserDto userDto = new UserDto();
        userDto.setId(willBeDeleted.getId());
        userDto.setUsername(willBeDeleted.getUsername());
        userDto.setRole(willBeDeleted.getRole());
        userDto.setAge(willBeDeleted.getAge());
        userDto.setVisible(willBeDeleted.isVisible());
        userDto.setPhoneNumber(willBeDeleted.getPhoneNumber());
        userDto.setAvatarUrl(willBeDeleted.getAvatarUrl());
        userDto.setRemovedBy(user.getUsername());

        return userDto;
    }
}
