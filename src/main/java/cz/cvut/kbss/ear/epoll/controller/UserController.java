package cz.cvut.kbss.ear.epoll.controller;

import cz.cvut.kbss.ear.epoll.dto.FollowDto;
import cz.cvut.kbss.ear.epoll.dto.UserDto;
import cz.cvut.kbss.ear.epoll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/")
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @PostMapping(path = "/follow")
    public FollowDto follow(@Valid @RequestBody FollowDto followDto) {
        return userService.follow(followDto);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping(path = "/followed")
    public List<UserDto> getFollowedUsers() {
        return userService.getFollowedUsers();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @GetMapping(path = "/followers")
    public List<UserDto> getFollowers() {
        return userService.getFollowers();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @PostMapping("/delete")
    public UserDto deleteUser(@RequestParam String username) {
        return userService.deleteUser(username);
    }
}
