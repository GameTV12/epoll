package cz.cvut.kbss.ear.epoll.service;

import cz.cvut.kbss.ear.epoll.dao.ModeratorRequestDao;
import cz.cvut.kbss.ear.epoll.dao.UserDao;
import cz.cvut.kbss.ear.epoll.dto.ModeratorRequestDto;
import cz.cvut.kbss.ear.epoll.dto.RequestModeratorRoleDto;
import cz.cvut.kbss.ear.epoll.dto.UpdateModeratorRequestStatusDto;
import cz.cvut.kbss.ear.epoll.dto.UserDto;
import cz.cvut.kbss.ear.epoll.exception.HttpStatusException;
import cz.cvut.kbss.ear.epoll.model.ModeratorRequest;
import cz.cvut.kbss.ear.epoll.model.ModeratorRequestStatus;
import cz.cvut.kbss.ear.epoll.model.Role;
import cz.cvut.kbss.ear.epoll.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModeratorRequestService {
    private final ModeratorRequestDao moderatorRequestDao;
    private final UserDao userDao;
    private final CurrentUserService currentUserService;

    @Autowired
    public ModeratorRequestService(ModeratorRequestDao moderatorRequestDao, UserDao userDao, CurrentUserService currentUserService) {
        this.moderatorRequestDao = moderatorRequestDao;
        this.userDao = userDao;
        this.currentUserService = currentUserService;
    }

    @Transactional
    public ModeratorRequestDto requestModeratorRole(RequestModeratorRoleDto dto) {
        User user = currentUserService.getCurrentUser();

        ModeratorRequest request = moderatorRequestDao.getWaitingModeratorRequestByUserId(user.getId());

        if (request != null) {
            throw new HttpStatusException("There is waiting request already", HttpStatus.BAD_REQUEST);
        }

        request = new ModeratorRequest();
        request.setCreatedBy(user);
        request.setCreatedAt(LocalDateTime.now());
        request.setReason(dto.getReason());
        request.setStatus(ModeratorRequestStatus.WAITING);
        moderatorRequestDao.persist(request);

        ModeratorRequestDto moderatorRequestDto = new ModeratorRequestDto();
        moderatorRequestDto.setReason(request.getReason());
        moderatorRequestDto.setStatus(request.getStatus());

        return moderatorRequestDto;
    }

    @Transactional
    public List<ModeratorRequestDto> getModeratorRequests() {
        return moderatorRequestDao.findAll().stream().map(x -> {
            ModeratorRequestDto moderatorRequestDto = new ModeratorRequestDto();
            moderatorRequestDto.setId(x.getId());
            moderatorRequestDto.setReason(x.getReason());
            moderatorRequestDto.setStatus(x.getStatus());
            moderatorRequestDto.setSender(getUserDto(x.getCreatedBy()));
            moderatorRequestDto.setAcceptor(getUserDto(x.getAcceptor()));

            return moderatorRequestDto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<ModeratorRequestDto> getModeratorRequestsWithStatus(ModeratorRequestStatus status) {
        return moderatorRequestDao.getAllModeratorRequestsByStatus(status).stream().map(x -> {
            ModeratorRequestDto moderatorRequestDto = new ModeratorRequestDto();
            moderatorRequestDto.setId(x.getId());
            moderatorRequestDto.setReason(x.getReason());
            moderatorRequestDto.setStatus(x.getStatus());
            moderatorRequestDto.setSender(getUserDto(x.getCreatedBy()));
            moderatorRequestDto.setAcceptor(getUserDto(x.getAcceptor()));

            return moderatorRequestDto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<ModeratorRequestDto> getUserModeratorRequests() {
        User user = currentUserService.getCurrentUser();

        return moderatorRequestDao.getAllModeratorRequestsByUserId(user.getId()).stream().map(x -> {
            ModeratorRequestDto moderatorRequestDto = new ModeratorRequestDto();
            moderatorRequestDto.setReason(x.getReason());
            moderatorRequestDto.setStatus(x.getStatus());

            return moderatorRequestDto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public ModeratorRequestDto updateModeratorRequestStatus(UpdateModeratorRequestStatusDto updateModeratorRequestStatusDto) {
        User user = currentUserService.getCurrentUser();

        ModeratorRequest moderatorRequest = moderatorRequestDao.find(updateModeratorRequestStatusDto.getId());

        if (moderatorRequest == null) {
            throw new HttpStatusException("Moderator request with specified id not found", HttpStatus.NOT_FOUND);
        }

        if (moderatorRequest.getStatus() != ModeratorRequestStatus.WAITING) {
            throw new HttpStatusException("Moderator request already processed", HttpStatus.BAD_REQUEST);
        }

        moderatorRequest.setStatus(updateModeratorRequestStatusDto.getNewStatus());
        if (moderatorRequest.getStatus() == ModeratorRequestStatus.ACCEPTED) {
            moderatorRequest.setAcceptor(user);

            User sender = moderatorRequest.getCreatedBy();

            if (sender.getRole() == Role.USER) {
                sender.setRole(Role.MODERATOR);
            }

            userDao.update(sender);
        }

        moderatorRequestDao.update(moderatorRequest);

        ModeratorRequestDto moderatorRequestDto = new ModeratorRequestDto();
        moderatorRequestDto.setId(moderatorRequest.getId());
        moderatorRequestDto.setReason(moderatorRequest.getReason());
        moderatorRequestDto.setStatus(moderatorRequest.getStatus());
        moderatorRequestDto.setSender(getUserDto(moderatorRequest.getCreatedBy()));
        moderatorRequestDto.setAcceptor(getUserDto(moderatorRequest.getAcceptor()));

        return moderatorRequestDto;
    }

    private UserDto getUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setAvatarUrl(user.getAvatarUrl());

        return userDto;
    }
}
