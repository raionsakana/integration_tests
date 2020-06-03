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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

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
}