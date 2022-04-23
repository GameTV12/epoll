package cz.cvut.kbss.ear.epoll.controller;

import cz.cvut.kbss.ear.epoll.dto.ModeratorRequestDto;
import cz.cvut.kbss.ear.epoll.dto.RequestModeratorRoleDto;
import cz.cvut.kbss.ear.epoll.dto.UpdateModeratorRequestStatusDto;
import cz.cvut.kbss.ear.epoll.model.ModeratorRequestStatus;
import cz.cvut.kbss.ear.epoll.service.ModeratorRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/request")
public class ModeratorRequestController {
    private final ModeratorRequestService moderatorRequestService;

    @Autowired
    public ModeratorRequestController(ModeratorRequestService moderatorRequestService) {
        this.moderatorRequestService = moderatorRequestService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/")
    public ModeratorRequestDto requestModeratorRole(@Valid RequestModeratorRoleDto requestModeratorRoleDto) {
        return moderatorRequestService.requestModeratorRole(requestModeratorRoleDto);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/")
    public List<ModeratorRequestDto> getUserModeratorRequests() {
        return moderatorRequestService.getUserModeratorRequests();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/waiting")
    public List<ModeratorRequestDto> getWaitingModeratorRequests() {
        return moderatorRequestService.getModeratorRequestsWithStatus(ModeratorRequestStatus.WAITING);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public List<ModeratorRequestDto> getModeratorRequests() {
        return moderatorRequestService.getModeratorRequests();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/accept")
    public ModeratorRequestDto acceptModeratorRequest(@RequestParam int id) {
        UpdateModeratorRequestStatusDto updateModeratorRequestStatusDto = new UpdateModeratorRequestStatusDto();
        updateModeratorRequestStatusDto.setId(id);
        updateModeratorRequestStatusDto.setNewStatus(ModeratorRequestStatus.ACCEPTED);

        return moderatorRequestService.updateModeratorRequestStatus(updateModeratorRequestStatusDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/decline")
    public ModeratorRequestDto declineModeratorRequest(@RequestParam int id) {
        UpdateModeratorRequestStatusDto updateModeratorRequestStatusDto = new UpdateModeratorRequestStatusDto();
        updateModeratorRequestStatusDto.setId(id);
        updateModeratorRequestStatusDto.setNewStatus(ModeratorRequestStatus.DECLINED);

        return moderatorRequestService.updateModeratorRequestStatus(updateModeratorRequestStatusDto);
    }
}
