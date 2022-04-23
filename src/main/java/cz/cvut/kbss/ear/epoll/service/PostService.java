package cz.cvut.kbss.ear.epoll.service;

import cz.cvut.kbss.ear.epoll.dao.PostDao;
import cz.cvut.kbss.ear.epoll.dto.*;
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
public class PostService {

    private final PostDao postDao;
    private final CurrentUserService currentUserService;

    @Autowired
    public PostService(PostDao dao, CurrentUserService currentUserService) {
        this.postDao = dao;
        this.currentUserService = currentUserService;
    }

    @Transactional
    public List<PostDto> getVisiblePosts() {
        return postDao.findAllVisible().stream().map(this::getPostDto).collect(Collectors.toList());
    }

    @Transactional
    public List<PostDto> getVisiblePostsByUser(String username) {
        return postDao.findAllVisibleByUsername(username).stream().map(this::getPostDto).collect(Collectors.toList());
    }

    @Transactional
    public PostDto getPostById(int id) {
        if(postDao.find(id)==null || !postDao.find(id).isVisible() || !postDao.find(id).getCreatedBy().isVisible()){
            throw new HttpStatusException("Not found", HttpStatus.NOT_FOUND);
        }
        Post post = postDao.find(id);
        return getPostDto(post);
    }

    @Transactional
    public PostDto createPost(CreatePostDto createPostDto) {
        User user = currentUserService.getCurrentUser();

        Post post = new Post();
        post.setCreatedBy(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setTitle(createPostDto.getTitle());
        post.setText(createPostDto.getContent());
        post.setVisible(true);

        if (createPostDto.getPhotos() != null) {
            List<Photo> photos = createPostDto.getPhotos().stream().map(x -> {
                Photo photo = new Photo();
                photo.setUrl(x.getUrl());
                photo.setPost(post);
                return photo;
            }).collect(Collectors.toList());
            post.setPhotos(photos);
        }

        if (createPostDto.getVideo() != null) {
            Video video = new Video();
            video.setUrl(createPostDto.getVideo().getUrl());
            video.setPost(post);
            post.setVideo(video);
        }

        if (createPostDto.getPoll() != null) {
            Poll poll = new Poll();
            poll.setFrozen(createPostDto.getPoll().isFrozen());
            List<Variant> variants = createPostDto.getPoll().getVariants().stream().map(x -> {
                Variant variant = new Variant();
                variant.setVotes(0);
                variant.setText(x.getText());
                variant.setPoll(poll);
                variant.setCreatedBy(post.getCreatedBy());
                variant.setCreatedAt(post.getCreatedAt());
                return variant;
            }).collect(Collectors.toList());
            poll.setVariants(variants);
            poll.setPost(post);
            post.setPoll(poll);
        }

        postDao.persist(post);

        return getPostDto(post);
    }

    private PostDto getPostDto(Post post) {
        if (post == null) {
            return null;
        }

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setText(post.getText());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setCreatedBy(post.getCreatedBy().getUsername());
        if (post.getRemovedBy()!=null){
            postDto.setRemovedBy(post.getRemovedBy().getUsername());
        }
        if (post.getPhotos() != null && post.getPhotos().size() > 0) {
            List<String> photoUrls = post.getPhotos().stream().map(Photo::getUrl).collect(Collectors.toList());
            postDto.setPhotoUrls(photoUrls);
        }
        if (post.getVideo() != null) {
            postDto.setVideoUrl(post.getVideo().getUrl());
        }
        if (post.getPoll() != null) {
            PollDto pollDto = new PollDto();
            pollDto.setFrozen(post.getPoll().isFrozen());
            List<VariantDto> variants = post.getPoll().getVariants().stream().filter(AbstractModerated::isVisible).map(x -> {
                VariantDto variantDto = new VariantDto();
                variantDto.setId(x.getId());
                variantDto.setText(x.getText());
                variantDto.setVotes(x.getVotes());
                return variantDto;
            }).collect(Collectors.toList());
            pollDto.setVariants(variants);
            pollDto.setId(post.getPoll().getId());
            pollDto.setpostId(post.getId());
            postDto.setPoll(pollDto);
        }

        return postDto;
    }

    @Transactional
    public PostDto deletePost(int id) {
        User user = currentUserService.getCurrentUser();
        Post post = postDao.find(id);

        if (post == null || !(post.getCreatedBy().getId() == user.getId() || user.getRole() != Role.USER)) {
            throw new HttpStatusException("Not found", HttpStatus.NOT_FOUND);
        }

        post.setVisible(false);
        post.setRemovedBy(user);
        postDao.update(post);

        return getPostDto(post);
    }

    @Transactional
    public PostDto editPost(int id, EditPostDto editPostDto) {
        User user = currentUserService.getCurrentUser();
        Post post = postDao.find(id);
        if (post==null || !(post.getCreatedBy().getId() == user.getId() || user.getRole() != Role.USER)){
            throw new HttpStatusException("Not found", HttpStatus.NOT_FOUND);
        }
        post.setTitle(editPostDto.getTitle());
        post.setText(editPostDto.getContent());

        if (editPostDto.getVideo() != null) {
            Video newVideo = new Video();
            newVideo.setPost(post);
            newVideo.setUrl(editPostDto.getVideo().getUrl());
            post.setVideo(newVideo);
        } else {
            post.setVideo(null);
        }

        post.getPhotos().clear();
        if (editPostDto.getPhotos() != null) {
            List<Photo> photos = editPostDto.getPhotos().stream().map(x -> {
                Photo photo = new Photo();
                photo.setUrl(x.getUrl());
                photo.setPost(post);
                return photo;
            }).collect(Collectors.toList());
            for (Photo photo : photos) {
                post.getPhotos().add(photo);
            }
        }

        postDao.update(post);

        return getPostDto(post);
    }
}