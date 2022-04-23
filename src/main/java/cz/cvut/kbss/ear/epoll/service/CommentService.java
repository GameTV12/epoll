package cz.cvut.kbss.ear.epoll.service;

import cz.cvut.kbss.ear.epoll.dao.PostCommentDao;
import cz.cvut.kbss.ear.epoll.dao.PostDao;
import cz.cvut.kbss.ear.epoll.dao.VariantCommentDao;
import cz.cvut.kbss.ear.epoll.dao.VariantDao;
import cz.cvut.kbss.ear.epoll.dto.CommentDto;
import cz.cvut.kbss.ear.epoll.dto.CreateCommentDto;
import cz.cvut.kbss.ear.epoll.dto.EditCommentDto;
import cz.cvut.kbss.ear.epoll.exception.HttpStatusException;
import cz.cvut.kbss.ear.epoll.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final PostCommentDao postCommentDao;
    private final VariantCommentDao variantCommentDao;
    private final PostDao postDao;
    private final VariantDao variantDao;
    private final CurrentUserService currentUserService;

    @Autowired
    public CommentService(PostCommentDao postCommentDao, VariantCommentDao variantCommentDao, PostDao postDao, VariantDao variantDao, CurrentUserService currentUserService) {
        this.postCommentDao = postCommentDao;
        this.variantCommentDao = variantCommentDao;
        this.postDao = postDao;
        this.variantDao = variantDao;
        this.currentUserService = currentUserService;
    }

    private CommentDto getCommentDto(PostComment comment) {
        if (comment == null) {
            return null;
        }

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setCreatedBy(comment.getCreatedBy().getUsername());

        if (comment.getRepliedTo() != null) {
            commentDto.setRepliedTo(comment.getRepliedTo().getId());
        }
        commentDto.setVisible(comment.isVisible());
        if (comment.getRemovedBy() != null) {
            commentDto.setRemovedBy(comment.getRemovedBy().getUsername());
        }

        return commentDto;
    }

    private CommentDto getCommentDto(VariantComment comment) {
        if (comment == null) {
            return null;
        }

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setCreatedBy(comment.getCreatedBy().getUsername());

        if (comment.getRepliedTo() != null) {
            commentDto.setRepliedTo(comment.getRepliedTo().getId());
        }
        commentDto.setVisible(comment.isVisible());
        if (comment.getRemovedBy() != null) {
            commentDto.setRemovedBy(comment.getRemovedBy().getUsername());
        }

        return commentDto;
    }

    @Transactional
    public List<CommentDto> getAllVisibleCommentsByPost(int postId) {
        return postCommentDao.findAllVisibleByPost(postId).stream().map(this::getCommentDto).collect(Collectors.toList());
    }

    @Transactional
    public List<CommentDto> getAllVisibleRepliesToPostComment(int commentId) {
        return postCommentDao.findAllReplies(commentId).stream().map(this::getCommentDto).collect(Collectors.toList());
    }

    @Transactional
    public CommentDto createPostComment(int postId, CreateCommentDto createCommentDto) {
        if (postDao.find(postId)==null){
            throw new HttpStatusException("Bad post", HttpStatus.BAD_REQUEST);
        }
        User user = currentUserService.getCurrentUser();
        Post post = postDao.find(postId);

        PostComment postComment = new PostComment();
        postComment.setCreatedBy(user);
        postComment.setCreatedAt(LocalDateTime.now());
        postComment.setText(createCommentDto.getText());
        postComment.setPost(post);
        postComment.setVisible(true);

        if ((createCommentDto.getReply() != null) && (postCommentDao.find(createCommentDto.getReply()) != null)) {
            postComment.setRepliedTo(postCommentDao.find(createCommentDto.getReply()));
        }

        postCommentDao.persist(postComment);
        post.getComments().add(postComment);
        postDao.update(post);

        return getCommentDto(postComment);
    }

    @Transactional
    public CommentDto deletePostComment(int id) {
        User user = currentUserService.getCurrentUser();
        PostComment postComment = postCommentDao.find(id);

        if (postComment == null || !(postComment.getCreatedBy().getId() == user.getId() || user.getRole() != Role.USER)) {
            throw new HttpStatusException("Not found", HttpStatus.NOT_FOUND);
        }

        postComment.setVisible(false);
        postCommentDao.update(postComment);

        return getCommentDto(postComment);
    }

    @Transactional
    public CommentDto editPostComment(EditCommentDto editCommentDto) {
        User user = currentUserService.getCurrentUser();
        PostComment postComment = postCommentDao.find(editCommentDto.getId());

        if (postComment == null || !(postComment.getCreatedBy().getId() == user.getId()) || !postComment.isVisible()) {
            throw new HttpStatusException("Not found", HttpStatus.NOT_FOUND);
        }

        postComment.setText(editCommentDto.getText());
        postCommentDao.update(postComment);

        return getCommentDto(postComment);
    }

    @Transactional
    public List<CommentDto> getAllVisibleCommentsByVariant(int variantId) {
        return variantCommentDao.findAllVisibleByVariant(variantId).stream().map(this::getCommentDto).collect(Collectors.toList());
    }

    @Transactional
    public List<CommentDto> getAllVisibleRepliesToVariantComment(int commentId) {
        return variantCommentDao.findAllReplies(commentId).stream().map(this::getCommentDto).collect(Collectors.toList());
    }

    @Transactional
    public CommentDto createVariantComment(int variantId, CreateCommentDto createCommentDto) {
        if (variantDao.find(variantId)==null){
            throw new HttpStatusException("Bad variant", HttpStatus.BAD_REQUEST);
        }
        User user = currentUserService.getCurrentUser();
        Variant variant = variantDao.find(variantId);

        VariantComment variantComment = new VariantComment();
        variantComment.setCreatedBy(user);
        variantComment.setCreatedAt(LocalDateTime.now());
        variantComment.setText(createCommentDto.getText());
        variantComment.setVariant(variant);
        variantComment.setVisible(true);

        if ((createCommentDto.getReply() != null) && (variantCommentDao.find(createCommentDto.getReply()) != null)) {
            variantComment.setRepliedTo(variantCommentDao.find(createCommentDto.getReply()));
        }

        variantCommentDao.persist(variantComment);
        variant.getComments().add(variantComment);
        variantDao.update(variant);

        return getCommentDto(variantComment);
    }

    @Transactional
    public CommentDto deleteVariantComment(int id) {
        User user = currentUserService.getCurrentUser();
        VariantComment variantComment = variantCommentDao.find(id);

        if (variantComment == null || !(variantComment.getCreatedBy().getId() == user.getId() || user.getRole() != Role.USER)) {
            throw new HttpStatusException("Not found", HttpStatus.NOT_FOUND);
        }

        variantComment.setVisible(false);
        variantCommentDao.update(variantComment);

        return getCommentDto(variantComment);
    }

    @Transactional
    public CommentDto editVariantComment(EditCommentDto editCommentDto) {
        User user = currentUserService.getCurrentUser();
        VariantComment variantComment = variantCommentDao.find(editCommentDto.getId());

        if (variantComment == null || !(variantComment.getCreatedBy().getId() == user.getId()) || !variantComment.isVisible()) {
            throw new HttpStatusException("Not found", HttpStatus.NOT_FOUND);
        }

        variantComment.setText(editCommentDto.getText());
        variantCommentDao.update(variantComment);

        return getCommentDto(variantComment);
    }
}
