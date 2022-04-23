package cz.cvut.kbss.ear.epoll.model;

import cz.cvut.kbss.ear.epoll.environment.Generator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostTest {
    private Post post;
    @BeforeEach
    public void setUp() {
        post = Generator.generatePost();
    }

    @Test
    public void setTextSetsText(){
        post.setText("Vitya");
        assertEquals("Vitya", post.getText());
    }

    @Test
    public void setTitleSetsTitle(){
        post.setTitle("Hello I'm V");
        assertEquals("Hello I'm V", post.getTitle());
    }
}
