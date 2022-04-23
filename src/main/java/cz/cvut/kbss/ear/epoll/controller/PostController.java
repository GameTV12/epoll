package cz.cvut.kbss.ear.epoll.controller;

import cz.cvut.kbss.ear.epoll.dto.*;
import cz.cvut.kbss.ear.epoll.service.CommentService;
import cz.cvut.kbss.ear.epoll.service.PostService;
import cz.cvut.kbss.ear.epoll.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final VariantService variantService;

    @Autowired
    public PostController(PostService postService, CommentService commentService, VariantService variantService) {
        this.postService = postService;
        this.commentService = commentService;
        this.variantService = variantService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public List<PostDto> getVisiblePosts() {
        return postService.getVisiblePosts();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @PostMapping("/")
    public PostDto createPost(@Valid @RequestBody CreatePostDto createPostDto) {
        return postService.createPost(createPostDto);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/posts-by-{username}")
    public List<PostDto> getVisiblePostsByUser(@PathVariable String username) {
        return postService.getVisiblePostsByUser(username);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{postId}")
    public PostDto getPostById(@PathVariable int postId) {
        return postService.getPostById(postId);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @PutMapping("/{postId}/edit")
    public PostDto editPost(@PathVariable int postId, @Valid @RequestBody EditPostDto editPostDto) {
        return postService.editPost(postId, editPostDto);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @DeleteMapping("/{postId}")
    public PostDto deletePost(@PathVariable int postId) {
        return postService.deletePost(postId);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @PostMapping("/{postId}")
    public CommentDto createPostComment(@PathVariable int postId, @Valid @RequestBody CreateCommentDto createCommentDto) {
        return commentService.createPostComment(postId, createCommentDto);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{postId}/comments")
    public List<CommentDto> getVisibleCommentsByPost(@PathVariable int postId) {
        return commentService.getAllVisibleCommentsByPost(postId);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/postId/comments/{commentId}")
    public List<CommentDto> getVisiblePostCommentReplies(@PathVariable int commentId) {
        return commentService.getAllVisibleRepliesToPostComment(commentId);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @DeleteMapping("/postId/comments/delete")
    public CommentDto deletePostComment(@RequestParam int id) {
        return commentService.deletePostComment(id);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @PutMapping("/postId/comments/edit")
    public CommentDto editPostComment(@Valid @RequestBody EditCommentDto editCommentDto) {
        return commentService.editPostComment(editCommentDto);
    }


    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @PostMapping("postId/poll/{variantId}")
    public CommentDto createVariantComment(@PathVariable int variantId, @Valid @RequestBody CreateCommentDto createCommentDto) {
        return commentService.createVariantComment(variantId, createCommentDto);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("postId/poll/{variantId}/comments")
    public List<CommentDto> getVisibleCommentsByVariant(@PathVariable int variantId) {
        return commentService.getAllVisibleCommentsByVariant(variantId);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/postId/poll/variantId/comments/{commentId}")
    public List<CommentDto> getVisibleVariantCommentReplies(@PathVariable int commentId) {
        return commentService.getAllVisibleRepliesToVariantComment(commentId);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @DeleteMapping("/postId/poll/variantId/comments/delete")
    public CommentDto deleteVariantComment(@RequestParam int id) {
        return commentService.deleteVariantComment(id);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @PutMapping("/postId/poll/variantId/comments/edit")
    public CommentDto editVariantComment(@Valid @RequestBody EditCommentDto editCommentDto) {
        return commentService.editVariantComment(editCommentDto);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @PutMapping("/postId/poll/{variantId}/vote")
    public VariantDto voteForVariant(@PathVariable int variantId) {
        return variantService.voteForVariant(variantId);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @PostMapping("/postId/poll/addVariant")
    public VariantDto voteAddNewVariant(@Valid @RequestBody NewVariantDto newVariantDto) {
        return variantService.addNewVariant(newVariantDto);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    @DeleteMapping("/postId/poll/delete")
    public VariantDto deleteVariant(@RequestParam int id) {
        return variantService.deleteVariant(id);
    }


}
