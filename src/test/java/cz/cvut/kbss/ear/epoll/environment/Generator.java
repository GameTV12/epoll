package cz.cvut.kbss.ear.epoll.environment;

import cz.cvut.kbss.ear.epoll.dto.CommentDto;
import cz.cvut.kbss.ear.epoll.dto.PostDto;
import cz.cvut.kbss.ear.epoll.model.Post;
import cz.cvut.kbss.ear.epoll.model.PostComment;
import cz.cvut.kbss.ear.epoll.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    private static final Random RAND = new Random();

    public static int randomInt() {
        return RAND.nextInt();
    }

    public static boolean randomBoolean() {
        return RAND.nextBoolean();
    }

    public static User generateUser() {
        final User user = new User();
        user.setUsername("Username" + randomInt());
        user.setAvatarUrl("Photo" + randomInt());
        user.setEmail("email" + randomInt() + "@kbss.felk.cvut.cz");
        user.setPassword(Integer.toString(randomInt()));
        user.setPhoneNumber(Integer.toString(randomInt()));
        user.setAge(randomInt());
        return user;
    }
    public static PostDto generatePostDto() {
        final PostDto post = new PostDto();
        post.setText("Random message" + Generator.randomInt());
        post.setTitle("Random title" + Generator.randomInt());
        return post;
    }
    public static Post generatePost() {
        final Post post = new Post();
        post.setText("Random message" + Generator.randomInt());
        post.setTitle("Random title" + Generator.randomInt());
        return post;
    }

}
