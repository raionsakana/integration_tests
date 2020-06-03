package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikePostRepository likePostRepository;

    private User user;
    private LikePost likePost;
    private BlogPost blogPost;

    @BeforeEach
    void setUp() {
        this.user = new User();
        this.user.setFirstName("Jan");
        this.user.setLastName("Walaszczyk");
        this.user.setEmail("john@domain.com");
        this.user.setAccountStatus(AccountStatus.NEW);

        this.blogPost = new BlogPost();
        this.blogPost.setEntry("Test");
        this.blogPost.setUser(this.user);

        this.likePost = new LikePost();
        this.likePost.setPost(this.blogPost);
        this.likePost.setUser(this.user);

        this.entityManager.persist(this.user);
        this.entityManager.persist(this.blogPost);
    }

    @Test
    void findAll() {
        this.likePostRepository.save(this.likePost);
        List<LikePost> likePosts = this.likePostRepository.findAll();
        assertThat(likePosts, hasSize(1));
    }

    @Test
    void findAllIfNoPosts() {
        List<LikePost> likePosts = this.likePostRepository.findAll();
        assertThat(likePosts, hasSize(0));
    }

    @Test
    void findAllAndCheckUser() {
        this.likePostRepository.save(this.likePost);
        List<LikePost> likePosts = this.likePostRepository.findAll();
        assertEquals(likePosts.get(0).getUser().getId(), this.user.getId());
    }

    @Test
    void findAllAndCheckPost() {
        this.likePostRepository.save(this.likePost);
        List<LikePost> likePosts = this.likePostRepository.findAll();
        assertEquals(likePosts.get(0).getPost().getId(), this.blogPost.getId());
    }

    @Test
    void findAllAndCheckLikePost() {
        this.likePostRepository.save(this.likePost);
        List<LikePost> likePosts = this.likePostRepository.findAll();
        assertEquals(likePosts.get(0).getId(), this.likePost.getId());
    }

    @Test
    void modifyLikePostNewBlogPost() {
        this.likePostRepository.save(this.likePost);

        BlogPost bPost = new BlogPost();
        bPost.setUser(this.user);
        bPost.setEntry("Test 2");
        this.entityManager.persist(bPost);

        this.likePostRepository.findAll().get(0).setPost(bPost);

        List<LikePost> likePosts = this.likePostRepository.findAll();
        assertEquals(likePosts.get(0).getPost(), bPost);
    }

    @Test
    void modifyLikePostNewUser() {
        this.likePostRepository.save(this.likePost);

        User userSecond = new User();
        userSecond.setFirstName("Kazik");
        userSecond.setLastName("Ukaszewski");
        userSecond.setEmail("kazik@gmail.com");
        userSecond.setAccountStatus(AccountStatus.NEW);

        this.entityManager.persist(userSecond);
        this.likePostRepository.findAll().get(0).setUser(userSecond);

        List<LikePost> likePosts = this.likePostRepository.findAll();
        assertEquals(likePosts.get(0).getUser(), userSecond);
    }

    @Test
    void findByUserAndPost() {
        this.likePostRepository.save(this.likePost);

        Optional<LikePost> likePosts = this.likePostRepository.findByUserAndPost(
                this.user, this.blogPost
        );

        assertEquals(likePosts.get().getPost(), this.blogPost);
        assertEquals(likePosts.get().getUser(), this.user);
    }

    @Test
    void findByUserAndPostWithNoParameters() {
        Optional<LikePost> likePosts = this.likePostRepository.findByUserAndPost(
                null, null
        );
        assertFalse(likePosts.isPresent());
    }

    @Test
    void findByUserAndPostWithNoUserParameter() {
        this.likePostRepository.save(this.likePost);

        Optional<LikePost> likePosts = this.likePostRepository.findByUserAndPost(
                null, this.blogPost
        );
        
        assertFalse(likePosts.isPresent());
    }
}