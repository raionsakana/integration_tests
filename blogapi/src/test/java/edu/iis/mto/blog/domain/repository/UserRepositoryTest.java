package edu.iis.mto.blog.domain.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User user;
    private User userSecond;

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);

        userSecond = new User();
        userSecond.setFirstName("Elka");
        userSecond.setLastName("Nowicka");
        userSecond.setEmail("elka@domain.com");
        userSecond.setAccountStatus(AccountStatus.NEW);
    }

    @Test
    public void shouldFindNoUsersIfRepositoryIsEmpty() {
        List<User> users = repository.findAll();
        assertThat(users, hasSize(0));
    }

    @Test
    public void shouldFindOneUsersIfRepositoryContainsOneUserEntity() {
        User persistedUser = entityManager.persist(user);
        List<User> users = repository.findAll();

        assertThat(users, hasSize(1));
        assertThat(users.get(0).getEmail(), equalTo(persistedUser.getEmail()));
    }

    @Test
    public void shouldStoreANewUser() {
        User persistedUser = repository.save(user);
        assertThat(persistedUser.getId(), notNullValue());
    }

    @Test
    public void testIfFindByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCaseFindOneUser() {
        User savedUser = this.repository.save(this.user);

        List<User> users = this.repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(user.getFirstName(), "", user.getEmail());
        assertThat(users, hasSize(1));
        assertEquals(savedUser.getId(), users.get(0).getId());
    }

    @Test
    public void testIfFindByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCaseFindOneUserByLastName() {
        String testLastName = "Walaszczyk";
        this.user.setLastName(testLastName);
        User savedUser = this.repository.save(this.user);

        List<User> users = this.repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(user.getFirstName(), testLastName, user.getEmail());
        assertThat(users, hasSize(1));
        assertEquals(savedUser.getId(), users.get(0).getId());
    }

    @Test
    public void testIfFindByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCaseFindUserBetweenTwo() {
        this.repository.save(this.user);
        this.repository.save(this.userSecond);

        List<User> users = this.repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(this.userSecond.getFirstName(), this.userSecond.getLastName(), this.userSecond.getEmail());
        assertThat(users, hasSize(1));
    }

}
