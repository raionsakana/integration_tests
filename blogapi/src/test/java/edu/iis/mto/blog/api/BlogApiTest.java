package edu.iis.mto.blog.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

import edu.iis.mto.blog.domain.errors.DomainError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.dto.Id;
import edu.iis.mto.blog.services.BlogService;
import edu.iis.mto.blog.services.DataFinder;

import javax.persistence.EntityNotFoundException;

@RunWith(SpringRunner.class)
@WebMvcTest(BlogApi.class)
public class BlogApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BlogService blogService;

    @MockBean
    private DataFinder finder;

    private UserRequest user;

    private Long newUserId = 1L;
    private Long notExistingUserId = 145L;

    private int notFoundErrorCode = 404;
    private int confilictErrorCode = 409;

    private String endpointBlogUser = "/blog/user";

    @Before
    public void setUp() {
        this.user = new UserRequest();
        this.user.setEmail("john@domain.com");
        this.user.setFirstName("John");
        this.user.setLastName("Steward");

        when(this.blogService.createUser(this.user)).thenReturn(this.newUserId);
    }

    @Test
    public void postBlogUserShouldResponseWithStatusCreatedAndNewUserId() throws Exception {
        String content = writeJson(this.user);

        this.mvc.perform(post(this.endpointBlogUser).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isCreated())
            .andExpect(content().string(writeJson(new Id(this.newUserId))));
    }

    @Test
    public void postBlogUserShouldResponse409WhenDataIntegrityViolationException() throws Exception {
        when(this.blogService.createUser(any())).thenThrow(DataIntegrityViolationException.class);
        String content = writeJson(new UserRequest());

        this.mvc.perform(post(this.endpointBlogUser).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().is(this.confilictErrorCode));
    }

    @Test
    public void postBlogUserShouldResponse404WhenUserNotFound() throws Exception {
        when(finder.getUserData(this.notExistingUserId))
            .thenThrow(new EntityNotFoundException(DomainError.USER_NOT_FOUND));

        this.mvc.perform(get(this.endpointBlogUser + "/" + this.notExistingUserId))
            .andExpect(status().is(this.notFoundErrorCode));
    }

    private String writeJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writer().writeValueAsString(obj);
    }

}
