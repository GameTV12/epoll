package cz.cvut.kbss.ear.epoll.model;

import cz.cvut.kbss.ear.epoll.environment.Generator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = Generator.generateUser();
    }

    @Test
    public void setUsernameSetsUsername(){
        user.setUsername("Vitya");
        assertEquals("Vitya", user.getUsername());
    }

    @Test
    public void setPhoneNumberSetsPhoneNumber(){
        user.setPhoneNumber("+420773030854");
        assertEquals("+420773030854", user.getPhoneNumber());
    }

    @Test
    public void setAgeSetsAge(){
        user.setAge(21);
        assertEquals(21, user.getAge());
    }

    @Test
    public void setEmailSetsEmail(){
        user.setEmail("vkao@gmail.com");
        assertEquals("vkao@gmail.com", user.getEmail());
    }

    @Test
    public void setRoleSetsRole(){
        user.setRole(Role.ADMIN);
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    public void setPasswordSetsPassword(){
        user.setPassword("catsanddogs123");
        assertEquals("catsanddogs123", user.getPassword());
    }

    @Test
    public void setAvatarUrlSetsAvatarUrl(){
        user.setAvatarUrl("google.com/pictures/cat");
        assertEquals("google.com/pictures/cat", user.getAvatarUrl());
    }
}
