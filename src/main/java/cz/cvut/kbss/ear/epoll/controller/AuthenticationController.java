package cz.cvut.kbss.ear.epoll.controller;

import cz.cvut.kbss.ear.epoll.dto.SignInDto;
import cz.cvut.kbss.ear.epoll.dto.SignUpDto;
import cz.cvut.kbss.ear.epoll.dto.UserDto;
import cz.cvut.kbss.ear.epoll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAnonymous() || hasRole('ADMIN')")
    @PostMapping(path = "/signUp")
    public UserDto signUp(@RequestBody @Valid SignUpDto signUpDto) {
        return userService.signUp(signUpDto);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(path = "/signIn")
    public UserDto signIn(@RequestBody @Valid SignInDto signInDto) {
        return userService.signIn(signInDto);
    }
}
