package cz.cvut.kbss.ear.epoll.service;

import cz.cvut.kbss.ear.epoll.dao.PollDao;
import cz.cvut.kbss.ear.epoll.dao.VariantDao;
import cz.cvut.kbss.ear.epoll.dto.NewVariantDto;
import cz.cvut.kbss.ear.epoll.dto.VariantDto;
import cz.cvut.kbss.ear.epoll.exception.HttpStatusException;
import cz.cvut.kbss.ear.epoll.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class VariantService {
    private final VariantDao variantDao;
    private final PollDao pollDao;
    private final CurrentUserService currentUserService;

    @Autowired
    public VariantService(VariantDao variantDao, PollDao pollDao, CurrentUserService currentUserService) {
        this.variantDao = variantDao;
        this.pollDao = pollDao;
        this.currentUserService = currentUserService;
    }

    @Transactional
    public VariantDto voteForVariant(int variantId){
        User user = currentUserService.getCurrentUser();
        Variant variant = variantDao.find(variantId);
        Poll poll = pollDao.find(variant.getPoll().getId());
        if (!variant.getPoll().getPost().isVisible() || !variant.getPoll().getPost().getCreatedBy().isVisible()){
            throw new HttpStatusException("Not found", HttpStatus.NOT_FOUND);
        }
        if (poll.getVoters().stream().anyMatch(x -> Objects.equals(x.getId(), user.getId()))){
            throw new HttpStatusException("Already voted", HttpStatus.BAD_REQUEST);
        }
        poll.getVoters().add(user);
        variant.setVotes(variant.getVotes()+1);
        pollDao.update(poll);
        variantDao.update(variant);
        VariantDto variantDto = new VariantDto();
        variantDto.setId(variant.getId());
        variantDto.setText(variant.getText());
        variantDto.setVotes(variant.getVotes());
        return variantDto;
    }

    @Transactional
    public VariantDto addNewVariant(NewVariantDto newVariantDto){
        User user = currentUserService.getCurrentUser();
        Poll poll = pollDao.find(newVariantDto.getPoll_id());
        if (!poll.getPost().isVisible() || !poll.getPost().getCreatedBy().isVisible()){
            throw new HttpStatusException("Not found", HttpStatus.NOT_FOUND);
        }
        if (poll.isFrozen()){
            throw new HttpStatusException("It is frozen poll", HttpStatus.FORBIDDEN);
        }
        if (poll.getVoters().stream().anyMatch(x -> Objects.equals(x.getId(), user.getId()))){
            throw new HttpStatusException("Already voted", HttpStatus.BAD_REQUEST);
        }

        Variant variant = new Variant();
        variant.setVotes(1);
        variant.setText(newVariantDto.getText());
        variant.setPoll(poll);
        variant.setCreatedBy(user);
        variant.setCreatedAt(LocalDateTime.now());
        variantDao.persist(variant);
        poll.getVoters().add(user);
        poll.getVariants().add(variant);
        pollDao.update(poll);
        VariantDto variantDto = new VariantDto();
        variantDto.setText(newVariantDto.getText());
        variantDto.setVotes(1);
        variantDto.setId(variant.getId());
        return variantDto;
    }

    @Transactional
    public VariantDto deleteVariant(int id) {
        User user = currentUserService.getCurrentUser();
        Variant variant = variantDao.find(id);

        if (variant == null || !variant.isVisible() ||!(variant.getCreatedBy().getId() == user.getId() || user.getRole() != Role.USER)) {
            throw new HttpStatusException("Not found", HttpStatus.NOT_FOUND);
        }

        variant.setVisible(false);
        variant.setRemovedBy(user);
        variantDao.update(variant);

        VariantDto variantDto = new VariantDto();
        variantDto.setText(variant.getText());
        variantDto.setVotes(variant.getVotes());
        variantDto.setId(variant.getId());

        return variantDto;
    }
}
