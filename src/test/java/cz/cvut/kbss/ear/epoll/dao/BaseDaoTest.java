package cz.cvut.kbss.ear.epoll.dao;

import cz.cvut.kbss.ear.epoll.EPollApplication;
import cz.cvut.kbss.ear.epoll.environment.Generator;
import cz.cvut.kbss.ear.epoll.model.Photo;
import cz.cvut.kbss.ear.epoll.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ComponentScan(basePackageClasses = EPollApplication.class)
public class BaseDaoTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private PhotoDao sut;

    private static Photo generatePhoto() {
        final Photo photo = new Photo();
        photo.setUrl("https://www.google.com/" + Generator.randomInt());
        photo.setPost(generatePost(photo));
        return photo;
    }

    private static Post generatePost(Photo photo) {
        final Post post = new Post();
        List<Photo> photoList = new ArrayList<>();
        photoList.add(photo);
        post.setText("Random message" + Generator.randomInt());
        post.setTitle("Random title" + Generator.randomInt());
        post.setPhotos(photoList);
        return post;
    }

    @Test
    public void persistSavesSpecifiedInstance() {
        final Photo photo = generatePhoto();
        sut.persist(photo);
        assertNotNull(photo.getId());
        final Photo result = em.find(Photo.class, photo.getId());
        assertNotNull(result);
        assertEquals(photo.getId(), result.getId());
        assertEquals(photo.getUrl(), result.getUrl());
    }

    @Test
    public void findRetrievesInstanceByIdentifier() {
        final Photo photo = generatePhoto();
        em.persistAndFlush(photo);
        assertNotNull(photo.getId());
        final Photo result = sut.find(photo.getId());
        assertNotNull(result);
        assertEquals(photo.getId(), result.getId());
        assertEquals(photo.getUrl(), result.getUrl());
    }

    @Test
    public void findAllRetrievesAllInstancesOfType() {
        final Photo photoOne = generatePhoto();
        em.persistAndFlush(photoOne);
        final Photo photoTwo = generatePhoto();
        em.persistAndFlush(photoTwo);
        final List<Photo> result = sut.findAll();
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getId() == (photoOne.getId())));
        assertTrue(result.stream().anyMatch(c -> c.getId() == (photoTwo.getId())));
    }

    @Test
    public void updateUpdatesExistingInstance() {
        final Photo photo = generatePhoto();
        em.persistAndFlush(photo);
        final Photo update = new Photo();
        update.setId(photo.getId());
        final String newUrl = "New url";
        update.setUrl(newUrl);
        sut.update(update);
        final Photo result = sut.find(photo.getId());
        assertNotNull(result);
        assertEquals(photo.getUrl(), result.getUrl());
    }

    @Test
    public void removeRemovesSpecifiedInstance() {
        final Photo photo = generatePhoto();
        em.persistAndFlush(photo);
        assertNotNull(em.find(Photo.class, photo.getId()));
        em.detach(photo);
        sut.remove(photo);
        assertNull(em.find(Photo.class, photo.getId()));
    }

    @Test
    public void removeDoesNothingWhenInstanceDoesNotExist() {
        final Photo photo = generatePhoto();
        photo.setId(123);
        assertNull(em.find(Photo.class, photo.getId()));

        sut.remove(photo);
        assertNull(em.find(Photo.class, photo.getId()));
    }

    @Test
    public void exceptionOnPersistInWrappedInPersistenceException() {
        final Photo photo = generatePhoto();
        em.persistAndFlush(photo);
        em.remove(photo);
        assertThrows(PersistenceException.class, () -> sut.update(photo));
    }

    @Test
    public void existsReturnsTrueForExistingIdentifier() {
        final Photo photo = generatePhoto();
        em.persistAndFlush(photo);
        assertTrue(sut.exists(photo.getId()));
        assertFalse(sut.exists(-1));
    }

}
