package cz.cvut.kbss.ear.epoll.dao;

import cz.cvut.kbss.ear.epoll.EPollApplication;
import cz.cvut.kbss.ear.epoll.environment.Generator;
import cz.cvut.kbss.ear.epoll.model.PostComment;
import cz.cvut.kbss.ear.epoll.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ComponentScan(basePackageClasses = EPollApplication.class)
public class PostDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private PostDao sut;

    private Post generatePost() {
        final Post post = new Post();
        post.setText("Random message" + Generator.randomInt());
        post.setTitle("Random title" + Generator.randomInt());
        post.setVisible(true);
        em.persist(post);
        return post;
    }

    private List<PostComment> generateComments(Post post){
        List<PostComment> postCommentList = new ArrayList<>();
        List<PostComment> postCommentList2 = new ArrayList<>();
        final Post post2 = new Post();
        post2.setText("Random message" + Generator.randomInt());
        post2.setTitle("Random title" + Generator.randomInt());
        for(int i =0; i<10; i++){
            PostComment c = new PostComment();
            c.setText("Random message" + Generator.randomInt());
            if(Generator.randomBoolean()){
                c.setPost(post);
                postCommentList.add(c);
            }
            else{
                c.setPost(post2);
                postCommentList2.add(c);
            }
            em.persist(c);
        }
        post.setComments(postCommentList);
        post2.setComments(postCommentList2);
        em.persist(post);
        em.persist(post2);
        return postCommentList;
    }

    @Test
    public void findByIdReturnsCommentsInPost() {
        final Post post = generatePost();
        final List<PostComment> comments = generateComments(post);
        final List<PostComment> result = sut.find(post.getId()).getComments();
        assertEquals(comments.size(), result.size());
        comments.sort(Comparator.comparing(PostComment::getText));
        result.sort(Comparator.comparing(PostComment::getText));
        IntStream.range(0, comments.size()).forEach(i -> assertEquals(comments.get(i).getId(), result.get(i).getId()));
    }

}
