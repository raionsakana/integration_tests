package edu.iis.mto.blog.domain;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.iis.mto.blog.domain.errors.DomainError;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.repository.BlogPostRepository;
import edu.iis.mto.blog.domain.repository.LikePostRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.repository.UserRepository;
import edu.iis.mto.blog.mapper.BlogDataMapper;
import edu.iis.mto.blog.services.BlogService;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogManagerTest {

    @MockBean
    private BlogPostRepository blogPostRepository;

    @MockBean
    private LikePostRepository likePostRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private BlogDataMapper dataMapper;

    @Autowired
    private BlogService blogService;

    @Captor
    private ArgumentCaptor<User> userParam;

    private BlogPost blogPost;
    private User user;

    private Long userID = 1L;
    private Long postID = 2L;
    private Long tmpID = 3L;

    @Before
    public void setUp() {
        this.user = new User();
        this.user.setId(this.userID);

        User tmp = new User();
        tmp.setId(this.tmpID);

        this.blogPost = new BlogPost();
        this.blogPost.setId(this.postID);
        this.blogPost.setUser(tmp);

        when(blogPostRepository.findById(this.postID)).thenReturn(Optional.of(this.blogPost));
        when(userRepository.findById(this.userID)).thenReturn(Optional.of(this.user));
        when(userRepository.findById(this.tmpID)).thenReturn(Optional.of(tmp));
    }

    @Test
    public void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }

    @Test
    public void likeAllowedToConfirmedUser() {
        this.user.setAccountStatus(AccountStatus.CONFIRMED);
        assertDoesNotThrow(() -> this.blogService.addLikeToPost(user.getId(), this.blogPost.getId()));
    }

    @Test
    public void likeNotAllowedToNewUser() {
        this.user.setAccountStatus(AccountStatus.NEW);
        assertThrows(DomainError.class, () -> this.blogService.addLikeToPost(user.getId(), this.blogPost.getId()));
    }

}
