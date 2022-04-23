package cz.cvut.kbss.ear.epoll.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.cvut.kbss.ear.epoll.controller.PostController;
import cz.cvut.kbss.ear.epoll.dto.CreatePostDto;
import cz.cvut.kbss.ear.epoll.dto.PostDto;
import cz.cvut.kbss.ear.epoll.environment.Generator;
import cz.cvut.kbss.ear.epoll.model.Post;
import cz.cvut.kbss.ear.epoll.service.CommentService;
import cz.cvut.kbss.ear.epoll.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest extends BaseControllerTestRunner{
    @Mock
    private PostService postServiceMock;

    @InjectMocks
    private PostController sut;

    @BeforeEach
    public void setUp() {
        super.setUp(sut);
    }

    @Test
    public void getAllReturnsAllProducts() throws Exception {
        final List<PostDto> posts = new ArrayList<>();
        for(int i = 0; i<10; i++){
            posts.add(Generator.generatePostDto());
        }
        when(postServiceMock.getVisiblePosts()).thenReturn(posts);
        final MvcResult mvcResult = mockMvc.perform(get("/post/")).andReturn();
        final List<PostDto> result = readValue(mvcResult, new TypeReference<List<PostDto>>() {
        });
        assertNotNull(result);
        assertEquals(posts.size(), result.size());
        for (int i = 0; i < posts.size(); i++) {
            assertEquals(posts.get(i).getTitle(), result.get(i).getTitle());
            assertEquals(posts.get(i).getText(), result.get(i).getText());
        }
    }

    @Test
    public void getByIdReturnsPostWithMatchingId() throws Exception {
        final PostDto post = Generator.generatePostDto();
        post.setId(123);
        when(postServiceMock.getPostById(post.getId())).thenReturn(post);
        final MvcResult mvcResult = mockMvc.perform(get("/post/" +post.getId())).andReturn();
        final PostDto result = readValue(mvcResult, PostDto.class);
        assertNotNull(result);
        assertEquals(post.getId(), result.getId());
        assertEquals(post.getTitle(), result.getTitle());
        assertEquals(post.getText(), result.getText());
    }


    @Test
    public void removeRemovesPostUsingService() throws Exception {
        final Post post = Generator.generatePost();
        mockMvc.perform(delete("/post/" + post.getId())).andExpect(status().isOk());
        verify(postServiceMock).deletePost(post.getId());
    }

    @Test
    public void createPostCreatesCategoryUsingService() throws Exception {
        final PostDto toCreate = new PostDto();
        toCreate.setTitle("New post");
        mockMvc.perform(post("/post/").content(toJson(toCreate))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        final ArgumentCaptor<CreatePostDto> captor = ArgumentCaptor.forClass(CreatePostDto.class);
        verify(postServiceMock).createPost(captor.capture());
        assertEquals(toCreate.getTitle(), captor.getValue().getTitle());
    }
}
